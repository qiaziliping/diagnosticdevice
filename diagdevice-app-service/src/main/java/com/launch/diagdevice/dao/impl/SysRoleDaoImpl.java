package com.launch.diagdevice.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.launch.diagdevice.dao.SysRoleDao;
import com.launch.diagdevice.dao.mapper.SysRoleMapper;
import com.launch.diagdevice.entity.SysRole;

@Repository
public class SysRoleDaoImpl implements SysRoleDao{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Override
	public SysRole selectById(Serializable id) {
		if (id == null) {
			logger.warn("---sysRoleDao selectById,but param is null,param={}",id);
			return null;
		}
		return sysRoleMapper.selectById(id);
	}

	@Override
	public SysRole selectOne(SysRole model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer save(SysRole e) {
		if (e == null) {
			logger.warn("---sysRoleDao save,but param is null,param={}",e);
			return 0;
		}
		e.setCreateTime(new Date());
		// 1表示有效
		e.setIsValid(1);
		return sysRoleMapper.save(e);
	}

	@Override
	public Integer update(SysRole e) {
		if (e == null || StringUtils.isEmpty(e.getId())) {
			logger.warn("---sysRoleDao update,but param is null,param={}",e);
			return 0;
		}
		e.setUpdateTime(new Date());
		return sysRoleMapper.update(e);
	}

	@Override
	public Integer delete(SysRole e) {
		if (e == null || StringUtils.isEmpty(e.getId())) {
			logger.warn("---sysRoleDao delete,but param is null,param={}",e);
			return 0;
		}
		return sysRoleMapper.delete(e);
	}

	@Override
	public List<SysRole> selectByIndex(SysRole model) {
		List<SysRole> list = new ArrayList<SysRole>();
		List<SysRole> temp = sysRoleMapper.selectByIndex(model);
		
		return null == temp ? list : temp;
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<SysRole> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SysRole> selectByUserId(Long userId) {
		if (null == userId) {
			return null;
		}
		
		return sysRoleMapper.selectByUserId(userId);
	}


	

}
