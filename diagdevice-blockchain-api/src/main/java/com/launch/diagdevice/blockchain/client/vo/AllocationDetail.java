package com.launch.diagdevice.blockchain.client.vo;

import lombok.Data;

/**
 * accounts、level、job、radios、sub_way必填，其它选填
 * 
 * @author ouxiangrui
 *
 */
@Data
public class AllocationDetail {
	/**
	 * 分账的优先级
	 */
	private int level=1;
	/**
	 * 分账人职位
	 */
	private String job;
	/**
	 * 分账比率（小数）
	 */
	private double radios;
	/**
	 * 分账方式 ，0为按比例分配，1为按总量分配
	 */
	private int sub_way;
	/**
	 * 定额分配重置方式 0不重置， 1按天重置， 2按月重置（定额分配要填）
	 */
	private int quota_way;
	/**
	 * 定额分配重置时间 一般是0~6点 （定额分配要填）
	 */
	private int reset_time;
	
	private int status;
	/**
	 * 分账人账户详情
	 */
	private Account account;

	/**
	 * name、alipay、telephone必填，其它选填
	 * 
	 * @author ouxiangrui
	 *
	 */
	@Data
	public class Account {
		/**
		 * 分账人姓名（必须跟支付宝实名认证的名字一致）
		 */
		private String name;
		/**
		 * 分账人银行卡（银行卡收款）
		 */
		private String bank_card;
		/**
		 * 分账人微信openID(微信收款)
		 */
		private String we_chat;
		/**
		 * 分账人支付宝账号（支付宝收款）
		 */
		private String alipay;
		/**
		 * 分账人手机号码
		 */
		private String telephone;
		/**
		 * 分账人邮箱
		 */
		private String email;
	}
}
