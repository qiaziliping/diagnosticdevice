package com.launch.diagdevice.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.alipay.api.AlipayResponse;
import com.alipay.api.internal.util.AlipaySignature;
import com.launch.diagdevice.common.configure.SignValidate;
import com.launch.diagdevice.common.constant.AppCodeConstant;
import com.launch.diagdevice.common.constant.AppConstant;
import com.launch.diagdevice.common.constant.Constants;
import com.launch.diagdevice.common.constant.PayConstants;
import com.launch.diagdevice.common.result.AppResult;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.common.util.OrderUtils;
import com.launch.diagdevice.common.utils.RedisUtil;
import com.launch.diagdevice.entity.DiagSoftPrice;
import com.launch.diagdevice.entity.UserOrder;
import com.launch.diagdevice.pay.entity.AlipayModel;
import com.launch.diagdevice.pay.service.AlipayService;
import com.launch.diagdevice.service.ConsumerRecordService;
import com.launch.diagdevice.service.DiagSoftPriceService;
import com.launch.diagdevice.service.UserExtService;
import com.launch.diagdevice.service.UserOrderService;
import com.launch.diagdevice.system.service.KeyValueExpandManager;


@RestController
@RequestMapping("/alipay")
public class AlipayController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(AlipayController.class);

	@Reference(interfaceClass = AlipayService.class)
	private AlipayService alipayService;

