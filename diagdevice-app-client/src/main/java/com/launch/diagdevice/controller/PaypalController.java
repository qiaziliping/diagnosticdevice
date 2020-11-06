package com.launch.diagdevice.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.launch.diagdevice.common.configure.SignValidate;
import com.launch.diagdevice.common.constant.AppCodeConstant;
import com.launch.diagdevice.common.constant.AppConstant;
import com.launch.diagdevice.common.constant.Constants;
import com.launch.diagdevice.common.constant.PayConstants;
import com.launch.diagdevice.common.result.AppResult;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.common.util.OrderUtils;
import com.launch.diagdevice.common.util.URLUtils;
import com.launch.diagdevice.common.utils.RedisUtil;
import com.launch.diagdevice.entity.DiagSoft;
import com.launch.diagdevice.entity.DiagSoftPrice;
import com.launch.diagdevice.entity.UserOrder;
import com.launch.diagdevice.pay.common.constant.PaypalPaymentIntent;
import com.launch.diagdevice.pay.common.constant.PaypalPaymentMethod;
import com.launch.diagdevice.pay.service.PaypalService;
import com.launch.diagdevice.service.DiagSoftPriceService;
import com.launch.diagdevice.service.DiagSoftService;
import com.launch.diagdevice.service.UserOrderService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;


/**
 * paypal支付 测试 
 * 真正paypal支付在PaypalNVPController中
 * @author liping
 *
 */
@Controller
@RequestMapping("/paypal")
public class PaypalController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(AlipayController.class);
	
	public static final String PAYPAL_SUCCESS_URL = "/pay/success";
    public static final String PAYPAL_CANCEL_URL = "/pay/cancel";
    
    /** 支付币种 */
    public static final String PAYPAL_CURRENCY = "USD";
    /** 支付回调URL */
    public static final String PAYPAL_NOTIFY_URL = "/notifyUrl";
    
    
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
     * 获取支付链接
     * @param diagSoftPriceId 诊断软件价格ID主键（获取诊断软件价格列表时已经返回）
     * @param serialNo 设备序列号
     * @param price 价格
     * @param sign 签名
     * @param userId 登录用户ID
     */
    @SignValidate(option = SignValidate.LA2LSVal, modelName = "getPayUrl")
    @RequestMapping(value = "/getPayUrl/{diagSoftPriceId}/{price}/{serialNo}/{sign}/{userId}",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody String getPayUrl(@PathVariable Long diagSoftPriceId,@PathVariable String serialNo, @PathVariable String price,
    		@PathVariable String sign,@PathVariable String userId,HttpServletRequest request) {
    	logger.info("paypalController.getUrl.diagSoftPriceId={},serialNo={},price={},sign={},userId={},"
    			,diagSoftPriceId,serialNo,price,userId);
    	AppResult appResult = new AppResult();
		JsonHelper jsonHelper = new JsonHelper();
    	
    	String cancelUrl = URLUtils.getBaseURl(request)  +"/paypal"+ PAYPAL_CANCEL_URL;
        String successUrl = URLUtils.getBaseURl(request)  + "/paypal"+PAYPAL_NOTIFY_URL;
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
					
					logger.info("paypalController.getUrl.currency={},cancelUrl={},successUrl={},diagSoftName={}"
							,currency,cancelUrl,successUrl,diagSoft.getSoftName());
					// 4、调用创建支付对象接口，得到支付对象
					Payment payment = paypalService.createPayment(diagSoft.getSoftName(),dsPrice,currency, 
		                    PaypalPaymentMethod.paypal,PaypalPaymentIntent.sale,
		                    cancelUrl, successUrl);
					
					for (Links links : payment.getLinks()) {
						// 5、如果url类型为是approval_url，则返回支付链接，并将订单信息保存到缓存中
		                if(links.getRel().equals("approval_url")){
		                	
		                	// 支付ID，每一次都不一样
		                	String paymentId = payment.getId();
		                	String orderNo = OrderUtils.getConsumerNo(AppConstant.DIAGDEVICE_PP_ORDER_NO_PRE);
		                	
		                	Map<String, String> rrMap = new HashMap<String, String>();
							rrMap.put("userId", String.valueOf(userId));
							rrMap.put("price", price.toString());
							rrMap.put("serialNo", serialNo);
							rrMap.put("orderNo", orderNo);

							// 6、未支付订单不存入数据库,所以先存入缓存中
							String orderKey = AppConstant.DIAGDEVICE_ORDER_NOT_PAY_ORDER + ":" + paymentId;
							redisUtil.hmset(orderKey, rrMap);
							// 因为二维码的失效时间为2小时，而且修改不了，所以设置过期时间为3小时
							redisUtil.setExpire(orderKey, 3 * 3600L);
							
							
		                	// 客户付款登陆地址
		                    // return "redirect:" + links.getHref();
							
							// 6、返回paypal支付地址和订单号
							Map<String,Object> map = new HashMap<String,Object>();
							map.put("href", links.getHref());
							map.put("orderNo", orderNo);
		                	appResult.setData(map);
		                }
		            }
				} else {
					// 支付金额有误
					setResult(appResult, AppCodeConstant.RECHARGE_MONEY_ERROR);
					return jsonHelper.toJsonStr(appResult);
				}
	        	
	        } else {
				// 缺少参数
				setResultOld(appResult, AppCodeConstant.PARAM_LACK);
			}
        	
        } catch (PayPalRESTException e) {
        	e.printStackTrace();
            logger.error("---------------PaypalController.getPayUrl.error>:{}",e);
            setResult(appResult, AppCodeConstant.ERROR);
        }
        return jsonHelper.toJsonStr(appResult);
