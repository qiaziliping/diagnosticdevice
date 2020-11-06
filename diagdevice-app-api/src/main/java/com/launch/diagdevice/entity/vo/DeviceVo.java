package com.launch.diagdevice.entity.vo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

/**
 * 设备实体类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
public class DeviceVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 设备序列号 */
	private Long id;
	/** 设备序列号 */
	private String serialNo;
	/** 设备类型 */
	private String deviceType;
	/** 设备分组ID */
	private Long deviceGroupId;
	/** 设备分组名称 */
	private String deviceGroupName;
	/** 设备位置 */
	private String location;
	/** 设备所有者 */
	private String owner;
	/** 设备凭证(图片或压缩包地址) */
	private String voucher;
	
	/** 分配关联关系id--add 20180926 By LIPING */
	private String allocationId;
	
	private String remark;
	private Integer status;
	private String createTime;
	private String updateTime;
	private String createUserId;
	private String updateUserId;
	
	
	public void setCreateTime(Object objectTime) {
		if (null != objectTime) {
			if (objectTime instanceof Date) {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				this.createTime = format.format(objectTime);
			} else {
				this.createTime = String.valueOf(objectTime);
			}
		}
	}
	
	public void setUpdateTime(Object objectTime) {
		if (null != objectTime) {
			if (objectTime instanceof Date) {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				this.updateTime = format.format(objectTime);
			} else {
				this.updateTime = String.valueOf(objectTime);
			}
		}
	}
	

}
