package com.launch.diagdevice.entity;


import java.util.Date;

import com.launch.diagdevice.common.model.PagingEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备售后操作记录实体类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeviceOptRecord extends PagingEntity {

	
	
	/** 设备ID */
	private Long deviceId;
	/** 记录时间 */
	private Date recordDate;
	/** 记录人 */
	private String recordName;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
