package com.launch.diagdevice.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.launch.diagdevice.dao.UserDao;
import com.launch.diagdevice.dao.mapper.UserMapper;
import com.launch.diagdevice.entity.User;
import com.launch.diagdevice.entity.vo.UserAndExtVo;

@Repository
public class UserDaoImpl implements UserDao{

	private Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User selectById(Serializable id) {
		return userMapper.selectById(id);
	}

	@Override
	public User selectOne(User user) {
		if (null == user) {
			logger.info("---user.selectOne but user is null --");
			return null;
		}
		return userMapper.selectOne(user);
	}

	@Override
	public Integer save(User e) {
		if (null == e) {
			logger.info("---user.save but user is null --");
			return 0;
		}
		return userMapper.save(e);
	}

	@Override
	public Integer update(User e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(User e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> selectByIndex(User model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<User> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User selectByUniqueFlag(String unique) {
		if (StringUtils.isEmpty(unique)) {
			logger.warn("------userdao selectByUniqueFlag,bug unique is null------");
		}
		return userMapper.selectByUniqueFlag(unique);
	}

	@Override
	public List<UserAndExtVo> selectUserAndExtVoByIndex(User user) {
		
		return userMapper.selectUserAndExtVoByIndex(user);
	}

	
	/*@Override
	public Integer insert(User user) {
		if (user == null) {
			logger.error("------insert user,bug user is null------");
			return 0;
		}
		return userDaoMapper.insert(user);
	}


	@Override
	public List<User> getList(User user) {
		return userDaoMapper.getList(user);
	}*/

}
