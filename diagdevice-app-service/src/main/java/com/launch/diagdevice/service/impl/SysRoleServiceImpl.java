package com.launch.diagdevice.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.dao.SysRoleDao;
import com.launch.diagdevice.entity.SysRole;
import com.launch.diagdevice.service.SysRoleAuthorityService;
import com.launch.diagdevice.service.SysRoleService;
import com.launch.diagdevice.service.SysUserRoleService;

@Service(interfaceClass = SysRoleService.class)
@Transactional(readOnly = true)
public class SysRoleServiceImpl implements SysRoleService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleAuthorityService SysRoleAuthorityService;

	@Override
	public SysRole selectById(Serializable id) {
		return sysRoleDao.selectById(id);
	}

	@Override
	public SysRole selectOne(SysRole model) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer save(SysRole e) {
		return sysRoleDao.save(e);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer update(SysRole e) {
		return sysRoleDao.update(e);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer delete(SysRole e) {
		
		return sysRoleDao.delete(e);		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public int deleteRoleAndURAndRA(SysRole role) {
		Long roleId = Long.parseLong(role.getId());
		// 1，先删除用户角色关联表中的所有该角色ID
		sysUserRoleService.deleteByRoleId(roleId);
		// 2，再删除角色权限关联表中的所有该角色ID的数据
		SysRoleAuthorityService.deleteByRoleId(roleId);
		// 3、最后删除该角色
		int count = delete(role);
		return count;
	}

	@Override
	public List<SysRole> selectByIndex(SysRole model) {
		
		return sysRoleDao.selectByIndex(model);
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
	public PagingData<SysRole> selectPage(SysRole sysRole) {
		PagingData<SysRole> pagingData = new PagingData<SysRole>();
		if (null == sysRole) {
			logger.warn("---SysRole selectPage, but model is null---");
			return pagingData;
		}


		int pageNum = sysRole.getPageNum();
		int pageSize = sysRole.getPageSize();
		Page<SysRole> page = PageHelper.startPage(pageNum, pageSize);

		List<SysRole> list = selectByIndex(sysRole);
		
		long total = page.getTotal(); // 总条数

		pagingData.setRows(list);
		pagingData.setTotal(total);
		return pagingData;
	}

	@Override
	public List<SysRole> selectByUserId(Long userId) {
		List<SysRole> list = new ArrayList<SysRole>();
		List<SysRole> temp = sysRoleDao.selectByUserId(userId);
		return (temp == null) ? list : temp;
	}


	



}
