package com.launch.diagdevice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.launch.diagdevice.common.constant.AppCodeConstant;
import com.launch.diagdevice.common.constant.Constants;
import com.launch.diagdevice.common.constant.WebConstant;
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.result.AppListResult;
import com.launch.diagdevice.common.result.AppResult;
import com.launch.diagdevice.common.util.DateUtils;
import com.launch.diagdevice.common.util.FileUploadUtil;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.entity.Device;
import com.launch.diagdevice.entity.DeviceGroup;
import com.launch.diagdevice.entity.DeviceOptRecord;
import com.launch.diagdevice.entity.vo.DeviceGroupVo;
import com.launch.diagdevice.entity.vo.DeviceOptRecordVo;
import com.launch.diagdevice.entity.vo.DeviceVo;
import com.launch.diagdevice.service.DeviceGroupService;
import com.launch.diagdevice.service.DeviceOptRecordService;
import com.launch.diagdevice.service.DeviceService;
import com.launch.diagdevice.system.model.SysProduct;
import com.launch.diagdevice.system.service.KeyValueExpandManager;
import com.launch.diagdevice.system.service.SysProductManager;

/**
 * 设备控制类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月6日
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

	@Reference(interfaceClass = DeviceService.class)
	private DeviceService deviceService;

	@Reference(interfaceClass = DeviceGroupService.class)
	private DeviceGroupService deviceGroupService;

	@Reference(interfaceClass = DeviceOptRecordService.class)
	private DeviceOptRecordService deviceOptRecordService;
	
	@Reference(interfaceClass = SysProductManager.class)
	private SysProductManager sysProductManager;
	@Reference(interfaceClass = KeyValueExpandManager.class)
	private KeyValueExpandManager keyValueExpandManager;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	/**
	 * 根据序列号查询Mycar的设备信息
	 * @param id 设备主键id
	 * @param location 设备位置
	 * LIPING
	 */
	@RequestMapping(value = "/getMycarDeviceInfoBySerialNo/{serialNo}", method = { RequestMethod.GET })
	public @ResponseBody String getMycarDeviceInfoBySerialNo(@PathVariable String serialNo) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------device getMycarDeviceInfoBySerialNo request param>:[serialNo={}]", serialNo);
		try {
			
			SysProduct product = sysProductManager.getProcuctBySerialNo(serialNo);
			if (null != product) {
				
				Integer pdtTypeId = product.getPdtTypeId();
				Integer softConfId = product.getSoftConfId();
				// 验证该序列号的产品类型是否允许添加
				if (isValid(softConfId)) {
					String pdtType = product.getPdtType();
					Map<String,Object> result = new HashMap<String,Object>();
					result.put("pdtType", pdtType);
					result.put("pdtTypeId", pdtTypeId);
					appResult.setData(result);
				} else {
					// 只有指定的产品类型的序列号才可以添加
					appResult.setCode(AppCodeConstant.SERIAL_NO_ERROR);
					appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.SERIAL_NO_ERROR));
				}
			} else {
				// 该序列号不存在
				appResult.setCode(AppCodeConstant.SERIAL_NO_NOT_EXIST);
				appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.SERIAL_NO_NOT_EXIST));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------device getMycarDeviceInfoBySerialNo error info>:{}" , e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	
	/**
	 * 修改设备
	 * @param id 设备主键id
	 * @param location 设备位置
	 * LIPING
	 */
	@RequestMapping(value = "/update/{id}/{location}", method = { RequestMethod.POST,RequestMethod.GET })
	public @ResponseBody String update(@PathVariable Long id, @PathVariable String location, @Valid Device device) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------device update request param>:[id={},location={}]", id, location);
		try {

			deviceService.update(device);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------device update error info>:{}" , e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 添加设备
	 * @param deviceGroupId 设备分组ID
	 * @param deviceType 设备类型
	 * @param serialNumber 设备序列号
	 * @param voucher 设备凭证
	 * @param location 设备所在位置
	 * @param owner 设备所有者身份
	 * LIPING
	 */
	@RequestMapping(value = "/save/{deviceGroupId}/{deviceType}/{serialNo}", method = { RequestMethod.POST })
	public @ResponseBody String save(@PathVariable Long deviceGroupId, @PathVariable String deviceType,
			@PathVariable String serialNo, @RequestParam MultipartFile upfile, @Valid Device device) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------device save request param>:[deviceGroupId={},deviceType={},serialNo={},getOwner={}]",
				deviceGroupId, deviceType, serialNo, device.getOwner());
		try {

			// 先判断上传文件是否为图片
			String fileName = upfile.getOriginalFilename();
			if (FileUploadUtil.isImage(fileName)) {
				String updateFileName = DateUtils.convertDateToString(DateUtils.getCurrentDate(),
						DateUtils.DATE_TIME_PATTERN) + "-" + serialNo;
				
				// path文件指定的路径
				String path = WebConstant.FILE_UPLOAD_URL;
				
				String retUrl = FileUploadUtil.upload(upfile,path , updateFileName);
				
				String voucher = retUrl.replace(path, "");
				device.setVoucher(voucher);
				deviceService.save(device);
			} else {
				appResult.setCode(WebConstant.UPLOAD_FILE_NO_IMAGE);
				appResult.setMessage(WebConstant.checkCode(WebConstant.UPLOAD_FILE_NO_IMAGE));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------device save error info>:{}" ,e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 
	 * 获取设备详情
	 * LIPING
	 */
	@RequestMapping(value = "/getDeviceDetail/{id}", method = { RequestMethod.GET })
	public @ResponseBody String getDeviceDetail(@PathVariable Long id) {
		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getDeviceDetail request param>:[id={}]", id);
		try {

			Device device = deviceService.selectById(id);
			DeviceVo vo = new DeviceVo();
			BeanUtils.copyProperties(device, vo);
			
			DeviceGroup group = deviceGroupService.selectById(device.getDeviceGroupId());
			
			vo.setDeviceGroupName(group.getGroupName());
			vo.setId(Long.parseLong(device.getId()));
			appResult.setData(vo);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------device getDeviceDetail error info>:{}" , e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 
	 * 获取设备列表
	 * LIPING
	 */
	@RequestMapping(value = "/getList", method = { RequestMethod.GET })
	public @ResponseBody String getList(@RequestParam int pageNum, @RequestParam int pageSize, @Valid Device device) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getList request param>:[pageNum={},pageSize={},deviceGroupId={},serialNo={}]", pageNum, pageSize,
				device.getDeviceGroupId(),device.getSerialNo());
		try {

			//device.setPageNum(pageNum);
			//device.setPageSize(pageSize);
			PagingData<DeviceVo> pageData = deviceService.selectPage(device);

			List<DeviceVo> listvo = pageData.getRows();

			appResult.setData(listvo);
			appResult.setCount(pageData.getTotal());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------device getList error info>:{}" ,  e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 查询所有设备组
	 * @param deviceGroupId 设备分组ID
	 * LIPING
	 */
	@RequestMapping(value = "/selectAllDeviceGroup", method = { RequestMethod.GET })
	public @ResponseBody String selectAllDeviceGroup(HttpServletRequest request) {

		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------deviceGroup selectAllDeviceGroup request param>:");
		try {
			List<DeviceGroup> list = deviceGroupService.selectAll(new DeviceGroup());

			List<DeviceGroupVo> listvo = new ArrayList<DeviceGroupVo>();

			for (DeviceGroup group : list) {
				DeviceGroupVo vo = new DeviceGroupVo();
				BeanUtils.copyProperties(group, vo);

				listvo.add(vo);
			}
			appResult.setData(listvo);
			appResult.setCount(Long.parseLong(String.valueOf(listvo.size())));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------deviceGroup selectAllDeviceGroup error info>:{}" ,  e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 添加设备组
	 * @param deviceGroupId 设备分组ID
	 * LIPING
	 */
	@RequestMapping(value = "/saveDeviceGroup/{groupName}", method = { RequestMethod.POST })
	public @ResponseBody String saveDeviceGroup(@PathVariable String groupName, @Valid DeviceGroup deviceGroup) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------deviceGroup save request param>:[groupName={}]", groupName);
		try {

			deviceGroupService.save(deviceGroup);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------deviceGroup save error info>:{}" ,  e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 添加设备售后操作记录
	 * @param deviceId 设备ID
	 * @param recordName 记录人
	 * @param remark 记录内容
	 * LIPING
	 */
	@RequestMapping(value = "/saveDeviceOptRecord/{deviceId}/{recordName}/{remark}", method = { RequestMethod.POST })
	public @ResponseBody String saveDeviceOptRecord(@PathVariable Long deviceId, @PathVariable String recordName,
			@PathVariable String remark) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------device saveDeviceOptRecord request param>:[deviceId={},recordName={},remark={}]", deviceId,
				recordName, remark);
		try {

			DeviceOptRecord record = new DeviceOptRecord();
			record.setDeviceId(deviceId);
			record.setRecordName(recordName);
			record.setRemark(remark);

			deviceOptRecordService.save(record);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------device save error info>:{}",  e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 获取设备售后操作记录列表
	 * LIPING
	 */
	@RequestMapping(value = "/getDeviceOptRecordList/{deviceId}/{pageNum}/{pageSize}", method = { RequestMethod.GET })
	public @ResponseBody String getDeviceOptRecordList(@PathVariable Long deviceId, @PathVariable int pageNum,
			@PathVariable int pageSize) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getList request param>:[deviceId={},pageNum={},pageSize={}]", deviceId, pageNum, pageSize);
		try {
			DeviceOptRecord record = new DeviceOptRecord();
			record.setPageNum(pageNum);
			record.setPageSize(pageSize);
			PagingData<DeviceOptRecord> pageData = deviceOptRecordService.selectPage(record);

			List<DeviceOptRecord> list = pageData.getRows();
			List<DeviceOptRecordVo> listvo = new ArrayList<DeviceOptRecordVo>();
			for (DeviceOptRecord model : list) {
				DeviceOptRecordVo vo = new DeviceOptRecordVo();
				BeanUtils.copyProperties(model, vo);
				listvo.add(vo);
			}

			appResult.setData(listvo);
			appResult.setCount(pageData.getTotal());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------device getDeviceOptRecordList error info>:{}" ,  e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 验证序列号是否允许添加
	 * LIPING
	 */
	private boolean isValid(Integer pdtTypeId) {
		boolean flag = false;
		
//		String value = keyValueExpandManager.queryValueByKey(Constants.DIAGDEVICE_ALLOW_PDT_TYPE);
		String value = keyValueExpandManager.queryValueByKey(Constants.DIAGDEVICE_ALLOW_CONFIG_ID);
		logger.info("DeviceController isValid produect type ids={}",value);
		if (StringUtils.isNotBlank(value)) {
			String [] arr = value.split(",");
			
			for (int i = 0;i<arr.length;i++) {
				String arrValue = arr[i];
				int ptId = Integer.parseInt(arrValue);
				
				if (pdtTypeId.intValue() == ptId) {
					flag = true;
					continue;
				}
			}
			
		}
		// 判断是否为PAD3产品
		return flag;
	}
}
