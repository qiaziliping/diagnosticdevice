package com.launch.diagdevice.dao;

import java.util.List;

import com.launch.diagdevice.common.dao.BaseDAO;
import com.launch.diagdevice.entity.User;
import com.launch.diagdevice.entity.vo.UserAndExtVo;


public interface UserDao extends BaseDAO<User>{

	
	User selectByUniqueFlag(String unique);

	
	List<UserAndExtVo> selectUserAndExtVoByIndex(User user);
	
	
}
