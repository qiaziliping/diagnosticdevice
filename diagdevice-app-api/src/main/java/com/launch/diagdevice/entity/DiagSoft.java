package com.launch.diagdevice.entity;

import com.launch.diagdevice.common.model.PagingEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 车型软件价格实体类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DiagSoft extends PagingEntity {

	
	
	/** 软件ID */
	private Long softId;
	/** 产品类型ID，软件表未冗余产品类型名称，将在枚举常量类中 */
	private Long pdtTypeId;
	/** 软件名称 */
	private String softName;
	/** 是否上架（0是，1否）默认上架 */
	private Integer isActive;
	
	/** 软件适用区域 */
	private String softApplicableArea;
	
	
	/*public Integer getIsActive() {
		if (null == isActive) isActive = 0;
		return isActive;
	}*/
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}
