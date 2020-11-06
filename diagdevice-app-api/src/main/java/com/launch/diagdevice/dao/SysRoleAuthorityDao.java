package com.launch.diagdevice.dao;

import java.util.List;

import com.launch.diagdevice.common.dao.BaseDAO;
import com.launch.diagdevice.entity.SysRoleAuthority;

/**
 * 系统角色权限DAO
 * @author LIPING
 */
public interface SysRoleAuthorityDao extends BaseDAO<SysRoleAuthority>{

	int deleteByRoleId(Long roleId);

	List<SysRoleAuthority> selectByRoleId(Long roleId);

	int deleteByAuthId(Long authId);


}
