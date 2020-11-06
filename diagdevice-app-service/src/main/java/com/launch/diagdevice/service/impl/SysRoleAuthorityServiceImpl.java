package com.launch.diagdevice.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.launch.diagdevice.dao.SysRoleAuthorityDao;
import com.launch.diagdevice.entity.SysRoleAuthority;
import com.launch.diagdevice.service.SysRoleAuthorityService;

@Service(interfaceClass = SysRoleAuthorityService.class)
@Transactional(readOnly = true)
public class SysRoleAuthorityServiceImpl implements SysRoleAuthorityService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysRoleAuthorityDao sysRoleAuthorityDao;

	@Override
	public SysRoleAuthority selectById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysRoleAuthority selectOne(SysRoleAuthority model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer save(SysRoleAuthority e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer update(SysRoleAuthority e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(SysRoleAuthority e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysRoleAuthority> selectByIndex(SysRoleAuthority model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void batchSave(Collection<SysRoleAuthority> entities) {
		if (entities == null || entities.size() == 0) {
			logger.warn("sysRoleAuthorityService batchSave,but entities is null,entities={}",entities);
			return;
		}
		sysRoleAuthorityDao.batchSave(entities);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void batchSave(List<SysRoleAuthority> raList, Long roleId) {
		if (raList == null || roleId == null) {
			logger.warn("sysRoleAuthorityService batchSave,but parm is null,raList={},roleId={}",raList,roleId);
			return;
		}
		// 1、先删除角色权限
		deleteByRoleId(roleId);
		// 2、再保存
		batchSave(raList);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public int deleteByRoleId(Long roleId) {
		
		return sysRoleAuthorityDao.deleteByRoleId(roleId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public int deleteByAuthId(Long authId) {
		
		return sysRoleAuthorityDao.deleteByAuthId(authId);
	}

	@Override
	public List<SysRoleAuthority> selectByRoleId(Long roleId) {
		return sysRoleAuthorityDao.selectByRoleId(roleId);
	}



}
