package com.launch.diagdevice.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.launch.diagdevice.dao.SysRoleAuthorityDao;
import com.launch.diagdevice.dao.mapper.SysRoleAuthorityMapper;
import com.launch.diagdevice.entity.SysRoleAuthority;

@Repository
public class SysRoleAuthorityDaoImpl implements SysRoleAuthorityDao{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysRoleAuthorityMapper sysRoleAuthorityMapper;

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

	@Override
	public void batchSave(Collection<SysRoleAuthority> entities) {
		sysRoleAuthorityMapper.batchSave(entities);
	}

	@Override
	public int deleteByRoleId(Long roleId) {
		return sysRoleAuthorityMapper.deleteByRoleId(roleId);
	}

	@Override
	public List<SysRoleAuthority> selectByRoleId(Long roleId) {
		List<SysRoleAuthority> list = new ArrayList<SysRoleAuthority>();
		if (null == roleId) {
			return list;
		}
		List<SysRoleAuthority> temp = sysRoleAuthorityMapper.selectByRoleId(roleId);
		return (null == temp) ? list : temp;
	}

	@Override
	public int deleteByAuthId(Long authId) {
		if (authId == null) {
			logger.warn("--------dao deleteByAuthId ,but authId is null");
			return 0;
		}
		return sysRoleAuthorityMapper.deleteByAuthId(authId);
	}

}
