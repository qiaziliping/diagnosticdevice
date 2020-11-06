package com.launch.diagdevice.system.dao;

import com.launch.diagdevice.system.model.SysUser;

public interface SysUserDao {
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public SysUser getSysUser(Integer userId);
}
