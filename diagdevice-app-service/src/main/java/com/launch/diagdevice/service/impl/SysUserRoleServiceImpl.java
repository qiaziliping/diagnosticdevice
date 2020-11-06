package com.launch.diagdevice.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.launch.diagdevice.dao.SysUserRoleDao;
import com.launch.diagdevice.entity.SysUserRole;
import com.launch.diagdevice.entity.vo.SysUserRoleVo;
import com.launch.diagdevice.service.SysUserRoleService;

@Service(interfaceClass = SysUserRoleService.class)
@Transactional(readOnly = true)
public class SysUserRoleServiceImpl implements SysUserRoleService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysUserRoleDao sysUserRoleDao;

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

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void batchSave(Collection<SysUserRole> entities) {
		// TODO Auto-generated method stub
		sysUserRoleDao.batchSave(entities);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public int deleteByUserId(Long userId) {
		
		return sysUserRoleDao.deleteByUserId(userId);
	}

	@Override
	public List<SysUserRoleVo> selectVoByCondition(Map<String, Object> condition) {
		List<SysUserRoleVo> list = new ArrayList<SysUserRoleVo>();
		List<SysUserRoleVo> temp = sysUserRoleDao.selectVoByCondition(condition);
		return (null == temp) ? list : temp;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public int deleteByRoleId(Long roleId) {
		return sysUserRoleDao.deleteByRoleId(roleId);
	}
	


	



}
