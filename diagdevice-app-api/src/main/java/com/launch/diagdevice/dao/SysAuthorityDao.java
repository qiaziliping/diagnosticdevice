package com.launch.diagdevice.dao;

import java.util.List;
import java.util.Map;

import com.launch.diagdevice.common.dao.BaseDAO;
import com.launch.diagdevice.entity.SysAuthority;

/**
 * 系统菜单权限DAO
 * @author LIPING
 */
public interface SysAuthorityDao extends BaseDAO<SysAuthority>{

	
	List<SysAuthority> selectByCondition(Map<String, Object> condition);

	List<SysAuthority> selectListByRoleId(Long roleId);


}
