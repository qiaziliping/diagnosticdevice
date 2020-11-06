package com.launch.diagdevice.service;


import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.service.BaseService;
import com.launch.diagdevice.entity.User;
import com.launch.diagdevice.entity.vo.UserAndExtVo;

public interface UserService extends BaseService<User>{

	/**
	 * 根据唯一标识查询数据
	 * unique 为用户名，邮箱，手机号
	 * LIPING
	 */
	User selectByUniqueFlag(String unique);

	
	/**
	 * 用户登录，
	 * 
	 * TODO
	 * liping
	 * @param dateTime 
	 */
	int login(User user, String dateTime);


	PagingData<UserAndExtVo> selectPage(User user);

	
}
