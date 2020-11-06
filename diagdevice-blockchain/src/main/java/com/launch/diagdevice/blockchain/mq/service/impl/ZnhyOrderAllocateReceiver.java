package com.launch.diagdevice.blockchain.mq.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.blockchain.client.service.SmartContractClientService;
import com.launch.diagdevice.blockchain.client.vo.AllocateOrderRequest;
import com.launch.diagdevice.blockchain.client.vo.AllocateOrderResult;
import com.launch.diagdevice.blockchain.dao.ZnhyAllocationDao;
import com.launch.diagdevice.blockchain.dao.ZnhyOrderAllocateFailDao;
import com.launch.diagdevice.blockchain.dao.ZnhyOrderAllocationDao;
import com.launch.diagdevice.blockchain.model.UserOrder;
import com.launch.diagdevice.blockchain.model.ZnhyAllocation;
import com.launch.diagdevice.blockchain.model.ZnhyOrderAllocateFail;
import com.launch.diagdevice.common.constant.Constants;
import com.launch.diagdevice.common.util.DateUtils;

@Component
public class ZnhyOrderAllocateReceiver {
	private static Logger log = LoggerFactory.getLogger(ZnhyOrderAllocateReceiver.class);
	@Autowired
	private SmartContractClientService smartContractClientService;
	@Autowired
	private ZnhyAllocationDao znhyAllocationDao;
	@Autowired
	private ZnhyOrderAllocationDao znhyOrderAllocationDao;
	@Autowired
	private ZnhyOrderAllocateFailDao znhyOrderAllocateFailDao;

	@RabbitListener(queues = "znhyOrderAllocate")
	@RabbitHandler
	public void process(String orderId) {
		UserOrder userOrder = znhyOrderAllocationDao.queryUserOrderByOrderId(Integer.valueOf(orderId));
		if (userOrder != null) {
			log.info("userOrder info:--------------" + userOrder.toString());
			ZnhyAllocation znhyAllocation = new ZnhyAllocation();
			znhyAllocation.setName(userOrder.getSerialNo());
			znhyAllocation = znhyAllocationDao.queryZnhyAllocation(znhyAllocation);

			AllocateOrderRequest request = new AllocateOrderRequest();
			request.setThirdTradeNo(userOrder.getThirdTradeNo());
			request.setOrderNo(userOrder.getOrderNo());
			request.setSubNumber(znhyAllocation.getAllocationId());
			request.setOrderTime(DateUtils.convertDateToString(userOrder.getPayTime()));
			request.setCompany(Constants.getCompany());
			request.setBranchShop(Constants.getBranchShop());
			if (userOrder.getPayFrom() == 1) {
				request.setPayWay("支付宝支付");
			} else if (userOrder.getPayFrom() == 2) {
				request.setPayWay("微信支付");
			}
			request.setSumPrice(userOrder.getPrice());
			request.setPrice(userOrder.getPrice());
			List orderDetails = new ArrayList<Object>();
			orderDetails.add(new Object());
			request.setOrderDetails(orderDetails);
			log.info("znhyOrderAllocate request:--------------" + request.toString());
			AllocateOrderResult result = smartContractClientService.allocateOrder(request);
			// code为0代表分配订单失败
			if (result.getCode().compareTo(0) != 0) {

				ZnhyOrderAllocateFail zaf = new ZnhyOrderAllocateFail();
				zaf.setOrderId(userOrder.getId());
				zaf.setSerialNo(userOrder.getSerialNo());
				zaf.setCode(result.getCode());
				zaf.setMessage(result.getMsg());
				zaf.setCreateTime(new Date());
				zaf.setSuccess(0);
				// 如果这个订单分配失败已经记录，则比较两次失败情况是否一样，如果不一样，则更新失败信息
				ZnhyOrderAllocateFail ozaf = znhyOrderAllocateFailDao
						.getZnhyOrderAllocateFail(Integer.valueOf(orderId));
				if (ozaf != null) {
					if (zaf.getCode() != ozaf.getCode()) {
						log.info("result code:" + zaf.getCode() + "" + zaf.getCode());
						znhyOrderAllocateFailDao.updateZnhyOrderAllocateFail(zaf);
						throw new RuntimeException(
								userOrder.getSerialNo() + " allocateOrder call fail reason:" + result.getMsg());
					}
				} else {
					znhyOrderAllocateFailDao.saveZnhyOrderAllocateFail(zaf);
					throw new RuntimeException(
							userOrder.getSerialNo() + " allocateOrder call fail reason:" + result.getMsg());
				}

			} else {
				ZnhyOrderAllocateFail ozaf = znhyOrderAllocateFailDao
						.getZnhyOrderAllocateFail(Integer.valueOf(orderId));
				if (ozaf != null) {
					ozaf.setSuccess(1);
					znhyOrderAllocateFailDao.updateZnhyOrderAllocateFail(ozaf);
				}
			}
			log.info("znhyOrderAllocate result:" + result.toString());
		} else {
			log.debug("orderId info:--------------" + orderId);
		}

	}

}
