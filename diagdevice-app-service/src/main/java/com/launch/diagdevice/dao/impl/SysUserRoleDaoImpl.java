package com.launch.diagdevice.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.launch.diagdevice.dao.SysUserRoleDao;
import com.launch.diagdevice.dao.mapper.SysUserRoleMapper;
import com.launch.diagdevice.entity.SysUserRole;
import com.launch.diagdevice.entity.vo.SysUserRoleVo;

@Repository
public class SysUserRoleDaoImpl implements SysUserRoleDao{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Override
	public SysUserRole selectById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysUserRole selectOne(SysUserRole model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer save(SysUserRole e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer update(SysUserRole e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(SysUserRole e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysUserRole> selectByIndex(SysUserRole model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<SysUserRole> entities) {
		// TODO Auto-generated method stub
		if (null == entities) {
			logger.warn("sysUserRoleDao batchSave ,but entities is null,entities={}",entities);
			return;
		}
		sysUserRoleMapper.batchSave(entities);
	}

	@Override
	public int deleteByUserId(Long userId) {
		if (null == userId) {
			logger.warn("sysUserRoleDao deleteByUserId ,but userId is null,userId={}",userId);
			return 0;
		}
		return sysUserRoleMapper.deleteByUserId(userId);
	}
	
	@Override
	public int deleteByRoleId(Long roleId) {
		if (null == roleId) {
			logger.warn("sysUserRoleDao deleteByRoleId ,but roleId is null,roleId={}",roleId);
			return 0;
		}
		return sysUserRoleMapper.deleteByRoleId(roleId);
	}

	@Override
	public List<SysUserRoleVo> selectVoByCondition(Map<String, Object> condition) {
		return sysUserRoleMapper.selectVoByCondition(condition);
	}

	


	

}
