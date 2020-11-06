package com.launch.diagdevice.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.launch.diagdevice.common.configure.SignValidate;
import com.launch.diagdevice.common.constant.AppCodeConstant;
import com.launch.diagdevice.common.constant.AppConstant;
import com.launch.diagdevice.common.constant.Constants;
import com.launch.diagdevice.common.constant.PayConstants;
import com.launch.diagdevice.common.result.AppResult;
import com.launch.diagdevice.common.util.DateUtils;
import com.launch.diagdevice.common.util.HttpInfoClient;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.common.util.MD5Util;
import com.launch.diagdevice.common.util.OrderUtils;
import com.launch.diagdevice.common.util.URLUtils;
import com.launch.diagdevice.common.utils.RedisUtil;
import com.launch.diagdevice.entity.DiagSoft;
import com.launch.diagdevice.entity.DiagSoftPrice;
import com.launch.diagdevice.entity.UserOrder;
import com.launch.diagdevice.entity.UserOrderDetail;
import com.launch.diagdevice.pay.service.PaypalService;
import com.launch.diagdevice.service.DiagSoftPriceService;
import com.launch.diagdevice.service.DiagSoftService;
import com.launch.diagdevice.service.UserOrderService;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;

/**
 * PAYPAL支付，调用杜睿支付接口
 * @author liping
 *
 */
