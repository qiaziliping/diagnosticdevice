package com.launch.diagdevice.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.launch.diagdevice.common.configure.SignValidate;
import com.launch.diagdevice.common.constant.AppCodeConstant;
import com.launch.diagdevice.common.constant.AppConstant;
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.result.AppListResult;
import com.launch.diagdevice.common.result.AppResult;
import com.launch.diagdevice.common.util.DateUtils;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.common.util.VerifyUtil;
import com.launch.diagdevice.common.utils.RedisUtil;
import com.launch.diagdevice.entity.ConsumerRecord;
import com.launch.diagdevice.entity.UserOrder;
import com.launch.diagdevice.entity.UserOrderDetail;
import com.launch.diagdevice.entity.request.ConsumerRecordRequest;
import com.launch.diagdevice.entity.vo.ConsumerRecordVo;
import com.launch.diagdevice.service.ConsumerRecordService;
import com.launch.diagdevice.service.RechargeRecordService;
import com.launch.diagdevice.service.UserExtService;
import com.launch.diagdevice.service.UserOrderDetailService;
import com.launch.diagdevice.service.UserOrderService;
import com.launch.diagdevice.service.UserService;

/**
 * 
 * 账户控制类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月14日
 */
@RestController
@RequestMapping("/app/account")
public class AccountController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(AccountController.class);

	/**
	 * reference 引用接口
	 */
	@Reference(interfaceClass = UserService.class)
	private UserService userService;
	@Reference(interfaceClass = UserExtService.class)
	private UserExtService userExtService;

	@Reference(interfaceClass = RechargeRecordService.class)
	private RechargeRecordService rechargeRecordService;
	
	@Reference(interfaceClass = UserOrderService.class)
	private UserOrderService userOrderService;
	@Reference(interfaceClass = UserOrderDetailService.class)
	private UserOrderDetailService userOrderDetailService;

	@Reference(interfaceClass = ConsumerRecordService.class)
	private ConsumerRecordService consumerRecordService;

	@Autowired
	private RedisUtil redisUtil;

	

	/**
	 * 保存用户消费记录
	 * @param diagEndTime 诊断结束时间
	 * @param diagSoftPriceId 诊断软件价格Id
	 * @param diagStartTime 诊断开始时间
	 * @param orderNo 订单号
	 * @param sign 签名
	 * @param softName 软件名称
	 * @param userId 用户ID
	 * @param vinCode 汽车唯一识别码
	 * LIPING
	 */
	
	@SignValidate(option=SignValidate.LA2LSVal,modelName="saveConsumerRecord")
	@ResponseBody
	@RequestMapping(value = {"/saveConsumerRecord/{diagEndTime}/{diagSoftPriceId}/{diagStartTime}/{orderNo}/{sign}/{softName}/{userId}/{vinCode}",
	"/saveConsumerRecord/{diagEndTime}/{diagSoftPriceId}/{diagStartTime}/{orderNo}/{sign}/{softName}/{userId}"}, method = { RequestMethod.POST ,RequestMethod.GET })
	public String saveConsumerRecord(@PathVariable String diagEndTime,@PathVariable Long diagSoftPriceId,@PathVariable String diagStartTime,@PathVariable String orderNo,
			@PathVariable String sign,@PathVariable String softName,@PathVariable String userId,@PathVariable(required=false) String vinCode) {
		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------saveConsumerRecord request param>:[diagEndTime={},diagSoftPriceId={},diagStartTime={},orderNo={},sign={},softName={},vinCode={}]" ,
				diagEndTime,diagSoftPriceId,diagStartTime,orderNo,sign,softName,vinCode);
		try {
			
			// 根据订单号查询订单ID
			UserOrder userOrder = new UserOrder();
			userOrder.setOrderNo(orderNo);
			userOrder = userOrderService.selectOne(userOrder);
			
			// 订单不存在不允许提交【测试提】
			if (null == userOrder) {
				appResult.setCode(AppCodeConstant.ORDER_NO_NOT_EXIST);
	            appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.ORDER_NO_NOT_EXIST));
	            return helper.toJsonStr(appResult);
			}
			// 该订单不属于该用户
			String tempUId = userOrder.getUserId().toString();
			if (!tempUId.equals(userId)) {
				appResult.setCode(AppCodeConstant.ORDER_NO_NOT_BELONG_USER);
	            appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.ORDER_NO_NOT_BELONG_USER));
	            return helper.toJsonStr(appResult);
			}
			// 如果返回为0表示OK
			int code = validDate(diagStartTime,diagEndTime);
			if (code != 0) {
				appResult.setCode(code);
	            appResult.setMessage(AppCodeConstant.checkCodeOld(code));
	            return helper.toJsonStr(appResult);
			}
			
			// 需要对softName参数进行校验，不是orderNo对应的softName【测试提】
			UserOrderDetail model = new UserOrderDetail();
			model.setOrderId(Long.parseLong(userOrder.getId()));
			model.setDiagSoftPriceId(diagSoftPriceId);
			
			model = userOrderDetailService.selectOne(model);
			if (model == null || !softName.equalsIgnoreCase(model.getSoftName())) {
				appResult.setCode(AppCodeConstant.ORDER_NO_SOFTNAME_DIFFERENCE);
	            appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.ORDER_NO_SOFTNAME_DIFFERENCE));
	            return helper.toJsonStr(appResult);
			}
			
			Long orderId = Long.parseLong(userOrder.getId());
			
			ConsumerRecord tempModel = consumerRecordService.selectById(orderId);
			// 排除重复提交数据问题
			if (null == tempModel) {
				ConsumerRecord record = new ConsumerRecord();
				record.setOrderId(orderId);
				
				record.setSoftName(softName);
				record.setVinCode(vinCode);
				
				record.setDiagStartTime(DateUtils.convertStringToDate(DateUtils.DATETIME, diagStartTime));
				record.setDiagEndTime(DateUtils.convertStringToDate(DateUtils.DATETIME,diagEndTime));
				
				consumerRecordService.save(record);
			} else {
				appResult.setCode(AppCodeConstant.REPEAT_COMMIT_DATA);
	            appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.REPEAT_COMMIT_DATA));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------saveConsumerRecord.ado>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);

	}

	/**
	 * 验证开始时间和结束时间的格式，以及大小问题
	 * 返回0表示验证OK
	 * @throws ParseException 
	 */
	private static int validDate(String diagStartTime, String diagEndTime) throws ParseException {
		int flag = 0;
		// 验证开始时间格式
		boolean isStart = VerifyUtil.isValidDate(diagStartTime, DateUtils.DATETIME);
		boolean isEnd = VerifyUtil.isValidDate(diagEndTime, DateUtils.DATETIME);
		if (!isStart || !isEnd) {
			return AppCodeConstant.DATE_FORMART_ERROR;
		}
		
		Date sdate = DateUtils.convertStringToDate(DateUtils.DATETIME, diagStartTime);
		Date edate = DateUtils.convertStringToDate(DateUtils.DATETIME,diagEndTime);
		if(!VerifyUtil.compareDate(sdate, edate)){
			return AppCodeConstant.STARTDATE_GT_ENDDATE;
		}
		
		return flag;
	}
	
	/**
	 * 调用此方法通知客户端已获取到消费token，服务器销毁token
	 * LIPING
	 */
	@SignValidate(option=SignValidate.LA2LSVal,modelName="consumerTokenNotify")
	@ResponseBody
	@RequestMapping(value = "/consumerTokenNotify/{consumerToken}/{orderNo}/{sign}/{userId}", method = { RequestMethod.POST,RequestMethod.GET })
	public String consumerTokenNotify( @PathVariable String consumerToken,@PathVariable String orderNo,@PathVariable String sign,
			@PathVariable String userId) {
		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------consumerTokenNotify request param>:[orderNo={},consumerToken={},userId={}]" ,orderNo,consumerToken,userId);
		try {
			String key = AppConstant.DIAGDEVICE_CONSUMER_TOKEN + ":" + userId+orderNo;
			// 获取消费
			String cacheToken = redisUtil.get(key);
			if (consumerToken.equals(cacheToken)) {
				redisUtil.remove(key);
			} else {
				// 该订单号和用户的消费token不一致
				appResult.setCode(AppCodeConstant.ORDER_NO_USER_CONSUMER_TOKEN_DIFFERENCE);
				appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.ORDER_NO_USER_CONSUMER_TOKEN_DIFFERENCE));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------consumerTokenNotify>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 获取消费token
	 * LIPING
	 */
	@SignValidate(option=SignValidate.LA2LSVal,modelName="getConsumerToken")
	@ResponseBody
	@RequestMapping(value = "/getConsumerToken/{orderNo}/{sign}/{userId}", method = { RequestMethod.POST,RequestMethod.GET })
	public String getConsumerToken(HttpServletRequest request, @PathVariable String orderNo,@PathVariable String sign,
			@PathVariable String userId) {
		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getConsumerToken request param>:[orderNo={},userId={}]" ,orderNo,userId);
		try {
			
			String key = AppConstant.DIAGDEVICE_CONSUMER_TOKEN + ":" +userId+ orderNo;
			// 获取消费
			String consumerToken = redisUtil.get(key);
			if (StringUtils.isEmpty(consumerToken))
				consumerToken = "";
			else 
				redisUtil.setExpire(key, 60*3L); // 设置过期时间为3分钟
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("consumerToken", consumerToken);
			appResult.setData(map);
			
			

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------getConsumerToken.ado>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	
	

	/**
	 * 用户消费记录查询
	 * LIPING
	 */
	@SignValidate(option=SignValidate.LA2LSVal,modelName="getConsumerRecord")
	@ResponseBody
	@RequestMapping(value = "/getConsumerRecord/{pageNum}/{pageSize}/{sign}/{userId}", method = { RequestMethod.POST,RequestMethod.GET })
	public String getConsumerRecord(@PathVariable String pageNum,@PathVariable String pageSize,
			@PathVariable String sign,@PathVariable String userId) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getConsumerRecord request param>:[pageNum={},pageSize={},sign={},userId={},]",pageNum,pageSize,sign,userId);
		try {
			if (userId != null) {
				ConsumerRecordRequest modelRequest = new ConsumerRecordRequest();
				
				modelRequest.setPageNum(Integer.parseInt(pageNum));
				modelRequest.setPageSize(Integer.parseInt(pageSize));
				modelRequest.setUserId(Long.parseLong(userId));
				
				PagingData<ConsumerRecordVo> pageData = consumerRecordService.selectPage(modelRequest);

				List<ConsumerRecordVo> list = pageData.getRows();

				appResult.setData(list);
				appResult.setCount(pageData.getTotal());
				helper.filter(ConsumerRecordVo.class, null, "username,orderNo");
			} else {
				// 缺少参数
				setResultOld(appResult, AppCodeConstant.PARAM_LACK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------getConsumerRecord.ado>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		
		return helper.toJsonStr(appResult);
	}
	
	
	/**
	 * 批量删除消费记录，逻辑删除
	 * LIPING
	 * @param ids = [1,2...]
	 */
	@SignValidate(option=SignValidate.LA2LSVal,modelName="batchDeleteCsmRecord")
	@RequestMapping(value = "/batchDeleteCsmRecord/{ids}/{sign}/{userId}", method = { RequestMethod.POST,RequestMethod.GET  })
	public @ResponseBody String batchDeleteCsmRecord(@PathVariable List<String> ids,@PathVariable String sign,@PathVariable String userId) {
	
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info(ids + "------batchDeleteCsmRecord request param>:[userId={},ids={}]",userId,ids);
		try {
			if (StringUtils.isNotBlank(userId)) {

				// List<String> listIds = helper.fromMultiObj(ids, new TypeReference<List<String>>(){});
				// 批量逻辑删除
				Integer count = consumerRecordService.batchUpdateStatus(Long.parseLong(userId), ids);

				appResult.setCount(Long.parseLong(String.valueOf(count)));
			} else {
				// 缺少参数
				setResultOld(appResult, AppCodeConstant.PARAM_LACK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------batchDeleteCsmRecord.ado>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 用户充值记录
	 * LIPING
	 */
	/*@SignValidate(option=SignValidate.LA2LSVal,modelName="getRechargeRecord")
	@ResponseBody
	@RequestMapping(value = "/getRechargeRecord/{pageNum}/{pageSize}/{sign}/{userId}", method = { RequestMethod.POST,RequestMethod.GET })
	 public String getRechargeRecord(@PathVariable int pageNum,@PathVariable int pageSize,@PathVariable String sign,
			 @PathVariable Long userId) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getRechargeRecord request param>:[pageNum={},pageSize={},sign={},userId={},]",pageNum,pageSize,sign,userId);
		try {
			if (userId != null) {
				RechargeRecord model = new RechargeRecord();
				model.setUserId(userId);

				model.setPageNum(pageNum);
				model.setPageSize(pageSize);
				model.setIsPay(PayConstants.IS_PAY_YES); //查询已支付订单

				PagingData<RechargeRecord> pageData = rechargeRecordService.selectPage(model);

				List<RechargeRecord> list = pageData.getRows();

				List<RechargeRecordVo> listvo = new ArrayList<RechargeRecordVo>();
				for (RechargeRecord entity : list) {
					RechargeRecordVo tmpVo = new RechargeRecordVo();
					BeanUtils.copyProperties(entity, tmpVo);

					tmpVo.setId(Long.parseLong(entity.getId()));
					tmpVo.setCreateTime(DateUtils.convertDateToString(entity.getCreateTime(), DateUtils.DATETIME));
					listvo.add(tmpVo);
				}
				appResult.setData(listvo);
				appResult.setCount(pageData.getTotal());
				helper.filter(RechargeRecordVo.class, null, "diagMoney,discountsMoney,updateTime");
			} else {
				// 缺少参数
				setResultOld(appResult, AppCodeConstant.PARAM_LACK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------getRechargeRecord.ado>:", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		
		return helper.toJsonStr(appResult);
	}*/

	/**
	 * 批量删除充值记录，逻辑删除
	 * LIPING
	 */
	/*@SignValidate(option=SignValidate.LA2LSVal,modelName="batchDeleteRechargeRecord")
	@RequestMapping(value = "/batchDeleteRechargeRecord/{ids}/{sign}/{userId}", method = { RequestMethod.POST })
	public @ResponseBody String batchDeleteRechargeRecord(@PathVariable List<String> ids,@PathVariable String sign,@PathVariable Long userId ) {
		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info(ids + "------batchDeleteRechargeRecord request param>:[userId={}]",userId);
		try {
			if (userId != null) {
				rechargeRecordService.batchUpdateStatus(userId, ids);

			} else {
				// 缺少参数
				setResultOld(appResult, AppCodeConstant.PARAM_LACK);
			}
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error("--------batchDelete.ado>:", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}*/

}
