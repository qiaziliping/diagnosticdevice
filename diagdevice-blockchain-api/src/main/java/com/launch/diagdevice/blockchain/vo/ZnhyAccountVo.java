package com.launch.diagdevice.blockchain.vo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

/**
 * 智能合约账户信息表VO对象，在配置规则中用到
 * 
 * @author LIPING
 *
 */
@Data
public class ZnhyAccountVo implements Serializable {
	
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	private String createDate;

	/**
	 * 更新时间
	 */
	private String updateDate;

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
	/** 账户名 */
	private String accountName;
	/** 账户类型 */
	private Integer accountType;
	
	/** 账户类型,旧的账户类型，修改时需要用到此字段【需要把原来的账户置空】 */
	private Integer accountTypeOld;
	
	
	
	public void setCreateDate(Object objectTime) {
		if (objectTime != null) {
			if (objectTime instanceof Date) {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				this.createDate = format.format(objectTime);
			} else {
				this.createDate = String.valueOf(objectTime);
			}
		}
	}
	
	public void setUpdateDate(Object objectTime) {
		if (objectTime != null) {
			if (objectTime instanceof Date) {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				this.updateDate = format.format(objectTime);
			} else {
				this.updateDate = String.valueOf(objectTime);
			}
		}
	}

}
