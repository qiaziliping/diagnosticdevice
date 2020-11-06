package com.launch.diagdevice.service;

import java.util.List;
import java.util.Map;

import com.launch.diagdevice.common.service.BaseService;
import com.launch.diagdevice.entity.SysUserRole;
import com.launch.diagdevice.entity.vo.SysUserRoleVo;

/**
 * 系统用户角色关联Service
 * @author LIPING
 * @version 0.0.1
 * @since 2018年10月24日
 */
public interface SysUserRoleService extends BaseService<SysUserRole>{

	/**
	 * 根据用户ID删除用户角色
	 */
	int deleteByUserId(Long userId);
	/**
	 * 根据角色ID删除用户角色
	 */
	int deleteByRoleId(Long roleId);
	/**
	 * 根据条件查询vo对象
	 * LIPING
	 */
	List<SysUserRoleVo> selectVoByCondition(Map<String, Object> condition);

	


	
}