//	@Reference(interfaceClass = RechargeRecordService.class)
//	private RechargeRecordService rechargeRecordService;
	
	@Reference(interfaceClass = ConsumerRecordService.class)
	private ConsumerRecordService consumerRecordService;
	
	@Reference(interfaceClass = UserOrderService.class)
	private UserOrderService userOrderService;

	@Reference(interfaceClass = UserExtService.class)
	private UserExtService userExtService;
	
	@Reference(interfaceClass=KeyValueExpandManager.class)
	private KeyValueExpandManager keyValueExpandManager;
	
	@Reference(interfaceClass = DiagSoftPriceService.class)
    private DiagSoftPriceService diagSoftPriceService;

	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 获取订单的状态
	 * LIPING
	 */
	@SignValidate(option = SignValidate.LA2LSVal, modelName = "api/getRechargeStatus")
	@RequestMapping(value = "/api/getRechargeStatus/{orderNo}/{sign}/{userId}", method = { RequestMethod.POST,RequestMethod.GET })
	public @ResponseBody String getRechargeStatus(@PathVariable String orderNo,@PathVariable String sign,@PathVariable String userId) {
		AppResult appResult = new AppResult();
		JsonHelper jsonhelp = new JsonHelper();

		logger.info("---api/getRechargeStatus request param>:[orderNo={},sign={},userId={}]",orderNo,sign,userId);
		try {

			if (!StringUtils.isEmpty(orderNo)) {

				String key = AppConstant.DIAGDEVICE_RECHARGE_IS_PAY + ":" + orderNo;
				String isPay = redisUtil.get(key);
				if (!StringUtils.isEmpty(isPay)) {
					isPay = String.valueOf(PayConstants.IS_PAY_YES);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("isPay", isPay);
					appResult.setData(map);
				} else {
					// 订单号不存在
					setResultOld(appResult, AppCodeConstant.ORDER_NO_NOT_EXIST);
				} 
				
			} else {
				// 缺少参数
				setResultOld(appResult, AppCodeConstant.PARAM_LACK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("---获取充值记录的状态.errorInfo>:{}", e);
			setResult(appResult, AppCodeConstant.ERROR);
		}
		return jsonhelp.toJsonStr(appResult);
	}

	/**
	 * AlipayResponse成功getBody的结果
	 * {
			"alipay_trade_precreate_response": {
			"code": "10000",
			"msg": "Success",
			"out_trade_no": "201808072041589545",
			"qr_code": "https:\/\/qr.alipay.com\/bax02816xxtfg7rlfnn600b0"
			},
			"sign": "dEb11OFM3nGhGrbsVxqNybtQTUCope1m7rVFUabsdV7dID/i/nNsyILJHMsPvBDu9471S+UwVMHzDYV4G/3etI1+6Ft1DrJj4YOLm2RLy75aevvKiC42cD0OGKN2QK6NLDcxUDFIakKyRExI0qdHsv12GyOOmXNkheN62LZLvE3vvPab1YNHmWlG2x7DanZ3TaNg3n3fAER/68unyHaoNpn+rJ3f2IRbbY4+LzsURoJIUIaF8agNipPAVlqLEfm9i9Kqg9Ar05HvFkso5grHc9UsBKst2tVfybQd/QydkOquW54OQcS2Umr3K/SPqubzsDmLJH19opaOoZhrti9fHg=="
	   }
	 * 页面显示二维码>: http://qr.liantu.com/api.php?&w=200&text=返回的二维码连接
	 * 扫码支付（获取支付二维码连接）
	 * LIPING
	 */
	/**
	 * @param diagSoftPriceId 诊断软件价格ID
	 * @param price 价格
	 * @param serialNo 序列号
	 * @param sign 签名
	 * @param userId 登录用户ID
	 */
	@SuppressWarnings("unchecked")
	@SignValidate(option = SignValidate.LA2LSVal, modelName = "api/preparePay")
	@RequestMapping(value = "/api/preparePay/{diagSoftPriceId}/{price}/{serialNo}/{sign}/{userId}", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String preparePay(HttpServletRequest request,@PathVariable Long diagSoftPriceId,@PathVariable String serialNo, @PathVariable String price,
			@PathVariable String sign,@PathVariable String userId) {

		logger.info("----api/preparePay request param->:[diagSoftPriceId={},serialNo={},price={},sign={},userId={}]", 
				diagSoftPriceId,serialNo,price, sign, userId);
		AppResult appResult = new AppResult();
		JsonHelper jsonHelper = new JsonHelper();

		String totalAmount = "0";
		try {

			// 设置基础参数
			AlipayModel aliModel = getAlipayModel();
			logger.info("--aliModel:={}-->:",aliModel);

			if (StringUtils.isNotBlank(userId) && price != null && StringUtils.isNotBlank(serialNo)) {

				// 判断序列号是否和登录时的序列号一致
				String snKey = AppConstant.DIAGDEVICE_APPUSER_SERIAL_NO + ":" + userId;
				String cacheSN = redisUtil.get(snKey);
				if (!serialNo.equals(cacheSN)) {
					setResultOld(appResult, AppCodeConstant.SERIAL_NO_ERROR);
					return jsonHelper.toJsonStr(appResult);
				}
				
				
				// 根据软件价格ID（diagSoftPriceId）获取价格
				DiagSoftPrice dsPriceModel = diagSoftPriceService.selectById(diagSoftPriceId);
				
				// 验证币种是否为 RMB,支付宝只支持人民币支付
				String currency = dsPriceModel.getCurrency();
				if (!Constants.CURRENCY_RMB.equals(currency)) {
					setResultOld(appResult, AppCodeConstant.CURRENCY_NOT_SUPPORT_ALIPAY);
					return jsonHelper.toJsonStr(appResult);
				}
				
				BigDecimal dsPrice = dsPriceModel.getPrice();
				BigDecimal rechargePrice = new BigDecimal(price); // 前端传过来的价格
				
				// 判断是否相等
				if (rechargePrice.compareTo(dsPrice) == 0) { 
					totalAmount = rechargePrice.toString();
					// 订单金额
					aliModel.setTotalAmount(totalAmount);
					// 订单号 
					// String orderNo = OrderUtils.getOrderCode("");
					// 消费编号（订单号）
					String orderNo = OrderUtils.getConsumerNo(AppConstant.DIAGDEVICE_CONSUMER_NO_PRE);
					aliModel.setOutTradeNo(orderNo);

					// 预支付，获取二维码接口
					AlipayResponse aliResponse = alipayService.preparePay(aliModel);

					logger.info("alipayService--subMsg={}--Code={}--body={}>:", aliResponse.getSubMsg(),
							aliResponse.getCode(), aliResponse.getBody());

					Integer code = Integer.parseInt(aliResponse.getCode());
					if (aliResponse.isSuccess()) {

						Map<String, String> rrMap = new HashMap<String, String>();
						rrMap.put("userId", String.valueOf(userId));
						rrMap.put("orderNo", orderNo);
						rrMap.put("price", price.toString());
						rrMap.put("serialNo", serialNo);
						rrMap.put("currency", currency);
						rrMap.put("diagSoftPriceId", String.valueOf(diagSoftPriceId));

						// 未支付订单不存入数据库,所以先存入缓存中
						String orderKey = AppConstant.DIAGDEVICE_ORDER_NOT_PAY_ORDER + ":" + orderNo;
						redisUtil.hmset(orderKey, rrMap);
						// 因为二维码的失效时间为2小时，而且修改不了，所以设置过期时间为3小时
						// redisUtil.setExpire(orderKey, 3 * 3600L); // 不设置过期时间原因： 支付宝回调成功之后再删除数据（存在支付成功但没回调情况），不设置自动过期

						// 解析支付宝预支付响应结果，获取二维码
						String body = aliResponse.getBody();
						Map<String, Object> bodyMap = jsonHelper.fromObj(body, Map.class);
						Map<String, String> atpResponse = (Map<String, String>) bodyMap
								.get("alipay_trade_precreate_response");
						// qr_code 二维码连接地址
						String qr_code = atpResponse.get("qr_code");

						Map<String, String> rstMap = new HashMap<String, String>();
						rstMap.put("qrCode", qr_code);
						rstMap.put("orderNo", orderNo);
						appResult.setData(rstMap);
					} else {
						appResult.setCode(code);
						appResult.setMessage(aliResponse.getMsg());
					}

				} else {
					// 充值金额有误
					setResult(appResult, AppCodeConstant.RECHARGE_MONEY_ERROR);
					return jsonHelper.toJsonStr(appResult);
				}
			} else {
				// 缺少参数
				setResultOld(appResult, AppCodeConstant.PARAM_LACK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("支付宝预支付获取二维码连接Exception>:{}", e);
			setResult(appResult, AppCodeConstant.ERROR);
		}
		return jsonHelper.toJsonStr(appResult);
	}
	
	/** 获取基础参数 */
	private AlipayModel getAlipayModel() {
		
		AlipayModel aliModel = new AlipayModel();
		aliModel.setOpenApiDomain(PayConstants.ALIPAY_OPEN_API_DOMAIN);
		aliModel.setAppId(PayConstants.ALIPAY_APP_ID);
		aliModel.setAppPrivateKey(PayConstants.ALIPAY_APP_PRIVATE_KEY);
		aliModel.setPublicKey(PayConstants.ALIPAY_PUBLIC_KEY);
		aliModel.setFormat(PayConstants.ALIPAY_FORMAT);
		aliModel.setCharset(PayConstants.ALIPAY_CHARSET);
		aliModel.setSignType(PayConstants.ALIPAY_SIGN_TYPE);

		aliModel.setSubject(PayConstants.ALIPAY_SUBJECT);
		aliModel.setStoreId(PayConstants.ALIPAY_STORE_ID);
		aliModel.setNotifyUrl(PayConstants.ALIPAY_NOTIFY_URL);
		aliModel.setTimeoutExpress(PayConstants.ALIPAY_TIMEOUT_EXPRESS); 
		return aliModel;
	}
	
	// 扫码--支付成功后回调方法
	@RequestMapping(value = "/api/prepareNotifyUrl", method = RequestMethod.POST)
	public String notifyUrl(HttpServletRequest request, HttpServletResponse httpResponse) {
		// 获取支付宝POST过来反馈信息
		Map<String, String> requestMap = toMap(request);
		logger.info("--prepareNotifyUrl.requestMap>:" + requestMap);

		int isPayFlag = PayConstants.IS_PAY_NO;
		// 2.封装必须参数
		String out_trade_no = request.getParameter("out_trade_no"); // 商户订单号
		String tradeStatus = request.getParameter("trade_status"); // 交易状态
		logger.info("out_trade_no={}:orderType={}", out_trade_no, tradeStatus);
					
		try {
			// 非常重要,验证回调的正确性,是不是支付宝发的.并且呢还要避免重复通知.
			// requestMap.remove("sign_type");

			// 3.签名验证(对支付宝返回的数据验证，确定是支付宝返回的)
			boolean signVerified = AlipaySignature.rsaCheckV1(requestMap, PayConstants.ALIPAY_PUBLIC_KEY,
					PayConstants.ALIPAY_CHARSET, PayConstants.ALIPAY_SIGN_TYPE);
			logger.info(out_trade_no + "-------signVerified-result>:" + signVerified);

			// 4.对验签进行处理
			if (signVerified) { // 验签通过
				if (tradeStatus.equals("TRADE_SUCCESS")) isPayFlag = PayConstants.IS_PAY_YES;
				else isPayFlag = PayConstants.IS_PAY_NO;

			} else { // 验签不通过
				logger.info("alipay sign fail>:");
				isPayFlag = PayConstants.IS_PAY_NO;
			}
			
			// 缓存中保存支付状态，APP端需要获取状态来跳转
			String key = AppConstant.DIAGDEVICE_RECHARGE_IS_PAY + ":" + out_trade_no;
			redisUtil.set(key, isPayFlag + "", 60L * 30);

			// 根据充值记录修改账户金额
			//int result = userExtService.updateMoneyByRechargeRecord(record, rrMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("notifyUrl.支付宝验签失败>:{}", e);
			isPayFlag = PayConstants.IS_PAY_NO;
		}

		// 支付成功，返回success给支付宝,防止重复回调
		if (isPayFlag == PayConstants.IS_PAY_YES) {
			logger.info("--------------return alipay success----");
			
			// 获取支付宝交易订单号
			String tradeNo = requestMap.get("trade_no"); 
			// 保存用户订单信息
			return saveUserOrder(out_trade_no,tradeNo);
			// return "success";
		} else {
			return "failed";
		}
	}

	/**
	 * 保存用户订单信息
	 * @param out_trade_no 自己订单号
	 * @param tradeNo 支付宝订单号
	 * LIPING
	 */
	private String saveUserOrder(String out_trade_no,String tradeNo) {
		// 获取缓存中的
		String orderKey = AppConstant.DIAGDEVICE_ORDER_NOT_PAY_ORDER + ":" + out_trade_no;
		Map<String, String> rrMap = redisUtil.hmget(orderKey);
		logger.info(tradeNo+"------------redis get not pay order>:" + rrMap);
		
		String userId = rrMap.get("userId");
		String price = rrMap.get("price");
		String serialNo = rrMap.get("serialNo");
		String currency = rrMap.get("currency");
		String diagSoftPriceId = rrMap.get("diagSoftPriceId");
		
		UserOrder uOrder = new UserOrder();
		uOrder.setOrderNo(out_trade_no);
		uOrder.setSerialNo(serialNo);
		uOrder.setUserId(Long.parseLong(userId));
		uOrder.setPrice(new BigDecimal(price));
		uOrder.setPayFrom(PayConstants.PAY_FROM_ALIPAY);
		uOrder.setPayTime(new Date());
		uOrder.setThirdTradeNo(tradeNo);
		uOrder.setCurrency(currency);
		
//		Integer result = userOrderService.save(uOrder);
		int result = userOrderService.saveOrderAndDetail(uOrder,Long.parseLong(diagSoftPriceId));
		logger.info(  "---save userOrder result >:" + result);
		if (result > 0) {
			redisUtil.remove(orderKey);
			// 缓存消费token，消费token保存30分钟
			String token = AppConstant.getConsumerToken();
			redisUtil.set(AppConstant.DIAGDEVICE_CONSUMER_TOKEN + ":" + userId+out_trade_no, token,Constants.CONSUMER_TOKEN_EXPIRE_TIME);
			return "success";
		}
		return "failed";
	}

	public Map<String, String> toMap(HttpServletRequest request) {
		logger.info("request.getQueryString()>：" + request.getQueryString());
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		return params;
	}
	
	
	// 老接口，兼容以前已经发布的，后期要删除
	@SuppressWarnings("unchecked")
	@SignValidate(option = SignValidate.LA2LSVal, modelName = "api/preparePay")
	@RequestMapping(value = "/api/preparePay/{price}/{serialNo}/{sign}/{userId}", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String preparePay(HttpServletRequest request,@PathVariable String serialNo, @PathVariable String price,
			@PathVariable String sign,@PathVariable String userId) {

		logger.info("----api/preparePay request param->:[serialNo={},price={},sign={},userId={}]", serialNo,price, sign, userId);
		AppResult appResult = new AppResult();
		JsonHelper jsonHelper = new JsonHelper();

		String totalAmount = "0";
		try {

			// 设置基础参数
			AlipayModel aliModel = getAlipayModel();
			logger.info("--aliModel:={}-->:",aliModel);

			if (StringUtils.isNotBlank(userId) && price != null && StringUtils.isNotBlank(serialNo)) {

				// 判断序列号是否和登录时的序列号一致
				String snKey = AppConstant.DIAGDEVICE_APPUSER_SERIAL_NO + ":" + userId;
				String cacheSN = redisUtil.get(snKey);
				if (!serialNo.equals(cacheSN)) {
					setResultOld(appResult, AppCodeConstant.SERIAL_NO_ERROR);
					return jsonHelper.toJsonStr(appResult);
				}
				
				
				// 诊断价格
				//BigDecimal carPrice = AppPropConstant.CAR_PRICE;
				
				String value = keyValueExpandManager.queryValueByKey(Constants.DIAGDEVICE_FIXED_PRICE);
				BigDecimal carPrice = new BigDecimal(value);
				
				BigDecimal rechargePrice = new BigDecimal(price);
				if (rechargePrice.compareTo(carPrice) == 0) { // 判断是否相等
					totalAmount = rechargePrice.toString();
					// 订单金额
					aliModel.setTotalAmount(totalAmount);
					// 订单号 
					// String orderNo = OrderUtils.getOrderCode("");
					// 消费编号（订单号）
					String orderNo = OrderUtils.getConsumerNo(AppConstant.DIAGDEVICE_CONSUMER_NO_PRE);
					aliModel.setOutTradeNo(orderNo);

					// 预支付，获取二维码接口
					AlipayResponse aliResponse = alipayService.preparePay(aliModel);

					logger.info("alipayService--subMsg={}--Code={}--body={}>:", aliResponse.getSubMsg(),
							aliResponse.getCode(), aliResponse.getBody());

					Integer code = Integer.parseInt(aliResponse.getCode());
					if (aliResponse.isSuccess()) {

						Map<String, String> rrMap = new HashMap<String, String>();
						rrMap.put("userId", String.valueOf(userId));
						rrMap.put("orderNo", orderNo);
						rrMap.put("price", price.toString());
						rrMap.put("serialNo", serialNo);

						// 未支付订单不存入数据库,所以先存入缓存中
						String orderKey = AppConstant.DIAGDEVICE_ORDER_NOT_PAY_ORDER + ":" + orderNo;
						redisUtil.hmset(orderKey, rrMap);
						// 因为二维码的失效时间为2小时，而且修改不了，所以设置过期时间为3小时
						redisUtil.setExpire(orderKey, 3 * 3600L);

						// 解析支付宝预支付响应结果，获取二维码
						String body = aliResponse.getBody();
						Map<String, Object> bodyMap = jsonHelper.fromObj(body, Map.class);
						Map<String, String> atpResponse = (Map<String, String>) bodyMap
								.get("alipay_trade_precreate_response");
						// qr_code 二维码连接地址
						String qr_code = atpResponse.get("qr_code");

						Map<String, String> rstMap = new HashMap<String, String>();
						rstMap.put("qrCode", qr_code);
						rstMap.put("orderNo", orderNo);
						appResult.setData(rstMap);
					} else {
						appResult.setCode(code);
						appResult.setMessage(aliResponse.getMsg());
					}

				} else {
					// 充值金额有误
					setResult(appResult, AppCodeConstant.RECHARGE_MONEY_ERROR);
					return jsonHelper.toJsonStr(appResult);
				}
			} else {
				// 缺少参数
				setResultOld(appResult, AppCodeConstant.PARAM_LACK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("支付宝预支付获取二维码连接Exception>:{}", e);
			setResult(appResult, AppCodeConstant.ERROR);
		}
		return jsonHelper.toJsonStr(appResult);
	}

}
