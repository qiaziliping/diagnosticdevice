package com.launch.diagdevice.entity;

import com.launch.diagdevice.common.model.PagingEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色资源（权限）实体类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoleAuthority extends PagingEntity {

	
	
	/** 角色ID */
	private Long roleId;
	/** 资源ID */
	private Long resourceId;
	
	
	
	
	
	
	private static final long serialVersionUID = 1L;

}
