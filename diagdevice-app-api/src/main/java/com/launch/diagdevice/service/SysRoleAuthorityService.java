package com.launch.diagdevice.service;

import java.util.List;

import com.launch.diagdevice.common.service.BaseService;
import com.launch.diagdevice.entity.SysRoleAuthority;

/**
 * 系统角色权限Service
 * @author LIPING
 * @version 0.0.1
 * @since 2018年10月24日
 */
public interface SysRoleAuthorityService extends BaseService<SysRoleAuthority>{

	/**
	 * 批量保存
	 * 先根据roleId删除权限，在保存
	 */
	void batchSave(List<SysRoleAuthority> raList, Long roleId);

	/**
	 * 根据角色Id删除角色权限
	 */
	int deleteByRoleId(Long roleId);
	/**
	 * 根据权限Id删除角色权限
	 */
	int deleteByAuthId(Long authId);
	/**
	 * 根据角色Id查询角色权限
	 */
	List<SysRoleAuthority> selectByRoleId(Long roleId);

	

	
}
