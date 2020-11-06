package com.launch.diagdevice.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.launch.diagdevice.dao.SysAuthorityDao;
import com.launch.diagdevice.dao.mapper.SysAuthorityMapper;
import com.launch.diagdevice.entity.SysAuthority;

@Repository
public class SysAuthorityDaoImpl implements SysAuthorityDao{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysAuthorityMapper sysAuthorityMapper;

	@Override
	public SysAuthority selectById(Serializable id) {
		return sysAuthorityMapper.selectById(id);
	}

	@Override
	public SysAuthority selectOne(SysAuthority model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer save(SysAuthority e) {
		if (e == null) {
			logger.warn("-------------sysAuthorityDao save,bug param is null,{}",e);
			return 0;
		}
		e.setCreateTime(new Date());
		return sysAuthorityMapper.save(e);
	}

	@Override
	public Integer update(SysAuthority e) {
		if (e == null) {
			logger.warn("-------------sysAuthorityDao update,bug param is null,{}",e);
			return 0;
		}
		e.setUpdateTime(new Date());
		return sysAuthorityMapper.update(e);
	}

	@Override
	public Integer delete(SysAuthority e) {
		if (e == null || StringUtils.isEmpty(e.getId())) {
			logger.warn("-------------sysAuthorityDao delete,bug param is null,{}",e);
			return 0;
		}
		return sysAuthorityMapper.delete(e);
	}

	@Override
	public List<SysAuthority> selectByIndex(SysAuthority model) {
		List<SysAuthority> list =  new ArrayList<SysAuthority>();
		List<SysAuthority> temp = sysAuthorityMapper.selectByIndex(model);
		return (temp == null) ? list : temp;
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<SysAuthority> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SysAuthority> selectByCondition(Map<String, Object> condition) {
		List<SysAuthority> result = new ArrayList<SysAuthority>();
		
		List<SysAuthority> temp = sysAuthorityMapper.selectByCondition(condition);
		return (null == temp) ? result : temp;
	}

	@Override
	public List<SysAuthority> selectListByRoleId(Long roleId) {
		if (roleId == null) {
			logger.warn("-------------sysAuthorityDao selectListByRoleId,bug roleId is null");
			return null;
		}
		return sysAuthorityMapper.selectListByRoleId(roleId);
	}

	

}
