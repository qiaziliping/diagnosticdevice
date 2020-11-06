package com.launch.diagdevice.blockchain.client.vo;

import lombok.Data;

/**
 * account、no、telephone、app_id必填，其它选填
 * 
 * @author ouxiangrui
 *
 */
@Data
public class UpdateAccountRequest {
	/**
	 * 组织名称
	 */
	private String org_name;
	/**
	 * 用户名
	 */
	private String user_name;
	/**
	 * 凭证
	 */
	private String secret;
	/**
	 * 应用id
	 */
	private String app_id;
//	/**
//	 * 分账人姓名（必须跟支付宝实名认证的名字一致）
//	 */
//	private String name;
//
//	/**
//	 * 分账人银行卡（银行卡收款）
//	 */
//	private String bank_card;
//	/**
//	 * 分账人微信openID(微信收款)
//	 */
//	private String we_chat;
//	/**
//	 * 分账人支付宝账号（支付宝收款）
//	 */
//	private String alipay;
//	/**
//	 * 分账人手机号码
//	 */
//	private String telephone;
//	/**
//	 * 分账人邮箱
//	 */
//	private String email;
	
	private Account account;

}
