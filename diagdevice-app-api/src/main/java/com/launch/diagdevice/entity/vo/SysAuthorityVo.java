package com.launch.diagdevice.entity.vo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 系统资源(权限)Vo
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
public class SysAuthorityVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Long id;
	/** 父ID */
	private Long parentId;
	/** 父编码 */
	private String parentResourceCode;
	/** 排序 */
	private Integer sort;
	/** 资源名称 */
	private String resourceName;
	/** 资源类型 1预留 2：一级菜单 3：二级菜单 4：三级菜单 5：权限 */
	private Integer resourceType;
	/** 资源URL */
	private String resourceUrl;
	/** 资源编码 format as function:permission */
	private String resourceCode;
	/** 页面路由 */
	private String module;
	/** 图标路径 */
	private String icon;
	
	private String remark;
	private Integer status;
	private String createTime;
	private String updateTime;
	private String createUserId;
	private String updateUserId;
	
	private List<SysAuthorityVo> childList;
	/** 是否选择，0未选择，1已选择 */
	private Integer isChecked;
	
	public void setId(Object id) {
		if (null != id) {
			if (id instanceof String) {
				this.id = Long.parseLong(id.toString());
			} else if (id instanceof Long) {
				this.id = Long.parseLong(id.toString());
			}
		}
	}
	
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
