package com.launch.diagdevice.service;

import java.util.List;
import java.util.Map;

import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.service.BaseService;
import com.launch.diagdevice.entity.SysAuthority;
import com.launch.diagdevice.entity.vo.SysAuthorityVo;

/**
 * 系统菜单权限Service
 * @author LIPING
 * @version 0.0.1
 * @since 2018年10月24日
 */
public interface SysAuthorityService extends BaseService<SysAuthority>{

	
	PagingData<SysAuthorityVo> selectPage(SysAuthority authority);
    
	/**
	 * 查询菜单权限树
	 *  isMenu 1菜单 0，菜单和权限
	 */
	List<SysAuthorityVo> selectMenuList(SysAuthority authority, int isMenu);
	/**
	 * 根据条件查询菜单树
	 */
	List<SysAuthority> selectByCondition(Map<String,Object> condition) ;
	/**
	 * 根据角色ID查询权限
	 */
	List<SysAuthority> selectListByRoleId(Long roleId);

	int deleteAuthAndRA(SysAuthority authority);


	
}
