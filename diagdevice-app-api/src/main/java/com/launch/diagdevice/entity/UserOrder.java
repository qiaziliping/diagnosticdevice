package com.launch.diagdevice.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.launch.diagdevice.common.model.PagingEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户订单实体
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月10日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserOrder extends PagingEntity {

	public UserOrder() {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2384700756451231286L;

	/** 用户ID */
	private Long userId;
	/** 设备序列号 */
	private String serialNo;
	/** 订单编号 */
	private String orderNo;
	/** 诊断价格 */
	private BigDecimal price;
	/** 支付来源:1:支付宝，2：微信 */
	private Integer payFrom;
	/** 支付时间 */
	private Date payTime;
	
	/** 第三方订单号 */
	private String thirdTradeNo;
	/** 币种，RMB,USD,EUR */
	private String currency;
	
	

}
