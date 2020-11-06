package com.launch.diagdevice.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.dao.SysAuthorityDao;
import com.launch.diagdevice.entity.SysAuthority;
import com.launch.diagdevice.entity.vo.SysAuthorityVo;
import com.launch.diagdevice.service.SysAuthorityService;
import com.launch.diagdevice.service.SysRoleAuthorityService;

@Service(interfaceClass = SysAuthorityService.class)
@Transactional(readOnly = true)
public class SysAuthorityServiceImpl implements SysAuthorityService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysAuthorityDao sysAuthorityDao;
	
	@Autowired
	private SysRoleAuthorityService sysRoleAuthorityService;

	@Override
	public SysAuthority selectById(Serializable id) {
		return sysAuthorityDao.selectById(id);
	}

	@Override
	public SysAuthority selectOne(SysAuthority model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer save(SysAuthority e) {
		return sysAuthorityDao.save(e);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer update(SysAuthority e) {
		return sysAuthorityDao.update(e);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer delete(SysAuthority e) {
		return sysAuthorityDao.delete(e);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public int deleteAuthAndRA(SysAuthority authority) {
		Long authId = Long.parseLong(authority.getId());
		// 1、先删除角色权限关联表的外键数据
		sysRoleAuthorityService.deleteByAuthId(authId);
		// 2、再删除菜单权限
		int count = delete(authority);
		return count;
	}

	@Override
	public List<SysAuthority> selectByIndex(SysAuthority model) {
		List<SysAuthority> list = new ArrayList<SysAuthority>();

		List<SysAuthority> temp = sysAuthorityDao.selectByIndex(model);

		return (null == temp) ? list : temp;
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
	public PagingData<SysAuthorityVo> selectPage(SysAuthority model) {
		PagingData<SysAuthorityVo> pagingData = new PagingData<SysAuthorityVo>();
		if (null == model) {
			logger.warn("---device selectPage, but model is null---");
			return pagingData;
		}

		int pageNum = model.getPageNum();
		int pageSize = model.getPageSize();
		Page<SysAuthorityVo> page = PageHelper.startPage(pageNum, pageSize);

		// model.setParentId(0L);
		List<SysAuthority> list = selectByIndex(model);

		List<SysAuthorityVo> listvo = new ArrayList<SysAuthorityVo>();
		for (SysAuthority authority : list) {
			SysAuthorityVo vo = new SysAuthorityVo();
			BeanUtils.copyProperties(authority, vo);

			Long parentId = authority.getParentId();
			// parentId=0表示没有上级
			if (0L != parentId.longValue()) {
				SysAuthority parent = selectById(authority.getParentId());
				vo.setParentResourceCode(parent.getResourceCode());
			}
			listvo.add(vo);
		}

		long total = page.getTotal(); // 总条数

		pagingData.setRows(listvo);
		pagingData.setTotal(total);
		return pagingData;
	}

	@Override
	public List<SysAuthority> selectByCondition(Map<String, Object> condition) {

		return sysAuthorityDao.selectByCondition(condition);
	}

	@Override
	public List<SysAuthorityVo> selectMenuList(SysAuthority authority, int isMenu) {

		// 1、 查询所有
		List<SysAuthority> rootMenu = this.selectByCondition(null);

		// 最后的结果
		List<SysAuthorityVo> menuList = new ArrayList<SysAuthorityVo>();

		// 2、 先找到所有的一级菜单
		for (SysAuthority menu : rootMenu) {
			// 一级菜单没有parentId
			if (0L == menu.getParentId().longValue()) {
				SysAuthorityVo vo = new SysAuthorityVo();
				BeanUtils.copyProperties(menu, vo);
				menuList.add(vo);
			}
		}

		// 3、 为一级菜单设置子菜单，getChild是递归调用的
		for (SysAuthorityVo menu : menuList) {
			menu.setChildList(getChild(menu.getId(), rootMenu, isMenu));
		}
		return menuList;
	}

	private List<SysAuthorityVo> getChild(Long id, List<SysAuthority> rootMenu, int isMenu) {
		// 子菜单
		List<SysAuthorityVo> childList = new ArrayList<SysAuthorityVo>();
		for (SysAuthority menu : rootMenu) {
			// 遍历所有节点，将父菜单id与传过来的id比较
			Long parentId = menu.getParentId();
			long pid = parentId.longValue();
			long lid = id.longValue();
			// 1 只查询菜单，
			if (isMenu == 1) {
				if (0L != pid && pid == lid && menu.getResourceType() != 5) {
					SysAuthorityVo vo = new SysAuthorityVo();
					BeanUtils.copyProperties(menu, vo);
					childList.add(vo);
				}
			} else {
				// 查询菜单跟权限
				if (0L != pid && pid == lid) {

					SysAuthorityVo vo = new SysAuthorityVo();
					BeanUtils.copyProperties(menu, vo);
					childList.add(vo);
				}
			}
		}

		// 把子菜单的子菜单再循环一遍
		for (SysAuthorityVo menu : childList) {
			// 处理5权限之外的都是菜单
			if (menu.getResourceType() != 5) {
				// 递归
				menu.setChildList(getChild(menu.getId(), rootMenu, isMenu));
			}
		}

		// 递归退出条件
		if (childList.size() == 0) {
			return null;
		}

		return childList;

	}

	@Override
	public List<SysAuthority> selectListByRoleId(Long roleId) {
		List<SysAuthority> list = new ArrayList<SysAuthority>();
		List<SysAuthority> temp = sysAuthorityDao.selectListByRoleId(roleId);
		return (null == temp) ? list : temp;
	}

}
