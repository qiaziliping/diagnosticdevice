package com.launch.diagdevice.common.constant;


import com.launch.diagdevice.common.util.StringUtil;

public class AppConstant {
	
	
	/**
	 * 存放到Redis的token前缀
	 */
	public static final String DIAGDEVICE_APPUSER_TOKEN = "diagdevice_appuser_token";
	/** 当前登录用户操作的序列号 */
	public static final String DIAGDEVICE_APPUSER_SERIAL_NO = "diagdevice_appuser_serial_no";
	/** 消费者token */
	public static final String DIAGDEVICE_CONSUMER_TOKEN = "diagdevice_consumer_token";
	/** APP验证前缀 */
	public static final String DIAGDEVICE_VERIFY_CODE = "diagdevice_verify_code";
	/** 管理后台验证码前缀 */
	public static final String DIAGDEVICE_VERIFY_CODE_ADMIN = "diagdevice_verify_code_admin";
	/** 充值记录是否已付款 */
	public static final String DIAGDEVICE_RECHARGE_IS_PAY = "diagdevice_recharge_is_pay";
	/** 订单记录未支付订单 */
	public static final String DIAGDEVICE_ORDER_NOT_PAY_ORDER = "diagdevice_order_not_pay_order";
	/** paypal支付参数 */
	public static final String DIAGDEVICE_PAYPAL_PARAMS = "diagdevice_paypal_params";
	
	/** 消费编号前缀 */
	public static final String DIAGDEVICE_CONSUMER_NO_PRE = "11";
	/** PAYPAL支付的订单号前缀 */
	public static final String DIAGDEVICE_PP_ORDER_NO_PRE = "13";
	
	/**
	 * 生成指定长度32位的随机大写字母符串
	 * @return
	 */
	public static final String getToken() {
		String token = StringUtil.randomString(21) + Long.toHexString(System.currentTimeMillis());
		return token.toUpperCase();
	}
	
	/**
	 * 获取消费token
	 * LIPING
	 */
	public static final String getConsumerToken() {
		String token = StringUtil.randomString(21) + Long.toHexString(System.currentTimeMillis());
		return token.toUpperCase();
	}
	
	
	public static void main(String[] args) {
		String sessionid = getConsumerToken();
		System.out.println(sessionid);
	}
	

}
