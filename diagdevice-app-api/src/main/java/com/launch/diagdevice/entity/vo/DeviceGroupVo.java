package com.launch.diagdevice.entity.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 设备分组VO
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
public class DeviceGroupVo implements Serializable {

	
	
	
	private Long id;
	/** 组名 */
	private String groupName;
	
	
	
	
	public void setId(Object id) {
		if (id instanceof String) {
			this.id = Long.parseLong(String.valueOf(id));
		} else if (id instanceof Long){
			this.id = (Long)id;
		}
	}





	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
