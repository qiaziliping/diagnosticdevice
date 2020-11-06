package com.launch.diagdevice.entity;

import com.launch.diagdevice.common.model.PagingEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色实体类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole extends PagingEntity {

	
	
	/** 角色名称 */
	private String roleName;
	/** role code <=> role identifier -- used by shiro tool */
	private String roleCode;
	/** 角色类型:admin管理员自己建立 */
	private String roleType;
	/** 是否激活 0: no 1:yes */
	private Integer isValid;
	
	
	
	
	
	
	private static final long serialVersionUID = 1L;

}
