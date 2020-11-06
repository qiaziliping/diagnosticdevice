package com.launch.diagdevice.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.launch.diagdevice.dao.UserOrderDetailDao;
import com.launch.diagdevice.dao.mapper.UserOrderDetailMapper;
import com.launch.diagdevice.entity.UserOrderDetail;

@Repository
public class UserOrderDetailDaoImpl implements UserOrderDetailDao{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserOrderDetailMapper userOrderDetailMapper;

	@Override
	public UserOrderDetail selectById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserOrderDetail selectOne(UserOrderDetail model) {
		if (null == model) {
			logger.warn("----UserOrderDetailDaoImpl selectOne ,bug model is null");
			return null;
		}
		return userOrderDetailMapper.selectOne(model);
	}

	@Override
	public Integer save(UserOrderDetail e) {
		if (null == e) {
			logger.warn("----UserOrderDetailDaoImpl save ,bug model is null");
			return null;
		}
		return userOrderDetailMapper.save(e);
	}

	@Override
	public Integer update(UserOrderDetail e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(UserOrderDetail e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserOrderDetail> selectByIndex(UserOrderDetail model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<UserOrderDetail> entities) {
		// TODO Auto-generated method stub
		
	}

	
	

}
