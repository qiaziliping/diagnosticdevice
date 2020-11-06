package com.launch.diagdevice.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.launch.diagdevice.common.constant.Constants;
import com.launch.diagdevice.dao.DiagSoftDao;
import com.launch.diagdevice.dao.DiagSoftPriceDao;
import com.launch.diagdevice.dao.UserOrderDao;
import com.launch.diagdevice.dao.UserOrderDetailDao;
import com.launch.diagdevice.entity.DiagSoft;
import com.launch.diagdevice.entity.DiagSoftPrice;
import com.launch.diagdevice.entity.UserOrder;
import com.launch.diagdevice.entity.UserOrderDetail;
import com.launch.diagdevice.service.UserOrderService;

@Service(interfaceClass = UserOrderService.class)
@Transactional(readOnly = true)
public class UserOrderServiceImpl implements UserOrderService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserOrderDao userOrderDao;
	
	@Autowired
	private UserOrderDetailDao userOrderDetailDao;
	@Autowired
	private DiagSoftPriceDao diagSoftPriceDao;
	@Autowired
	private DiagSoftDao diagSoftDao;
	
	@Autowired
	 private RabbitTemplate rabbitTemplate;
	
	@Override
	public UserOrder selectById(Serializable id) {
		
		return userOrderDao.selectById(id);
	}

	@Override
	public UserOrder selectOne(UserOrder model) {
		
		return userOrderDao.selectOne(model);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer save(UserOrder e) {
		if (e == null) {
			logger.warn("---UserOrderService save,bug model is null---");
			return 0;
		}
		
		int count = userOrderDao.save(e);
		if (count > 0) {
			// 订单ID主键
			String orderId = e.getId();
			logger.info("userOrderService.save.orderId>:"+orderId);
			//rabbitTemplate.convertAndSend("znhyOrderAllocate", orderId);
			this.rabbitTemplate.convertAndSend("znhyOrderAllocate"+Constants.getTestQueueFlag(), orderId);
			logger.info(Constants.getTestQueueFlag()+"userOrderService.sendMessage.success>:orderId={}",orderId);
		}
		return count;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public int saveOrderAndDetail(UserOrder uOrder, long diagSoftPriceId) {
		if (uOrder == null) {
			logger.warn("---UserOrderService saveOrderAndDetail,bug model is null---");
			return 0;
		}
		
		int count = userOrderDao.save(uOrder);
		if (count > 0) {
			// 订单ID主键
			String orderId = uOrder.getId();
			logger.info("userOrderService.saveOrderAndDetail.orderId>:"+orderId);
			
			UserOrderDetail uoDetail = new UserOrderDetail();
			uoDetail.setOrderId(Long.parseLong(orderId));
			uoDetail.setDiagSoftPriceId(diagSoftPriceId);
			// 根据诊断软件价格ID获诊断软件ID
			DiagSoftPrice dsPrice = diagSoftPriceDao.selectById(diagSoftPriceId);
			DiagSoft diagsoft = diagSoftDao.selectById(dsPrice.getSoftId());
			uoDetail.setSoftName(diagsoft.getSoftName());
			userOrderDetailDao.save(uoDetail);
			
			this.rabbitTemplate.convertAndSend("znhyOrderAllocate"+Constants.getTestQueueFlag(), orderId);
			logger.info(Constants.getTestQueueFlag()+"userOrderService.saveOrderAndDetail.sendMessage.success>:orderId={}",orderId);
		}
		return count;
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
