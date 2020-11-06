package com.launch.diagdevice.entity;

import com.launch.diagdevice.common.model.PagingEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统资源(权限)实体类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysAuthority extends PagingEntity {

	
	
	/** 父ID */
	private Long parentId;
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
	
	
	
	private static final long serialVersionUID = 1L;

}
