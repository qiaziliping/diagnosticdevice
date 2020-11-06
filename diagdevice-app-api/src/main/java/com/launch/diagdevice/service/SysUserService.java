package com.launch.diagdevice.service;

import java.util.List;

import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.service.BaseService;
import com.launch.diagdevice.entity.SysUser;
import com.launch.diagdevice.entity.vo.SysUserVo;

/**
 * 系统用户Service
 * @author LIPING
 * @version 0.0.1
 * @since 2018年10月24日
 */
public interface SysUserService extends BaseService<SysUser>{

	/**
	 * 保存用户和用户角色关联信息
	 * LIPING
	 */
	int saveUserAndUR(SysUser user, List<String> listIds);
	/**
	 * 修改用户和用户角色关联信息
	 * LIPING
	 * @param isUpdateRole true表示需要修改角色，false不需要
	 */
	int updateUserAndUR(SysUser sysUser, List<String> listIds, Boolean isUpdateRole);
	/**
	 * 根据用户ID
	 * 删除用户和用户角色关联信息
	 * LIPING
	 */
	int deleteUserAndUR(Long userId);
	/**
	 * 分页查询用户列表
	 */
	PagingData<SysUserVo> selectPage(SysUser sysUser);


	
}
