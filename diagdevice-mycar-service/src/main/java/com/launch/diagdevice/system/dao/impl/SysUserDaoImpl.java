package com.launch.diagdevice.system.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.launch.diagdevice.system.dao.SysUserDao;
import com.launch.diagdevice.system.dao.mapper.SysUserMapper;
import com.launch.diagdevice.system.model.SysUser;

@Repository
public class SysUserDaoImpl implements SysUserDao {
	@Autowired
	private SysUserMapper mapper;

	@Override
	public SysUser getSysUser(Integer userId) {
		return mapper.getSysUser(userId);
	}
}
