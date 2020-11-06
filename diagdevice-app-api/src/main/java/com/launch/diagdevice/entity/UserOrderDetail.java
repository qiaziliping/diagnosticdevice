package com.launch.diagdevice.entity;

import com.launch.diagdevice.common.model.PagingEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户订单详情实体
 * @author LIPING
 * @version 0.0.1
 * @since 2019.03.13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserOrderDetail extends PagingEntity {

	
	public UserOrderDetail() {
	}


	/** 订单主键ID */
	private Long orderId;
	/** 诊断软件价格ID */
	private Long diagSoftPriceId;
	/** 软件名称 */
	private String softName;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8914109742165840070L;

}
