package com.launch.diagdevice.blockchain.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 智能合约账户信息表，在配置规则中用到
 * 
 * @author ouxiangrui
 *
 */
@Data
public class ZnhyAccount implements Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = -2358206895914537079L;
    /**
	 * 表记录id
	 */
	private Integer id;
	/**
	 * 分账人姓名（必须跟支付宝实名认证的名字一致）
	 */
	private String name;
	/**
	 * 分账人银行卡（银行卡收款）
	 */
	private String bankCard;
	/**
	 * 分账人微信openID(微信收款)
	 */
	private String weChat;
	/**
	 * 分账人支付宝账号（支付宝收款）
	 */
	private String alipay;
	/**
	 * 分账人手机号码
	 */
	private String telephone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 更新时间
	 */
	private Date updateDate;

	/**
	 * 创建者
	 */
	private String creator;

	/**
	 * 更新者
	 */
	private String updator;
	/**
	 * 系统分配的钱包地址id
	 */
	private String accountId;
	
	/**
	 * paypal账户
	 */
	private String paypal;

}
