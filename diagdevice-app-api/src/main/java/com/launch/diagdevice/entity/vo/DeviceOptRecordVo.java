package com.launch.diagdevice.entity.vo;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

/**
 * 设备售后操作记录VO
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月18日
 */
@Data
public class DeviceOptRecordVo implements Serializable {

	
	
	private Long id;
	/** 设备ID */
	private Long deviceId;
	/** 记录时间 */
	private String recordDate;
	/** 记录人 */
	private String recordName;
	
	private String remark;
	private Integer status;
	private String createUserId;
	private String createTime;
	
	
	
	
	public void setId(Object objId) {
		if (objId instanceof String) {
			this.id = Long.parseLong(String.valueOf(objId));
		} else {
			this.id = (Long) objId;
		}
	}

	public void setRecordDate(Object recordDate) {
		if (recordDate instanceof Date) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			this.recordDate = format.format(recordDate);
		} else {
			this.recordDate = String.valueOf(recordDate);
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
