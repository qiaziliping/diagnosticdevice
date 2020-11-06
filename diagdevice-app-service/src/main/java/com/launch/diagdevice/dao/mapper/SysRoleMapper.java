package com.launch.diagdevice.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.launch.diagdevice.common.dao.mapper.BaseMapper;
import com.launch.diagdevice.entity.SysRole;

/**
 * 系统角色Mapper
 * @author LIPING
 * @version 0.0.1
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

	List<SysRole> selectByUserId(Long userId);

	

}
