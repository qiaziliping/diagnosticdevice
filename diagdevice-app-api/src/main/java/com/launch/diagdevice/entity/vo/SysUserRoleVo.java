package com.launch.diagdevice.entity.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户角色关联-实体类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
public class SysUserRoleVo implements Serializable {

	
	
	/** 用户ID */
	private Long userId;
	/** 角色ID */
	private Long roleId;
	/** 角色名称 */
	private String roleName;
	/** 角色编码 */
	private String roleCode;
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
