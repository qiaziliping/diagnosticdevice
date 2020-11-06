package com.launch.diagdevice.blockchain.mq.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.blockchain.client.service.SmartContractClientService;
import com.launch.diagdevice.blockchain.client.vo.Result;
import com.launch.diagdevice.blockchain.client.vo.UpdateAllocationRequest;
import com.launch.diagdevice.blockchain.dao.ZnhyAllocationDao;
import com.launch.diagdevice.blockchain.model.ZnhyAllocation;

@Component
public class ZnhyAllocationUpdateReceiver {
	private static Logger log=LoggerFactory.getLogger(ZnhyAllocationUpdateReceiver.class);
	@Autowired
	private SmartContractClientService smartContractClientService;
	@Autowired
	private ZnhyAllocationDao znhyAllocationDao;

	@RabbitListener(queues = "znhyAllocationUpdate")
	@RabbitHandler
	public void process(ZnhyAllocation znhyAllocation) {
		UpdateAllocationRequest query4UpdateZnhyAllocation = znhyAllocationDao
				.query4UpdateZnhyAllocation(znhyAllocation);
		log.info("AllocationUpdate request:--------------" + query4UpdateZnhyAllocation.toString());
		Result result = smartContractClientService.updateAllocation(query4UpdateZnhyAllocation);
		log.info("AllocationUpdateReceiver result:--------------" + result.toString());
	}

}
