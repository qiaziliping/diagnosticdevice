package com.launch.diagdevice.entity.request;

import java.math.BigDecimal;

import com.launch.diagdevice.common.model.request.RequestPaging;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 车型软件价格Request
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DiagSoftPriceRequest extends RequestPaging {

	DiagSoftPriceRequest() {
		status = Integer.valueOf(0);
	}
	
	/** 币种，如：RMB/USD */
	private String currency;
	/** 类型：1单次，2包月，3包季，4包年 */
	private Integer buyType;
	/** 对应价格 */
	private BigDecimal price;
	
	
	private Integer status;
	
	/** 软件ID */
	private Long softId;
	/** 软件名称 */
	private String softName;
	/** 是否上架（0是，1否）默认上架 */
	private Integer isActive;
	
	/** 产品类型ID */
	private Long pdtTypeId;
	
	/** 软件适用区域 */
	private String softApplicableArea;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}
