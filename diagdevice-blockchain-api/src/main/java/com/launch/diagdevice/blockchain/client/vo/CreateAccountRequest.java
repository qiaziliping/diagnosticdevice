package com.launch.diagdevice.blockchain.client.vo;

import lombok.Data;

/**
 * 用户账号对象 name必须为真实姓名 手机号必填 支付宝账号必填 邮箱等其它字段选填
 * 
 * @author ouxiangrui
 *
 */
@Data
public class CreateAccountRequest {
	/**
	 * 联盟组织
	 */
	private String org_name;
	/**
	 * 联盟用户
	 */
	private String user_name;
	/**
	 * 用户证书
	 */
	private String secret;
	
	/**
	 * 应用id
	 */
	private String app_id;
//	/**
//	 * 姓名
//	 */
//	private String name;
//	/**
//	 * 手机号
//	 */
//	private String bank_card;
//	/**
//	 * 微信账号
//	 */
//	private String we_chat;
//	/**
//	 * 支付宝账号
//	 */
//	private String alipay;
	/**
	 * 手机号
	 */
	private String telephone;
	/**
	 * 邮箱
	 */
	private String email;

}
