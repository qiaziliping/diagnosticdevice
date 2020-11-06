package com.launch.diagdevice.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.launch.diagdevice.common.dao.mapper.BaseMapper;
import com.launch.diagdevice.entity.SysUserRole;
import com.launch.diagdevice.entity.vo.SysUserRoleVo;

/**
 * 系统用户角色关联Mapper
 * @author LIPING
 * @version 0.0.1
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

	int deleteByUserId(Long userId);

	List<SysUserRoleVo> selectVoByCondition(Map<String, Object> condition);

	int deleteByRoleId(Long roleId);

	

}
