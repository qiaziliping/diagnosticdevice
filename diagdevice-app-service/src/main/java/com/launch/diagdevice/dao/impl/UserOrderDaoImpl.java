package com.launch.diagdevice.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.launch.diagdevice.dao.UserOrderDao;
import com.launch.diagdevice.dao.mapper.UserOrderMapper;
import com.launch.diagdevice.entity.UserOrder;

@Repository
public class UserOrderDaoImpl implements UserOrderDao{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserOrderMapper userOrderMapper;

	@Override
	public UserOrder selectById(Serializable id) {
		return userOrderMapper.selectById(id);
	}

	@Override
	public UserOrder selectOne(UserOrder model) {
		if (model == null) {
			logger.warn("---UserOrderDao selectOne,bug model is null---");
			return null;
		}
		return userOrderMapper.selectOne(model);
	}

	@Override
	public Integer save(UserOrder e) {
		if (e == null) {
			logger.warn("---UserOrderDao save,bug model is null---");
			return 0;
		}
		e.setCreateTime(new Date());
		
		return userOrderMapper.save(e);
	}

	@Override
	public Integer update(UserOrder e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(UserOrder e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserOrder> selectByIndex(UserOrder model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<UserOrder> entities) {
		// TODO Auto-generated method stub
		
	}
	
	

}
