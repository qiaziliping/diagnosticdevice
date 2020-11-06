package com.launch.diagdevice.dao;

import java.util.List;

import com.launch.diagdevice.common.dao.BaseDAO;
import com.launch.diagdevice.entity.SysUser;
import com.launch.diagdevice.entity.vo.SysUserVo;

/**
 * 系统用户DAO
 * @author LIPING
 */
public interface SysUserDao extends BaseDAO<SysUser>{

	List<SysUserVo> selectVoByIndex(SysUser model);


}
