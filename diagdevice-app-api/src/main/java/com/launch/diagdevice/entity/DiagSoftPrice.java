package com.launch.diagdevice.entity;

import java.math.BigDecimal;

import com.launch.diagdevice.common.model.PagingEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 软件价格对应币种的金额-实体类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DiagSoftPrice extends PagingEntity {

	
	
	/** 软件ID */
	private Long softId;
	/** 币种，如：RMB/USD */
	private String currency;
	/** 类型：1单次，2包月，3包季，4包年 */
	private Integer buyType;
	/** 对应价格 */
	private BigDecimal price;
	
	
	/*public Integer getBuyType() {
		if (buyType == null) buyType = 1; 
		return buyType;
	}*/
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}
