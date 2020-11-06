package com.launch.diagdevice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.result.AppListResult;
import com.launch.diagdevice.common.result.AppResult;
import com.launch.diagdevice.common.util.DateUtils;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.entity.ConsumerRecord;
import com.launch.diagdevice.entity.User;
import com.launch.diagdevice.entity.UserOrder;
import com.launch.diagdevice.entity.request.ConsumerRecordRequest;
import com.launch.diagdevice.entity.request.RechargeRecordRequest;
import com.launch.diagdevice.entity.vo.ConsumerRecordVo;
import com.launch.diagdevice.entity.vo.RechargeRecordVo;
import com.launch.diagdevice.entity.vo.UserAndExtVo;
import com.launch.diagdevice.service.ConsumerRecordService;
import com.launch.diagdevice.service.RechargeRecordService;
import com.launch.diagdevice.service.UserOrderService;
import com.launch.diagdevice.service.UserService;

/**
 * 设备登录用户controller
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月18日
 */
@RestController
@RequestMapping("/loginUser")
public class LoginUserController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Reference(interfaceClass = UserService.class)
	private UserService userService;

	@Reference(interfaceClass = RechargeRecordService.class)
	private RechargeRecordService rechargeService;

	@Reference(interfaceClass = ConsumerRecordService.class)
	private ConsumerRecordService consumerRecordService;

	@Reference(interfaceClass = UserOrderService.class)
	private UserOrderService userOrderService;

	/**
	 * 
	 * 获取登录用户列表
	 * LIPING
	 */
	@RequestMapping(value = "/getUserList", method = { RequestMethod.GET })
	public @ResponseBody String getList(@RequestParam int pageNum, @RequestParam int pageSize, User user) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------loginuser getUserList request param>:[pageNum={},pageSize={},user={},]", pageNum, pageSize,
				user);
		try {

			//user.setPageNum(pageNum);
			//user.setPageSize(pageSize);

			PagingData<UserAndExtVo> pageData = userService.selectPage(user);

			List<UserAndExtVo> listvo = pageData.getRows();

			appResult.setData(listvo);
			appResult.setCount(pageData.getTotal());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------loginUser getUserList error info>:{}" ,  e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 获取消费记录详情
	 * LIPING
	 */
	@RequestMapping(value = "/getConsumerRecordDetail/{orderId}", method = { RequestMethod.GET })
	public @ResponseBody String getConsumerRecordDetail(@PathVariable Long orderId) {
		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------loginuser getConsumerRecordDetail request param>:[orderId={}]", orderId);
		try {
			Map<String, Object> resultMap = new HashMap<String,Object>();
			// 订单信息
			Map<String, Object> orderInfo = new HashMap<String,Object>();
			// 用户信息
			Map<String, Object> userInfo = new HashMap<String,Object>();
			// 消费（诊断）信息
			Map<String, Object> diagInfo = new HashMap<String,Object>();
			
			UserOrder userOd = userOrderService.selectById(orderId);
			orderInfo.put("orderNo", userOd.getOrderNo());
			orderInfo.put("price", userOd.getPrice());
			orderInfo.put("payTime", DateUtils.convertDateToString(userOd.getPayTime(), "yyyy/MM/dd HH:mm:ss"));
			
			Long userId = userOd.getUserId();
			User user = userService.selectById(userId);
			userInfo.put("username", user.getUsername());
			userInfo.put("email", user.getEmail());
			userInfo.put("mobile", user.getMobile());

			
			ConsumerRecord consumerRecord = consumerRecordService.selectById(orderId);
			// 有可能支付但未消费的情况
			if (consumerRecord != null) {
				// 页面显示的 “诊断地点”需要根据 softName 去device表中去查
				// 目前诊断软件APP是写死的，只读取了诊断价格
				diagInfo.put("softName", consumerRecord.getSoftName());
				diagInfo.put("vinCode", consumerRecord.getVinCode());
				diagInfo.put("diagStartTime", DateUtils.convertDateToString(consumerRecord.getDiagStartTime(), "yyyy/MM/dd HH:mm:ss"));
				diagInfo.put("diagEndTime", DateUtils.convertDateToString(consumerRecord.getDiagEndTime(), "yyyy/MM/dd HH:mm:ss"));
			}
			
			resultMap.put("orderInfo", orderInfo);
			resultMap.put("userInfo", userInfo);
			resultMap.put("diagInfo", diagInfo);
			
			appResult.setData(resultMap);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------loginUser getConsumerRecordDetail error info>:{}" , e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 获取消费记录列表
	 * LIPING
	 */
	@RequestMapping(value = "/getConsumerRecordList", method = { RequestMethod.GET })
	public @ResponseBody String getConsumerRecordList(@RequestParam int pageNum, @RequestParam int pageSize,
			ConsumerRecordRequest crRequest) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------loginuser getConsumerRecordList request param>:[pageNum={},pageSize={},rrRequest={},]",
				pageNum, pageSize, crRequest);
		try {

			PagingData<ConsumerRecordVo> pageData = consumerRecordService.selectPage(crRequest);

			List<ConsumerRecordVo> listvo = pageData.getRows();
			appResult.setData(listvo);
			appResult.setCount(pageData.getTotal());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------loginUser getConsumerRecordList error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 获取充值（订单）记录列表
	 * LIPING
	 * 废弃原因：目前APP没有预充值，直接扫描支付使用设备
	 */
	@Deprecated
	@RequestMapping(value = "/getRechargeRecordList/{pageNum}/{pageSize}", method = { RequestMethod.GET })
	public @ResponseBody String getRechargeRecordList(@PathVariable int pageNum, @PathVariable int pageSize,
			RechargeRecordRequest rrRequest) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------loginuser getRechargeRecordList request param>:[pageNum={},pageSize={},rrRequest={},]",
				pageNum, pageSize, rrRequest);
		try {

			PagingData<RechargeRecordVo> pageData = rechargeService.selectPage(rrRequest);

			List<RechargeRecordVo> listvo = pageData.getRows();
			appResult.setData(listvo);
			appResult.setCount(pageData.getTotal());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------loginUser getRechargeRecordList error info>:{}" , e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

}
