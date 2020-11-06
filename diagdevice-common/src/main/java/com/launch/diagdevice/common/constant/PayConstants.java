package com.launch.diagdevice.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 支付相关常量
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月30日
 */
@PropertySource(value={"classpath:diagdevice.properties"},encoding="UTF-8")
@Component
public class PayConstants {
	
	
	/** 支付来源:1:支付宝 */
	public static int PAY_FROM_ALIPAY = 1; 
	/** 支付来源:，2：微信 */
	public static int PAY_FROM_WECHAT = 2; 
	/** 支付来源:，3：paypal贝宝 */
	public static int PAY_FROM_PAYPAL = 3; 
	/** 支付方式:0:货到付款 1:在线支付(暂时都是1)  */
	public static int PAY_TYPE_ONLINE = 1; 
	/** 是否支付:0:未支付 */
	public static int IS_PAY_NO = 0; 
	/** 是否支付:1:已支付  */
	public static int IS_PAY_YES = 1; 
	/** 是否支付:2:支付失败  */
	public static int IS_PAY_FAIL = 2;
	
	
	
	/** 支付宝网关名 */
	public static String ALIPAY_OPEN_API_DOMAIN;
	/** 支付宝APPID */
	public static String ALIPAY_APP_ID;
	/** 应用私钥（你的商户私钥且转PKCS8格式） */
	public static String ALIPAY_APP_PRIVATE_KEY;
	/** 支付宝公钥 */
	public static String ALIPAY_PUBLIC_KEY;
	/** 编码 */
	public static String ALIPAY_CHARSET;
	/** 返回格式 */
	public static String ALIPAY_FORMAT;
	/**  签名类型: RSA->SHA1withRsa,RSA2->SHA256withRsa */
	public static String ALIPAY_SIGN_TYPE;
	/**  4.服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 */
	public static String ALIPAY_NOTIFY_URL;
	/** (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店消费” */
	public static String ALIPAY_SUBJECT;
	/** 交易超时时间，可为空 */
	public static String ALIPAY_TIMEOUT_EXPRESS;
	/** 支付成功code 10000 */
	public static int APLPAY_CODE_SUCCESS = 10000;
	/** (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持  */
	public static String ALIPAY_STORE_ID = "10";
	
	
	/** paypal 支付 key，在launch平台生成 */
	public static String PAYPAL_KEY;
	/** paypal 支付 秘钥，在launch平台生成 */
	public static String PAYPAL_SECRET;
	/** launch paypal支付平台 创建支付的URL */
	public static String PAYPAL_LAUNCH_CREATE_PAY_URL;
	
	
	
	@Value("${paypal.launch.create.pay.url}")
	public void setPAYPAL_LAUNCH_CREATE_PAY_URL(String pAYPAL_LAUNCH_CREATE_PAY_URL) {
		PayConstants.PAYPAL_LAUNCH_CREATE_PAY_URL = pAYPAL_LAUNCH_CREATE_PAY_URL;
	}
	@Value("${paypal.key}")
	public void setPAYPAL_KEY(String pAYPAL_KEY) {
		PayConstants.PAYPAL_KEY = pAYPAL_KEY;
	}
	@Value("${paypal.secret}")
	public void setPAYPAL_SECRET(String pAYPAL_SECRET) {
		PayConstants.PAYPAL_SECRET = pAYPAL_SECRET;
	}

	@Value("${alipay.timeout_express}")
	public void setALIPAY_TIMEOUT_EXPRESS(String aLIPAY_TIMEOUT_EXPRESS) {
		PayConstants.ALIPAY_TIMEOUT_EXPRESS = aLIPAY_TIMEOUT_EXPRESS;
	}
	
	@Value("${alipay.subject}")
	public void setALIPAY_SUBJECT(String aLIPAY_SUBJECT) {
		PayConstants.ALIPAY_SUBJECT = aLIPAY_SUBJECT;
	}
	@Value("${alipay.notify_url}")
	public void setALIPAY_NOTIFY_URL(String aLIPAY_NOTIFY_URL) {
		PayConstants.ALIPAY_NOTIFY_URL = aLIPAY_NOTIFY_URL;
	}
	@Value("${alipay.open_api_domain}")
	public void setALIPAY_OPEN_API_DOMAIN(String aLIPAY_OPEN_API_DOMAIN) {
		PayConstants.ALIPAY_OPEN_API_DOMAIN = aLIPAY_OPEN_API_DOMAIN;
	}
	@Value("${alipay.app_id}")
	public void setALIPAY_APP_ID(String aLIPAY_APP_ID) {
		PayConstants.ALIPAY_APP_ID = aLIPAY_APP_ID;
	}
	@Value("${alipay.app_private_key}")
	public  void setALIPAY_APP_PRIVATE_KEY(String aLIPAY_APP_PRIVATE_KEY) {
		PayConstants.ALIPAY_APP_PRIVATE_KEY = aLIPAY_APP_PRIVATE_KEY;
	}
	@Value("${alipay.public_key}")
	public  void setALIPAY_PUBLIC_KEY(String aLIPAY_PUBLIC_KEY) {
		PayConstants.ALIPAY_PUBLIC_KEY = aLIPAY_PUBLIC_KEY;
	}
	@Value("${alipay.charset}")
	public  void setALIPAY_CHARSET(String aLIPAY_CHARSET) {
		PayConstants.ALIPAY_CHARSET = aLIPAY_CHARSET;
	}
	@Value("${alipay.format}")
	public  void setALIPAY_FORMAT(String aLIPAY_FORMAT) {
		PayConstants.ALIPAY_FORMAT = aLIPAY_FORMAT;
	}
	@Value("${alipay.sign_type}")
	public void setALIPAY_SIGN_TYPE(String aLIPAY_SIGN_TYPE) {
		PayConstants.ALIPAY_SIGN_TYPE = aLIPAY_SIGN_TYPE;
	}
	
	
	
	

}
