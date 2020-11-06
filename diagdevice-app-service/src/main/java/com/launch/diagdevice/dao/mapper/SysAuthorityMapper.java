package com.launch.diagdevice.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.launch.diagdevice.common.dao.mapper.BaseMapper;
import com.launch.diagdevice.entity.SysAuthority;

/**
 * 系统菜单权限Mapper
 * @author LIPING
 * @version 0.0.1
 */
@Mapper
public interface SysAuthorityMapper extends BaseMapper<SysAuthority> {

	
	List<SysAuthority> selectByCondition(Map<String, Object> condition);

	List<SysAuthority> selectListByRoleId(Long roleId);

	

}