//         return "redirect:/paypal/index";
    }
    
    /**
     * paypal支付回调
     */
    @RequestMapping(value = PAYPAL_NOTIFY_URL,method = RequestMethod.GET)
    public String notifyUrl(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        try {
        	
            Payment payment = paypalService.executePayment(paymentId, payerId);
            
            logger.info("----paymeng>:"+payment);
            logger.info("----payment.getState()>:"+payment.getState());
            // 1、如果支付状态是被认可的[返回approved表示成功]
            if(payment.getState().equals("approved")) {
            	// 2、支付成功，保存订单信息
            	String orderNo = saveOrderInfo(payment);
            	
            	// 3、缓存中保存支付状态，APP端需要获取状态来跳转
				String key = AppConstant.DIAGDEVICE_RECHARGE_IS_PAY + ":" + orderNo;
				redisUtil.set(key, PayConstants.IS_PAY_YES + "", 60L * 30);
            	
            	// 4、返回到成功页面
                return "success";
            }
            
        } catch (PayPalRESTException e) {
        	e.printStackTrace();
            logger.error("----------errorInfo>:{}",e);
        }
        return "redirect:/paypal/index";
    }
    
    /**
     * 支付成功的返回参数payment如注释：payment-success
     * @return orderNo 订单号
     * LIPING
     */
	private String saveOrderInfo(Payment payment) {
		
		// 支付ID
		String paymentId = payment.getId();
		
		List<Transaction> tranList = payment.getTransactions();
		Transaction tran = tranList.get(0);
		// 订单总价
        String total = tran.getAmount().getTotal();
        // 获取币种
        String currency = tran.getAmount().getCurrency();
		 // 交易号（第三方订单号），对应userOrder 的 thirdTradeNo
        String transationId = tran.getRelatedResources().get(0).getSale().getId();
        
        logger.info("---total={},currency={},transationId={}",total,currency,transationId);
        
        // ---------获取缓存中的
     	String orderKey = AppConstant.DIAGDEVICE_ORDER_NOT_PAY_ORDER + ":" + paymentId;
 		Map<String, String> rrMap = redisUtil.hmget(orderKey);
 		logger.info("---saveOrderInfo.rrMap={}",rrMap);
 		
 		String userId = rrMap.get("userId");
 		//String price = rrMap.get("price");
 		String serialNo = rrMap.get("serialNo");
 		String orderNo = rrMap.get("orderNo");
 		
 		
 		UserOrder uOrder = new UserOrder();
 		uOrder.setOrderNo(orderNo);
 		uOrder.setSerialNo(serialNo);
 		uOrder.setUserId(Long.parseLong(userId));
 		uOrder.setPrice(new BigDecimal(total));
 		uOrder.setPayFrom(PayConstants.PAY_FROM_PAYPAL);
 		uOrder.setPayTime(new Date());
 		uOrder.setThirdTradeNo(transationId);
 		uOrder.setCurrency(currency);
 		
 		Integer result = userOrderService.save(uOrder);
 		
 		if (result > 0) {
 			redisUtil.remove(orderKey);
 			// 缓存消费token，消费token保存30分钟
 			String token = AppConstant.getConsumerToken();
 			redisUtil.set(AppConstant.DIAGDEVICE_CONSUMER_TOKEN + ":" + userId+orderNo, token,Constants.CONSUMER_TOKEN_EXPIRE_TIME);
 		}
 		logger.info(  "---paypal save userOrder result >:" + result);
        return orderNo;
	}
    
    
    
    
    //--------------------------以下为测试接口-----------------------------------------
    
    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String index(){
    	
        return "index";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/pay")
    public String pay(HttpServletRequest request){
        String cancelUrl = URLUtils.getBaseURl(request)  +"/paypal"+ PAYPAL_CANCEL_URL;
        String successUrl = URLUtils.getBaseURl(request)  + "/paypal"+PAYPAL_SUCCESS_URL;
        try {
        	
        	String diagSoftName = "AUDI";
        	// 币种不支持CNY人民币，支持欧元EUR
        	BigDecimal price = new BigDecimal(1.00);
            Payment payment = paypalService.createPayment(
            		diagSoftName,
            		price,
                    "USD", 
//                    "EUR", 
                    PaypalPaymentMethod.paypal, 
                    PaypalPaymentIntent.sale,
                    cancelUrl, 
                    successUrl);
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                	// 客户付款登陆地址
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
        	e.printStackTrace();
            logger.error("---------errorInfo>:{}",e);
        }
        return "redirect:/paypal/index";
    }
	
    /**
     * 取消支付
     * TODO
     * liping
     */
    @RequestMapping(value = PAYPAL_CANCEL_URL,method = RequestMethod.GET)
    public String cancelPay(){
    	logger.info("-------paypal 取消支付---------");
        return "cancel";
    }
	
    
    @RequestMapping(value = PAYPAL_SUCCESS_URL,method = RequestMethod.GET)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        try {
        	
            Payment payment = paypalService.executePayment(paymentId, payerId);
            
            logger.info("----paymeng>:"+payment);
            logger.info("----payment.getState()>:"+payment.getState());
            // 如果支付状态是被认可的
            if(payment.getState().equals("approved")){
//            	saveOrderInfo(payment);
                return "success";
            }
            
        } catch (PayPalRESTException e) {
        	e.printStackTrace();
            logger.error("-----errorInfo>:{}",e);
        }
        return "redirect:/paypal/index";
    }

    
    
	
	// 支付成功返回payment的参数  payment-success
	/*{
		  "id": "PAY-0K326865FH189764VLP3Z76A",
		  "intent": "sale",
		  "payer": {
		    "payment_method": "paypal",
		    "status": "UNVERIFIED",
		    "payer_info": {
		      "email": "597721793@qq.com",
		      "first_name": "li",
		      "last_name": "ping",
		      "payer_id": "VFQ7F78ZB2MD4",
		      "country_code": "C2",
		      "shipping_address": {
		        "recipient_name": "li ping",
		        "line1": "NO 1 Nan Jin Road",
		        "city": "Shanghai",
		        "country_code": "C2",
		        "postal_code": "200000",
		        "state": "Shanghai"
		      }
		    }
		  },
		  "cart": "01B07295D76093439",
		  "transactions": [
		    {
		      "transactions": [],
		      "related_resources": [
		        {
		          "sale": {
		            "id": "3E288894980632818",
		            "amount": {
		              "currency": "USD",
		              "total": "100.00",
		              "details": {
		                "subtotal": "100.00"
		              }
		            },
		            "payment_mode": "INSTANT_TRANSFER",
		            "state": "completed",
		            "protection_eligibility": "ELIGIBLE",
		            "protection_eligibility_type": "ITEM_NOT_RECEIVED_ELIGIBLE,UNAUTHORIZED_PAYMENT_ELIGIBLE",
		            "transaction_fee": {
		              "currency": "USD",
		              "value": "3.70"
		            },
		            "parent_payment": "PAY-0K326865FH189764VLP3Z76A",
		            "create_time": "2018-11-23T06:38:38Z",
		            "update_time": "2018-11-23T06:38:38Z",
		            "links": [
		              {
		                "href": "https://api.sandbox.paypal.com/v1/payments/sale/3E288894980632818",
		                "rel": "self",
		                "method": "GET"
		              },
		              {
		                "href": "https://api.sandbox.paypal.com/v1/payments/sale/3E288894980632818/refund",
		                "rel": "refund",
		                "method": "POST"
		              },
		              {
		                "href": "https://api.sandbox.paypal.com/v1/payments/payment/PAY-0K326865FH189764VLP3Z76A",
		                "rel": "parent_payment",
		                "method": "GET"
		              }
		            ]
		          }
		        }
		      ],
		      "amount": {
		        "currency": "USD",
		        "total": "100.00",
		        "details": {}
		      },
		      "payee": {
		        "email": "ping.li@cnlaunch.com",
		        "merchant_id": "2KTQHS9KDXMW8"
		      },
		      "description": "payment description",
		      "item_list": {
		        "items": [],
		        "shipping_address": {
		          "recipient_name": "ping li",
		          "line1": "NO 1 Nan Jin Road",
		          "city": "Shanghai",
		          "country_code": "C2",
		          "postal_code": "200000",
		          "state": "Shanghai"
		        }
		      }
		    }
		  ],
		  "state": "approved",
		  "create_time": "2018-11-23T06:38:39Z",
		  "links": [
		    {
		      "href": "https://api.sandbox.paypal.com/v1/payments/payment/PAY-0K326865FH189764VLP3Z76A",
		      "rel": "self",
		      "method": "GET"
		    }
		  ]
		}*/
}
