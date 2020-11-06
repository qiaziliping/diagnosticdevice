package com.launch.diagdevice.service;

import java.util.List;

import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.service.BaseService;
import com.launch.diagdevice.entity.SysRole;

/**
 * 系统角色Service
 * @author LIPING
 * @version 0.0.1
 * @since 2018年10月24日
 */
public interface SysRoleService extends BaseService<SysRole>{

	PagingData<SysRole> selectPage(SysRole sysRole);

	List<SysRole> selectByUserId(Long userId);

	int deleteRoleAndURAndRA(SysRole role);


	
}
