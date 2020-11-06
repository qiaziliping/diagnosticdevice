package com.launch.diagdevice.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

/**
 * 软件价格对应币种VO
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
public class DiagSoftPriceVo implements Serializable {

	
	
	private Long id;
	/** 软件ID */
	private Long softId;
	/** 产品类型ID，软件表未冗余产品类型名称，将在枚举常量类中 */
	private Long pdtTypeId;
	
	/** 产品类型名称 */
	private String pdtType;
	
	/** 币种，如：RMB/USD */
	private String currency;
	/** 类型：1单次，2包月，3包季，4包年 */
	private Integer buyType;
	/** 对应价格 */
	private BigDecimal price;
	
	/** 软件名称 */
	private String softName;
	/** 软件适用区域 */
	private String softApplicableArea;
	
	private String createTime;
	
	public void setId(Object objId) {
		if (objId instanceof String) {
			this.id = Long.parseLong(String.valueOf(objId));
		} else {
			this.id = (Long) objId;
		}
	}
	
	public void setCreateTime(Object objectTime) {
		if (objectTime instanceof Date) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.createTime = format.format(objectTime);
		} else {
			this.createTime = String.valueOf(objectTime);
		}
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}
