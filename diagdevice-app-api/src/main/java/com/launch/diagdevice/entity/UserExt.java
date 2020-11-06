package com.launch.diagdevice.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.launch.diagdevice.common.model.PagingEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserExt extends PagingEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4678428770967515870L;

	/** 用户ID */
	private Long userId;
	/** 生日（yyyy-MM-dd） */
	private String birthday;
	/** 语言code，如：zh-cn */
	private String lanCode;
	/** 所属机构ID */
	private Long orgId;
	/** 所属机构名称 */
	private String orgName;
	/** 用户钱包金额 */
	private BigDecimal userMoney;
	/** 冻结金额 */
	private BigDecimal lockMoney;
	/** 最后登录时间 */
	private Date lastTime;
	/** 账号是否被锁： 0否，1是 */
	private Integer isLock;
	/** 校验用户登录信息 */
	private String sessionId;
	
	
	
	
	

}
