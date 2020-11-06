package com.launch.diagdevice.pay.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 阿里支付实体类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月30日
 */
@Data
public class AlipayModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/** 支付宝网关名 */
	private String openApiDomain;
	/** 支付宝APPID */
	private String appId;
	/** 应用私钥（你的商户私钥且转PKCS8格式） */
	private String appPrivateKey;
	/** 支付宝公钥 */
	private String publicKey;
	/** 编码 */
	private String charset;
	/** 返回格式 */
	private String format;
	/**  签名类型: RSA->SHA1withRsa,RSA2->SHA256withRsa */
	private String signType;
	/**  4.服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 */
	private String notifyUrl;
	/** (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持  */
	private String storeId;
	/** 必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店消费” */
	private String subject;
	/** 订单号 */
	private String outTradeNo;
	/** 交易超时时间 */
	private String timeoutExpress;
	/** 交易金额 */
	private String totalAmount;
	
	
	
	public String getCharset() {
		if (isBank(charset)) {
			charset = "UTF-8";
		}
		return charset;
	}
	
	public String getFormat() {
		if (isBank(format)) {
			format = "json";
		}
		return format;
	}
	
	public String getSignType() {
		if (isBank(signType)) {
			signType = "RSA2";
		}
		return signType;
	}
	
	private boolean isBank(String param) {
		if (param == null || "".equals(param.trim())) {
			return false;
		}
		return true;
	}

}
