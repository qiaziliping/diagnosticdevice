package com.launch.diagdevice.entity;

import com.launch.diagdevice.common.model.PagingEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色关联-实体类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserRole extends PagingEntity {

	
	
	/** 用户ID */
	private Long userId;
	/** 角色ID */
	private Long roleId;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
