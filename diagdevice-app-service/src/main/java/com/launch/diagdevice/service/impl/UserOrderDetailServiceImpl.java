package com.launch.diagdevice.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.launch.diagdevice.dao.UserOrderDetailDao;
import com.launch.diagdevice.entity.UserOrderDetail;
import com.launch.diagdevice.service.UserOrderDetailService;

@Service(interfaceClass = UserOrderDetailService.class)
@Transactional(readOnly = true)
public class UserOrderDetailServiceImpl implements UserOrderDetailService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserOrderDetailDao userOrderDetailDao;

	@Override
	public UserOrderDetail selectById(Serializable id) {
		if (null == id) {
			logger.warn("------UserOrderDetailServiceImpl selectById , but id is null");
			return null;
		}
		return userOrderDetailDao.selectById(id);
	}

	@Override
	public UserOrderDetail selectOne(UserOrderDetail model) {
		if (null == model) {
			logger.warn("------UserOrderDetailServiceImpl selectOne , but id is null");
			return null;
		}
		return userOrderDetailDao.selectOne(model);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer save(UserOrderDetail e) {
		return userOrderDetailDao.save(e);
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
