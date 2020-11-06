package com.launch.diagdevice.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.launch.diagdevice.common.dao.mapper.BaseMapper;
import com.launch.diagdevice.entity.SysUser;
import com.launch.diagdevice.entity.vo.SysUserVo;

/**
 * 系统用户Mapper
 * @author LIPING
 * @version 0.0.1
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

	List<SysUserVo> selectVoByIndex(SysUser model);

	

}
