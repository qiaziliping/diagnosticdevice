package com.launch.diagdevice.dao;

import java.util.List;
import java.util.Map;

import com.launch.diagdevice.common.dao.BaseDAO;
import com.launch.diagdevice.entity.SysUserRole;
import com.launch.diagdevice.entity.vo.SysUserRoleVo;

/**
 * 系统用户角色关联DAO
 * @author LIPING
 */
public interface SysUserRoleDao extends BaseDAO<SysUserRole>{

	int deleteByUserId(Long userId);

	List<SysUserRoleVo> selectVoByCondition(Map<String, Object> condition);

	int deleteByRoleId(Long roleId);


}