@Controller
@RequestMapping("/paypalNVP")
public class PaypalNVPController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(AlipayController.class);

	public static final String PAYPAL_SUCCESS_URL_TEST = "/pay/success/test";
	public static final String PAYPAL_NOTIFY_URL_TEST = "/pay/notifyUrl/test";
	
	/** 支付成功返回url */
	public static final String PAYPAL_SUCCESS_URL = "/pay/success";
	/** 取消支付返回url */
	public static final String PAYPAL_CANCEL_URL = "/pay/cancel";
	/** 支付回调URL */
	public static final String PAYPAL_NOTIFY_URL = "/pay/notifyUrl";

	/** 支付币种 */
	public static final String PAYPAL_CURRENCY = "USD";


	@Reference(interfaceClass = PaypalService.class)
	private PaypalService paypalService;

	@Reference(interfaceClass = UserOrderService.class)
	private UserOrderService userOrderService;

	@Reference(interfaceClass = DiagSoftPriceService.class)
	private DiagSoftPriceService diagSoftPriceService;

	@Reference(interfaceClass = DiagSoftService.class)
	private DiagSoftService diagSoftService;

	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 跳转到paypal支付页面
	 * @param diagSoftPriceId 诊断软件价格ID主键（获取诊断软件价格列表时已经返回）
	 * @param serialNo 设备序列号
	 * @param price 价格
	 * @param sign 签名
	 * @param userId 登录用户ID
	 */
	@SignValidate(option = SignValidate.LA2LSVal, modelName = "toPay")
	@RequestMapping(value = "/toPay/{diagSoftPriceId}/{price}/{serialNo}/{sign}/{userId}",method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String toPay(@PathVariable Long diagSoftPriceId,@PathVariable String serialNo, @PathVariable String price,
	    		@PathVariable String sign,@PathVariable String userId,HttpServletRequest request) {
		 
	    logger.info("paypalController.getUrl.diagSoftPriceId={},serialNo={},price={},sign={},userId={},"
	    			,diagSoftPriceId,serialNo,price,userId);
		AppResult appResult = new AppResult();
		JsonHelper jsonHelper = new JsonHelper();
		Map<String,Object> rstMap = new HashMap<String,Object>();
		
		try {

			// 1、判断必要字段是否为空
	        if (StringUtils.isNotBlank(userId) && price != null && StringUtils.isNotBlank(serialNo)) {

	        	// 2、判断序列号是否和登录时的序列号一致
				String snKey = AppConstant.DIAGDEVICE_APPUSER_SERIAL_NO + ":" + userId;
				String cacheSN = redisUtil.get(snKey);
				if (!serialNo.equals(cacheSN)) {
					setResultOld(appResult, AppCodeConstant.SERIAL_NO_ERROR);
					return jsonHelper.toJsonStr(appResult);
				}
				
				
				// 3、根据软件价格ID（diagSoftPriceId）获取价格
				DiagSoftPrice dsPriceModel = diagSoftPriceService.selectById(diagSoftPriceId);
				
				// 验证币种是否为美元，不支持CNY人民币[支持欧元EUR，欧元后期添加]
				String currency = dsPriceModel.getCurrency();
				if (!Constants.CURRENCY_USD.equals(currency)) {
					setResultOld(appResult, AppCodeConstant.CURRENCY_NOT_SUPPORT_PAYPAL);
					return jsonHelper.toJsonStr(appResult);
				}
				
				BigDecimal dsPrice = dsPriceModel.getPrice();
				
				BigDecimal rechargePrice = new BigDecimal(price); // rechargePrice页面传过来的价格
				if (rechargePrice.compareTo(dsPrice) == 0) { // 判断是否相等
					// 获取诊断设备对象
					DiagSoft diagSoft = diagSoftService.selectById(dsPriceModel.getSoftId());
					
					
					
					String orderNo = OrderUtils.getConsumerNo(AppConstant.DIAGDEVICE_PP_ORDER_NO_PRE);
					
					logger.info("paypalController.getUrl.currency={},diagSoftName={},orderNo={}",currency,diagSoft.getSoftName(),orderNo);
					
					Map<String, String> params =  getMapParams(request);
					params.put("orderNo", orderNo); // 订单编号
					params.put("orderPrice", price); // 订单金额 , 【单位:元】
					params.put("productName", diagSoft.getSoftName()); // 商品名称
					params.put("field1", currency); 	// 币种
					
					String paypalSign = sortAndMd5Util(params);
					
					params.put("sign", paypalSign); // 支付签名
					
					String formData = buildReq(params,"post","submit",PayConstants.PAYPAL_LAUNCH_CREATE_PAY_URL);
					logger.info("-----------paypal--formData>:" + formData);
					
					rstMap.put("orderNo", orderNo);
					//rstMap.put("htmlStr", htmlStr);
					appResult.setData(rstMap);
					
					//					request.setAttribute("htmlstr", htmlStr);
					
					// 未支付订单不存入数据库,所以先存入缓存中，回调时需要保存
					Map<String, String> cacheMap = new HashMap<String, String>();
					cacheMap.put("userId", String.valueOf(userId));
					cacheMap.put("price", price.toString());
					cacheMap.put("serialNo", serialNo);
					cacheMap.put("orderNo", orderNo);
					cacheMap.put("diagSoftPriceId", String.valueOf(diagSoftPriceId));
					//cacheMap.put("formData", formData);

					String orderKey = AppConstant.DIAGDEVICE_ORDER_NOT_PAY_ORDER + ":" + orderNo;
					redisUtil.hmset(orderKey, cacheMap);
					
					String paramsKey = AppConstant.DIAGDEVICE_PAYPAL_PARAMS + ":" + orderNo;
					redisUtil.hmset(paramsKey, params);
					redisUtil.setExpire(paramsKey, 60*30L);
					
					
				} else {
					// 支付金额有误
					setResult(appResult, AppCodeConstant.RECHARGE_MONEY_ERROR);
					return jsonHelper.toJsonStr(appResult);
				}
			} else {
				// 缺少参数
				setResultOld(appResult, AppCodeConstant.PARAM_LACK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("-------PaypalController.toPay.error>:{}", e);
			setResult(appResult, AppCodeConstant.ERROR);
		}
		//		return "topay"; 
		return jsonHelper.toJsonStr(appResult);
	}
	
	/**
	 * 跳转到PAYPAL登录支付页面
	 * @return
	 */
	@SignValidate(option = SignValidate.LA2LSVal, modelName = "goPaypal")
	@RequestMapping(value ="/goPaypal/{orderNo}/{sign}/{userId}")
	public String goPaypal(@PathVariable("orderNo") String orderNo,@PathVariable("sign") String sign,@PathVariable Long userId,HttpServletRequest request) {
		logger.info("------goPaypal.orderNo--->:"+orderNo);
		try {
			// ---------获取缓存中的
	     	//String orderKey = AppConstant.DIAGDEVICE_ORDER_NOT_PAY_ORDER + ":" + orderNo;
	 		//Map<String, String> cacheMap = redisUtil.hmget(orderKey);
			
			String paramsKey = AppConstant.DIAGDEVICE_PAYPAL_PARAMS + ":" + orderNo;
			Map<String, String> paramsMap = redisUtil.hmget(paramsKey);
			Map<String, String> requestMap = new HashMap<String, String>();
			for (Entry<String, String> entry : paramsMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (value != null) {
					requestMap.put(key, value);
				}
			}
			
			HttpInfoClient httpClient = HttpInfoClient.getInstance();
			String responseRst = httpClient.sendPost(PayConstants.PAYPAL_LAUNCH_CREATE_PAY_URL, requestMap);
	 		
			logger.info("xxxxxxxxxxxxx--->:"+responseRst);
//	 		request.setAttribute("htmlstr", cacheMap.get("formData"));
	 		request.setAttribute("htmlstr", responseRst);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------PaypalController.goPaypal.error>:{}", e);
		}
		return "topay";
	}
	
	
	private Map<String,String> getMapParams(HttpServletRequest request) {
		
		String cancelUrl = URLUtils.getBaseURl(request) + "/paypalNVP" + PAYPAL_CANCEL_URL;
		String successUrl = URLUtils.getBaseURl(request) + "/paypalNVP" + PAYPAL_SUCCESS_URL;
		String notifyUrl = URLUtils.getBaseURl(request) + "/paypalNVP" + PAYPAL_NOTIFY_URL;
		
		logger.info("------cancelUrl--->:"+cancelUrl);
		logger.info("------successUrl--->:"+successUrl);
		logger.info("------notifyUrl--->:"+notifyUrl);
		
		cancelUrl = cancelUrl.replace("localhost", "172.16.65.169");
		successUrl = successUrl.replace("localhost", "172.16.65.169");
		notifyUrl = notifyUrl.replace("localhost", "172.16.65.169");
		
		Map<String, String> params = new TreeMap<String, String>();
		params.put("payWayCode", "PAYPAL"); // 支付方式编码
		Date date = new Date();
		params.put("orderDate", DateUtils.convertDateToString(date, "yyyyMMdd")); // 订单日期（yyyyMMDD）
		params.put("orderTime", DateUtils.convertDateToString(date, DateUtils.DATE_TIME_PATTERN));// 订单时间(yyyyMMddHHmmss)
		params.put("payKey", PayConstants.PAYPAL_KEY); // 商户注册网关token
		params.put("orderIp", "172.16.65.169"); // 下单IP ???
		params.put("orderPeriod", "30"); // 订单有效期
		params.put("pageType", "login"); // login:表示papay需登陆
											// credit card..." ???
		params.put("returnUrl", successUrl); // 页面通知返回url
		params.put("notifyUrl", notifyUrl); // 后台消息通知Url
		params.put("cancelUrl", cancelUrl); // 用户付款取消时返回url
		params.put("remark", "launch diagdevice paypal"); // 支付备注
		params.put("field2", "a"); 		// 预留字段
		params.put("field3", "b"); 		// 预留字段
		params.put("field4", "c"); 		// 预留字段
		params.put("field5", "d"); 		// 预留字段
		
		return params;
	}
	
	/**
	 * 构建一个form表单
	 * @param paramMap  请求参数
	 * @param mth 请求方式 GET，POST...
	 * @param buttonName 提交按钮名称
	 * @param actionUrl 提交的地址
	 * @return
	 */
	public String buildReq(Map<String,String>paramMap,String mth,String buttonName,String actionUrl )
    {
        List<String> paramKey = new ArrayList<String>(paramMap.keySet());
        StringBuilder html = new StringBuilder();
        html.append("<form id=\"paysubmit\" name=\"paysubmit\" action=\"" + actionUrl + "\" method=\"" + mth+ "\">");
        for (int i = 0; i < paramKey.size(); i++)
        {
            String name = paramKey.get(i);
            Object obj = paramMap.get(name);
            String value ="";
            if(obj!=null) {
                value =(String) paramMap.get(name);
            }
            html.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        html.append("<input type=\"submit\" value=\"" + buttonName + "\" style=\"display:none;\"></form>");
        html.append("<script>document.forms['paysubmit'].submit();</script>");
        return html.toString();
    }
	
	/***
	 * 通过参数排序之后进行MD5加密得到签名 sign
	 */
	public String sortAndMd5Util(Map paramMap) {
    	SortedMap<String, Object> smap = new TreeMap<String, Object>(paramMap);
    	
    	StringBuffer stringBuffer = new StringBuffer();
    	for (Map.Entry<String, Object> m : smap.entrySet()) 
    	{
    	  Object value = m.getValue();
    	  
    	  if (value != null && StringUtils.isNotBlank(String.valueOf(value)))
    	  {
    		  stringBuffer.append(m.getKey()).append("=").append(value).append("&");
    	  }
    	}
    	stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
    	String argPreSign = stringBuffer.append("&paySecret=").append(PayConstants.PAYPAL_SECRET).toString();
    	
    	String signStr = MD5Util.MD5(argPreSign).toUpperCase();
    	logger.info("signStr>:"+signStr);
    	return signStr;
    }

	/**
	 * paypal支付回调
	 */
	@RequestMapping(value = PAYPAL_NOTIFY_URL, method = RequestMethod.GET)
	@ResponseBody
	public String notifyUrl(HttpServletRequest request) {
		String returnFlag = "success";
		try {
			String tradeStatus = request.getParameter("tradeStatus");
			String orderNo = request.getParameter("orderNo");
			logger.info(orderNo+"-------paypal支付notifyUrl-------tradeStatus>:"+tradeStatus);
			UserOrder uorder = new UserOrder();
			uorder.setOrderNo(orderNo);
			uorder = userOrderService.selectOne(uorder);
			
			if (uorder == null && "SUCCESS".equals(tradeStatus)) {
				Map<String,String[]> reqMap = request.getParameterMap();
				Map<String,String> notifyMap = new HashMap<String,String>();
				
				for (Map.Entry<String, String[]> entry : reqMap.entrySet()) {
					String key = entry.getKey();
					String [] arrVal = entry.getValue();
					notifyMap.put(key, arrVal[0]);
				}
				logger.info("----paypal.notify.map-->:" + notifyMap);
				saveOrderInfo(notifyMap);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("-----notifyUrl.errorInfo>:{}",e);
			returnFlag = "failed";
		}
		return returnFlag;
	}

	/**
	 * 支付成功 liping
	 * 先回调success地址，再回调notify地址
	 */
	@RequestMapping(value = PAYPAL_SUCCESS_URL, method = RequestMethod.GET)
	public String successPay(HttpServletRequest request) {
		
		
		String tradeStatus = request.getParameter("tradeStatus");
		logger.info("-------paypal支付successUrl-------tradeStatus>:"+tradeStatus);
		
		if ("SUCCESS".equals(tradeStatus)) {
			Map<String,String[]> reqMap = request.getParameterMap();
			Map<String,String> notifyMap = new HashMap<String,String>();
			
			for (Map.Entry<String, String[]> entry : reqMap.entrySet()) {
				String key = entry.getKey();
				String [] arrVal = entry.getValue();
				notifyMap.put(key, arrVal[0]);
			}
			logger.info("----paypal.notify.map-->:" + notifyMap);
			saveOrderInfo(notifyMap);
			
		}
		// 返回到成功页面
		return "success";
	}
	
	/**
     * 支付成功的返回
     * @param notifyMap 回调的数据
     * LIPING
     */
	private void saveOrderInfo(Map<String,String> notifyMap) {
		
		String orderNo   = notifyMap.get("orderNo");  // 自己的订单号
		String trxNo     = notifyMap.get("trxNo");    // launch支付平台流水号【网关支付流水】
		// String bankTrxNo = notifyMap.get("bankTrxNo");// PAYPAL支付流水号,launch支付平台那边没有开放给我们
		String orderPrice = notifyMap.get("orderPrice");// 订单金额
		String currency  = notifyMap.get("field1");   // 币种
		
        logger.info("---paypal.saveOrderInfo.orderPrice={},currency={},trxNo={},orderNo={}",
        		orderPrice,currency,trxNo,orderNo);
        
        // ---------获取缓存中的
     	String orderKey = AppConstant.DIAGDEVICE_ORDER_NOT_PAY_ORDER + ":" + orderNo;
 		Map<String, String> cacheMap = redisUtil.hmget(orderKey);
 		logger.info("---paypal.saveOrderInfo.cacheMap={}",cacheMap);
 		
 		String userId = cacheMap.get("userId");
 		String serialNo = cacheMap.get("serialNo");
 		String diagSoftPriceId = cacheMap.get("diagSoftPriceId");
 		
 		// 封装订单实体对象
 		UserOrder uOrder = new UserOrder();
 		uOrder.setOrderNo(orderNo);
 		uOrder.setSerialNo(serialNo);
 		uOrder.setUserId(Long.parseLong(userId));
 		uOrder.setPrice(new BigDecimal(orderPrice));
 		uOrder.setPayFrom(PayConstants.PAY_FROM_PAYPAL);
 		uOrder.setPayTime(new Date());
 		uOrder.setThirdTradeNo(trxNo);  // 直接存launch支付平台的网关流水号
 		uOrder.setCurrency(currency);
 		
 		//Integer result = userOrderService.save(uOrder);
 		int result = userOrderService.saveOrderAndDetail(uOrder,Long.parseLong(diagSoftPriceId));
		logger.info(  "---paypal.save.userOrder.result>:" + result);
		if (result > 0) {
			redisUtil.remove(orderKey);
			// 缓存消费token，消费token保存30分钟
			String token = AppConstant.getConsumerToken();
			redisUtil.set(AppConstant.DIAGDEVICE_CONSUMER_TOKEN + ":" + userId+orderNo, token,Constants.CONSUMER_TOKEN_EXPIRE_TIME);
			
			// 缓存中保存支付状态，APP端需要获取状态来跳转
			String key = AppConstant.DIAGDEVICE_RECHARGE_IS_PAY + ":" + orderNo;
			redisUtil.set(key, PayConstants.IS_PAY_YES + "", 60L * 30);
		}
	}
	
	/**
	 * 取消支付 TODO liping
	 */
	@RequestMapping(value = PAYPAL_CANCEL_URL, method = RequestMethod.GET)
	public String cancelPay() {
		logger.info("-------paypal 取消支付---------");
		return "cancel";
	}
	
	//-----------------------------------------------TEST----------------------------------------------------------------------
	
//	@SignValidate(option = SignValidate.LA2LSVal, modelName = "toPayTest")
	@RequestMapping(value = "/toPayTest/{diagSoftPriceId}/{price}/{serialNo}/{sign}/{userId}",method = {RequestMethod.POST,RequestMethod.GET})
	public String toPayTest(@PathVariable Long diagSoftPriceId,@PathVariable String serialNo, @PathVariable String price,
		    		@PathVariable String sign,@PathVariable String userId,HttpServletRequest request) {
			 
		    logger.info("paypalController.getUrl.diagSoftPriceId={},serialNo={},price={},sign={},userId={},"
		    			,diagSoftPriceId,serialNo,price,userId);
			AppResult appResult = new AppResult();

			try {

					String orderNo = OrderUtils.getConsumerNo(AppConstant.DIAGDEVICE_PP_ORDER_NO_PRE);
					
					Map<String, String> params =  getMapParamsTest(request);
					params.put("orderNo", orderNo); // 订单编号
					params.put("orderPrice", "0.01"); // 订单金额 , 【单位:元】
					params.put("productName", "AUDI-A7"); // 商品名称
					params.put("field1", "USD"); 	// 币种
					
					String paypalSign = sortAndMd5Util(params);
					
					params.put("sign", paypalSign); // 支付签名

					
					String htmlStr = buildReq(params,"post","submit",PayConstants.PAYPAL_LAUNCH_CREATE_PAY_URL);
					logger.info("-----------paypal--test--result>:" + htmlStr);
					request.setAttribute("htmlstr", htmlStr);

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("---------------PaypalController.toPayTest.error>:{}", e);
				setResult(appResult, AppCodeConstant.ERROR);
			}
			return "topay"; 
	}
	
	/**
	 * paypal支付回调
	 */
	@RequestMapping(value = PAYPAL_NOTIFY_URL_TEST, method = RequestMethod.GET)
	@ResponseBody
	public String notifyUrlTest(HttpServletRequest request) {
		try {
			Map<String,String[]> reqMap = request.getParameterMap();
			logger.info("---test.aaa-reqMap>:" + reqMap);
			
			for (Map.Entry<String, String[]> entry : reqMap.entrySet()) {
				String key = entry.getKey();
				String [] arrVal = entry.getValue();
				logger.info("----paypal.notify.value-->:"+key+"="+arrVal[0]);
			}
			
			String tradeStatus = request.getParameter("tradeStatus");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("-----notifyUrlTest.errorInfo>:{}",e);
		}
		return "success";
	}
	
	/**
	 * 支付成功 liping
	 */
	@RequestMapping(value = PAYPAL_SUCCESS_URL_TEST, method = RequestMethod.GET)
	public String successPayTest(HttpServletRequest request) {
		
		logger.info("-------paypal 支付success---------");
		Map<String,String[]> reqMap = request.getParameterMap();
		logger.info("---test.aaa-reqMap>:" + reqMap);
		
		for (Map.Entry<String, String[]> entry : reqMap.entrySet()) {
			String key = entry.getKey();
			String [] arrVal = entry.getValue();
			logger.info("----paypal.notify.value-->:"+key+"="+arrVal[0]);
		}
		return "success";
	}
	
	private Map<String,String> getMapParamsTest(HttpServletRequest request) {
		
		String cancelUrl = URLUtils.getBaseURl(request) + "/paypalNVP" + PAYPAL_CANCEL_URL;
		String successUrl = URLUtils.getBaseURl(request) + "/paypalNVP" + PAYPAL_SUCCESS_URL_TEST;
		String notifyUrl = URLUtils.getBaseURl(request) + "/paypalNVP" + PAYPAL_NOTIFY_URL_TEST;
		
		logger.info("------cancelUrl--->:"+cancelUrl);
		logger.info("------successUrl--->:"+successUrl);
		logger.info("------notifyUrl--->:"+notifyUrl);
		
//		cancelUrl = cancelUrl.replace("localhost", "172.16.65.169");
//		successUrl = successUrl.replace("localhost", "172.16.65.169");
//		notifyUrl = notifyUrl.replace("localhost", "172.16.65.169");
		
		Map<String, String> params = new TreeMap<String, String>();
		params.put("payWayCode", "PAYPAL"); // 支付方式编码
		Date date = new Date();
		params.put("orderDate", DateUtils.convertDateToString(date, "yyyyMMdd")); // 订单日期（yyyyMMDD）
		params.put("orderTime", DateUtils.convertDateToString(date, DateUtils.DATE_TIME_PATTERN));// 订单时间(yyyyMMddHHmmss)
		params.put("payKey", PayConstants.PAYPAL_KEY); // 商户注册网关token
		params.put("orderIp", "172.16.65.169"); // 下单IP ???
		params.put("orderPeriod", "30"); // 订单有效期
		params.put("pageType", "login"); // login:表示papay需登陆
											// credit card..." ???
		params.put("returnUrl", successUrl); // 页面通知返回url
		params.put("notifyUrl", notifyUrl); // 后台消息通知Url
		params.put("cancelUrl", cancelUrl); // 用户付款取消时返回url
		params.put("remark", "launch diagdevice paypal"); // 支付备注
		params.put("field2", "a"); 		// 预留字段
		params.put("field3", "b"); 		// 预留字段
		params.put("field4", "c"); 		// 预留字段
		params.put("field5", "d"); 		// 预留字段
		
		return params;
	}
	
}
