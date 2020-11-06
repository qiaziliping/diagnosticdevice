package com.launch.diagdevice.blockchain.client.vo;

import java.io.Serializable;

import lombok.Data;
@Data
public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6001182067818938764L;
	/**
	 * 钱包地址id
	 */
	private String no;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 手机号
	 */
	private String bank_card;
	/**
	 * 微信账号
	 */
	private String we_chat;
	/**
	 * 支付宝账号
	 */
	private String alipay;
	/**
	 * 手机号
	 */
	private String telephone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 应用id
	 */
	private String app_id;

}
