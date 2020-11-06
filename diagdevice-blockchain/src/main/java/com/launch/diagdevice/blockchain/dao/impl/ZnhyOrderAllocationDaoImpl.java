package com.launch.diagdevice.blockchain.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.blockchain.dao.ZnhyOrderAllocationDao;
import com.launch.diagdevice.blockchain.dao.mapper.ZnhyOrderAllocationMapper;
import com.launch.diagdevice.blockchain.model.UserOrder;
@Component
public class ZnhyOrderAllocationDaoImpl implements ZnhyOrderAllocationDao {
	@Autowired
	private ZnhyOrderAllocationMapper mapper;
	@Override
	public UserOrder queryUserOrderByOrderId(Integer orderId) {
		return mapper.queryUserOrderById(orderId);
	}

}
