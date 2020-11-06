package com.launch.diagdevice.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.alibaba.dubbo.config.annotation.Service;
import com.launch.diagdevice.system.dao.SysUserDao;
import com.launch.diagdevice.system.model.SysUser;
import com.launch.diagdevice.system.service.SysUserManager;

@Service(interfaceClass = SysUserManager.class)
public class SysUserManagerImpl implements SysUserManager {
	@Autowired
	private SysUserDao sysUserDao;

	@Override
	@Cacheable(cacheNames={"diaglog_bus"},keyGenerator="cacheKeyGenerator",unless="#result == null")
	public SysUser getSysUser(Integer userId) {
		return sysUserDao.getSysUser(userId);
	}

}
