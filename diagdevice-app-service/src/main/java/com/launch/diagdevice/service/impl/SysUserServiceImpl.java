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
import com.launch.diagdevice.common.dao.BaseDAO;
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.service.impl.BaseServiceImpl;
import com.launch.diagdevice.dao.SysUserDao;
import com.launch.diagdevice.entity.SysUser;
import com.launch.diagdevice.entity.SysUserRole;
import com.launch.diagdevice.entity.vo.SysUserVo;
import com.launch.diagdevice.service.SysUserRoleService;
import com.launch.diagdevice.service.SysUserService;

@Service(interfaceClass = SysUserService.class)
@Transactional(readOnly = true)
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysUserDao sysUserDao;
	
	@Override
	public BaseDAO<SysUser> getDao() {
		return sysUserDao;
	}
	
	@Autowired
	private SysUserRoleService sysUserRoleService;
	

	@Override
	public SysUser selectById(Serializable id) {
		return sysUserDao.selectById(id);
	}

	@Override
	public SysUser selectOne(SysUser model) {
		
		return sysUserDao.selectOne(model);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer save(SysUser e) {
		return sysUserDao.save(e);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer update(SysUser e) {
		return sysUserDao.update(e);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer delete(SysUser e) {
		return sysUserDao.delete(e);
	}

	@Override
	public List<SysUser> selectByIndex(SysUser model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<SysUser> entities) {
		// TODO Auto-generated method stub
		
	}

	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public int saveUserAndUR(SysUser user, List<String> listIds) {
		
		int i = save(user);
		if (i > 0) {
			String strId = user.getId();
			
			List<SysUserRole> urList = new ArrayList<SysUserRole>();
			for (String roleId : listIds) {
				SysUserRole userRole = new SysUserRole();
				
				userRole.setUserId(Long.parseLong(strId));
				userRole.setRoleId(Long.parseLong(roleId));
				urList.add(userRole);
			}
			sysUserRoleService.batchSave(urList);
		}
		
		return i;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public int updateUserAndUR(SysUser sysUser, List<String> listIds, Boolean isUpdateRole) {
		
		int result = update(sysUser);
		
		Long userId = Long.parseLong(sysUser.getId());
		
		if (isUpdateRole) {
			// 先删除用户角色
			sysUserRoleService.deleteByUserId(userId);
			
			List<SysUserRole> urList = new ArrayList<SysUserRole>();
			for (String roleId : listIds) {
				SysUserRole userRole = new SysUserRole();
				
				userRole.setUserId(userId);
				userRole.setRoleId(Long.parseLong(roleId));
				urList.add(userRole);
			}
			if (urList.size() > 0) {
				// 再新加
				sysUserRoleService.batchSave(urList);
			}
			
		}
		/*if (!CollectionTool.listIsEmpty(listIds)) {
			
		}*/
		return result;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public int deleteUserAndUR(Long userId) {
		SysUser user = new SysUser();
		user.setId(String.valueOf(userId));
		
		// 1，删除用户角色关联表的外键
		sysUserRoleService.deleteByUserId(userId);
		
		// 2、再删除用户表
		int result = delete(user);
		return result;
	}

	@Override
	public PagingData<SysUserVo> selectPage(SysUser sysUser) {
		PagingData<SysUserVo> pagingData = new PagingData<SysUserVo>();
		if (null == sysUser) {
			logger.warn("---SysUser selectPage, but model is null---");
			return pagingData;
		}


		int pageNum = sysUser.getPageNum();
		int pageSize = sysUser.getPageSize();
		Page<SysUserVo> page = PageHelper.startPage(pageNum, pageSize);

		List<SysUserVo> list = selectVoByIndex(sysUser);
		
		long total = page.getTotal(); // 总条数

		pagingData.setRows(list);
		pagingData.setTotal(total);
		return pagingData;
	}

	public List<SysUserVo> selectVoByIndex(SysUser model) {
		
		return sysUserDao.selectVoByIndex(model);
	}



}
