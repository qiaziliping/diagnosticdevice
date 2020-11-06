package com.launch.diagdevice.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.launch.diagdevice.common.constant.AppCodeConstant;
import com.launch.diagdevice.common.enums.PdtTypeEnum;
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.result.AppListResult;
import com.launch.diagdevice.common.result.AppResult;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.entity.DiagSoft;
import com.launch.diagdevice.entity.DiagSoftPrice;
import com.launch.diagdevice.entity.request.DiagSoftPriceRequest;
import com.launch.diagdevice.entity.vo.DiagSoftPriceVo;
import com.launch.diagdevice.service.DiagSoftPriceService;
import com.launch.diagdevice.service.DiagSoftService;
import com.launch.diagdevice.system.service.DiagSoftInfoManager;
import com.launch.diagdevice.system.vo.DiagSoftInfo;

/**
 * 软件价格控制类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月26日
 */
@RestController
@RequestMapping("/diagSoft")
public class DiagSoftController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/** 类型：1单次，2包月，3包季，4包年 ,目前默认为1*/
	private static final Integer DEFAULT_BUY_TYPE = 1;

	@Reference(interfaceClass = DiagSoftInfoManager.class)
	private DiagSoftInfoManager diagSoftInfoManager;

	@Reference(interfaceClass = DiagSoftService.class)
	private DiagSoftService diagSoftService;

	@Reference(interfaceClass = DiagSoftPriceService.class)
	private DiagSoftPriceService diagSoftPriceService;

	
	/**
	 * @验证诊断软件的价格是否重复
	 * @param buyType 类型：1单次，2包月，3包季，4包年 ,目前默认为1
	 * @param currency 币种 如：RMB/USD
	 * @param softId 诊断软件ID
	 * LIPING
	 */
	@RequestMapping(value = "/verifyDiagPriceIsRepeated/{buyType}/{currency}/{softId}", method = { RequestMethod.GET })
	public @ResponseBody String verifyDiagPriceIsRepeated(@PathVariable int buyType, @PathVariable String currency,
			 @PathVariable Long softId) {

		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		try {
			DiagSoftPrice model = new DiagSoftPrice();
			model.setBuyType(buyType == 0 ? DEFAULT_BUY_TYPE : buyType);
			model.setCurrency(currency);
			model.setSoftId(softId);
			
			Map<String, Object> condition = new HashMap<String,Object>();
			condition.put("buyType", (buyType == 0 ? DEFAULT_BUY_TYPE : buyType));
			condition.put("currency", currency);
			// 根据序列号查询对应的诊断软件ID集合
			List<Long> softIDList = new ArrayList<Long>();
			softIDList.add(softId);
			condition.put("list", softIDList);
			
			List<DiagSoftPriceVo> rstList = diagSoftPriceService.selectByCondition(condition);
			appResult.setData(rstList);
			appResult.setCount(Long.parseLong(String.valueOf(rstList.size())));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------diagsoft verifyDiagPriceIsRepeated error info>:{}" ,e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);

	}
	
	/**
	 * @param buyType 类型：1单次，2包月，3包季，4包年 ,目前默认为1
	 * @param currency 币种 如：RMB/USD
	 * @param price 价格
	 * @param softId 诊断软件ID
	 * 上传软件定价
	 * LIPING
	 */
	@RequestMapping(value = "/saveDiagSoftPrice/{buyType}/{currency}/{price}/{softId}", method = { RequestMethod.POST })
	public @ResponseBody String saveDiagSoftPrice(@PathVariable int buyType, @PathVariable String currency,
			@PathVariable BigDecimal price, @PathVariable Long softId) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		try {
			DiagSoftPrice model = new DiagSoftPrice();
			model.setBuyType(buyType == 0 ? DEFAULT_BUY_TYPE : buyType);
			model.setCurrency(currency);
			model.setPrice(price);
			model.setSoftId(softId);
			// 上传价格
			diagSoftPriceService.save(model);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------diagsoft saveDiagSoftPrice error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);

	}

	/**
	 * @param id 软件价格ID
	 * @param price 价格
	 * 修改软件价格，只能修改价格，其他参数修改了和新增没有区别了（就无意义了）
	 * LIPING
	 */
	@RequestMapping(value = "/updateDiagSoftPrice/{id}/{price}", method = { RequestMethod.POST })
	public @ResponseBody String updateDiagSoftPrice(@PathVariable Long id, @PathVariable BigDecimal price) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		try {
			DiagSoftPrice model = new DiagSoftPrice();
			model.setPrice(price);
			model.setId(String.valueOf(id));
			// 修改价格
			diagSoftPriceService.update(model);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------diagsoft saveDiagSoftPrice error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * @param id 软件价格ID
	 * 删除软件价格，实为修改，修改status为1
	 * LIPING
	 */
	@RequestMapping(value = "/deleteDiagSoftPrice/{id}", method = { RequestMethod.POST })
	public @ResponseBody String deleteDiagSoftPrice(@PathVariable Long id) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		try {
			DiagSoftPrice model = new DiagSoftPrice();
			model.setId(String.valueOf(id));
			model.setStatus(1);
			// 修改价格
			diagSoftPriceService.update(model);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------diagsoft saveDiagSoftPrice error info>:{}" , e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 分页获取已激活诊断软件列表
	 * LIPING
	 */
	@RequestMapping(value = "/getDiagSoftPriceList", method = { RequestMethod.GET })
	public @ResponseBody String getDiagSoftPriceList(@RequestParam int pageNum, @RequestParam int pageSize,
			DiagSoftPriceRequest dsRequest) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		try {

			dsRequest.setBuyType(DEFAULT_BUY_TYPE);
			// 获取诊断设备价格列表
			PagingData<DiagSoftPriceVo> pagingData = diagSoftPriceService.selectPageVo(dsRequest);
			List<DiagSoftPriceVo> list = pagingData.getRows();
			if (list != null) {
				for (DiagSoftPriceVo vo : list) {
					// 根据产品类型ID获取产品类型名称
					PdtTypeEnum ptEnum = PdtTypeEnum.getPdtType(vo.getPdtTypeId());
					vo.setPdtType(ptEnum.getPdtType());
				}
				
			}
			
			appResult.setData(list);
			appResult.setCount(pagingData.getTotal());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------soft price getDiagSoftPriceList error info>:{}" ,  e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 
	 * 获取全部未激活软件列表（数据库的是从mycar同步过来的）
	 * LIPING
	 */
	@RequestMapping(value = "/getDiagSoftAll", method = { RequestMethod.GET })
	public @ResponseBody String getDiagSoftAll(HttpServletRequest request) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		try {
			// 获取诊断设备列表，不分页
			List<DiagSoft> list = diagSoftService.selectByCondition(null);

			long count = (long) list.size();

			appResult.setData(list);
			appResult.setCount(count);
			helper.filter(DiagSoft.class, "softId,softName,softApplicableArea", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------soft price getDiagSoftAll error info>:{}" ,  e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 根据产品类型获取未激活的软件列表（数据库的是从mycar同步过来的）
	 * LIPING
	 */
	@RequestMapping(value = "/getDiagSoftByPdtTypeId/{pdtTypeId}", method = { RequestMethod.GET })
	public @ResponseBody String getDiagSoftByPdtTypeId(HttpServletRequest request,@PathVariable Long pdtTypeId) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("--------controller.getDiagSoftByPdtTypeId.param>:"+pdtTypeId);
		try {
			Map<String, Object> condition = new HashMap<String,Object>();
			condition.put("pdtTypeId", pdtTypeId);
			List<DiagSoft> list = diagSoftService.selectByCondition(condition);
			
			
			long count = (long) list.size();
			appResult.setData(list);
			appResult.setCount(count);
			helper.filter(DiagSoft.class, "softId,softName,softApplicableArea", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------soft price getDiagSoftByPdtTypeId error info>:{}" ,  e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 获取所有产品类型
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getPdtTypeAll", method = { RequestMethod.GET })
	public @ResponseBody String getPdtTypeAll(HttpServletRequest request) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		try {
			// 获取诊断设备列表，不分页
			List<Long> list = diagSoftService.selectPdtTypeAll();
			List<Map<String,Object>> rstList = new ArrayList<Map<String,Object>>();
			for (Long pdtTypeId : list) {
				Map<String,Object> map = new HashMap<String,Object>();
				String pdtType = PdtTypeEnum.getPdtType(pdtTypeId).getPdtType();
				
				map.put("pdtTypeId", pdtTypeId);
				map.put("pdtType", pdtType);
				rstList.add(map);
			}
			
			appResult.setData(rstList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------soft price getPdtTypeAll error info>:{}" , e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 同步mycar的诊断软件设备
	 * 
	 */
	@RequestMapping(value = "/synchronizedMyCarDiagSoft/{pdtTypeId}/{lanId}", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String synchronizedMyCarDiagSoft(@PathVariable int pdtTypeId,@PathVariable int lanId) throws Exception {
		
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		try {
			// 目前1133（产品类型ID）和1001（语言ID）定死的
			//List<DiagSoftInfo> list = diagSoftInfoManager.queryDiagSoftInfoList(1133, 1001);
			List<DiagSoftInfo> list = diagSoftInfoManager.queryDiagSoftInfoList(pdtTypeId, lanId);
			
			logger.info(pdtTypeId+"--lanId>:"+lanId+"----diagSoft.synchronizedMyCarDiagSoft.list>:"+list);
			List<DiagSoft> dslist = new ArrayList<DiagSoft>();
			for (DiagSoftInfo info : list) {
				DiagSoft ds = new DiagSoft();
				
				Integer softId = info.getSoftId();
				
				ds.setSoftId(Long.parseLong(softId+""));
				ds.setPdtTypeId(Long.parseLong(info.getPdtTypeId()+""));
				ds.setSoftName(info.getSoftName());
				ds.setSoftApplicableArea(info.getSoftApplicableArea());
				dslist.add(ds);
			}
			diagSoftService.batchSave(dslist);
			appResult.setData(dslist);
			int size = dslist.size();
			appResult.setCount((long)size);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("sayHello error>:{}",e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(e.toString());
		}
		return helper.toJsonStr(appResult);
	}

}
