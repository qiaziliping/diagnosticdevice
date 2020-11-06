package com.launch.diagdevice.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.launch.diagdevice.dao.UserExtDao;
import com.launch.diagdevice.dao.mapper.UserExtMapper;
import com.launch.diagdevice.entity.UserExt;

@Repository
public class UserExtDaoImpl implements UserExtDao{

	private Logger logger = LoggerFactory.getLogger(UserExtDaoImpl.class);

	
	
	@Autowired
	private UserExtMapper userExtMapper;
	
	
	@Override
	public UserExt selectById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserExt selectOne(UserExt model) {
		
		if (null == model) {
			logger.info("----selectOne UserExt ,bug param is null");
			return null;
		}
		UserExt result = userExtMapper.selectOne(model);
		return result;
	}

	@Override
	public Integer save(UserExt e) {
		if (e == null || e.getUserId() == null) {
			logger.info("----save UserExt ,bug userId is null");
			return 0;
		}
		Date date = new Date();
		e.setCreateTime(date);
		e.setUpdateTime(date);
		BigDecimal defaultMoney = new BigDecimal(0);
		if (e.getUserMoney() == null) e.setUserMoney(defaultMoney);
		if (e.getLockMoney() == null) e.setLockMoney(defaultMoney);
		if (e.getIsLock() == null) e.setIsLock(0);
		return userExtMapper.save(e);
	}

	@Override
	public Integer update(UserExt e) {
		if (e == null || e.getUserId() == null) {
			logger.info("----update UserExt ,bug param is null");
			return 0;
		}
		e.setUpdateTime(new Date());
		return userExtMapper.update(e);
	}

	@Override
	public Integer delete(UserExt e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserExt> selectByIndex(UserExt model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<UserExt> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer updateByUserId(UserExt userExt) {
		if (userExt == null || userExt.getUserId() == null) {
			logger.warn("----userExtdao updateByUserId,but param is null");
			return 0;
		}
		userExt.setUpdateTime(new Date());
		return userExtMapper.updateByUserId(userExt);
	}



}
