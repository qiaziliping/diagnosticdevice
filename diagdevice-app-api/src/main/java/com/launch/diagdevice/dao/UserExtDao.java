package com.launch.diagdevice.dao;

import com.launch.diagdevice.common.dao.BaseDAO;
import com.launch.diagdevice.entity.UserExt;


public interface UserExtDao extends BaseDAO<UserExt>{

	
	Integer updateByUserId(UserExt userExt);

	
	
}
