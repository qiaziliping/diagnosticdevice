package com.launch.diagdevice.entity;

import com.launch.diagdevice.common.model.PagingEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备实体类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Device extends PagingEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 设备序列号 */
	private String serialNo;
	/** 设备类型 */
	private String deviceType;
	/** 设备分组ID */
	private Long deviceGroupId;
	/** 设备位置 */
	private String location;
	/** 设备所有者 */
	private String owner;
	/** 设备凭证(图片或压缩包地址) */
	private String voucher;
	
	/** 分配关联关系id--add 20180926 By LIPING */
	private String allocationId;
	
	

}
