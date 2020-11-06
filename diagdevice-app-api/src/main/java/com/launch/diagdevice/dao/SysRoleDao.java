package com.launch.diagdevice.dao;

import java.util.List;

import com.launch.diagdevice.common.dao.BaseDAO;
import com.launch.diagdevice.entity.SysRole;

/**
 * 系统角色DAO
 * @author LIPING
 */
public interface SysRoleDao extends BaseDAO<SysRole> {

	List<SysRole> selectByUserId(Long userId);


}
