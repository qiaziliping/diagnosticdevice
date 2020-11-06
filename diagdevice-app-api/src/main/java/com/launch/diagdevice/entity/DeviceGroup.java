package com.launch.diagdevice.entity;

import com.launch.diagdevice.common.model.PagingEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备分组实体类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeviceGroup extends PagingEntity {

	
	
	/** 组名 */
	private String groupName;
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
