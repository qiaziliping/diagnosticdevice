package com.launch.diagdevice.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.launch.diagdevice.common.dao.mapper.BaseMapper;
import com.launch.diagdevice.entity.SysRoleAuthority;

/**
 * 系统角色权限Mapper
 * @author LIPING
 * @version 0.0.1
 */
@Mapper
public interface SysRoleAuthorityMapper extends BaseMapper<SysRoleAuthority> {

	int deleteByRoleId(Long roleId);

	List<SysRoleAuthority> selectByRoleId(Long roleId);

	int deleteByAuthId(Long authId);

	

}
