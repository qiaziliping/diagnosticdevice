package com.launch.diagdevice.blockchain.mq.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.blockchain.client.service.SmartContractClientService;
import com.launch.diagdevice.blockchain.client.vo.CreateAllocationRequest;
import com.launch.diagdevice.blockchain.client.vo.CreateAllocationResult;
import com.launch.diagdevice.blockchain.dao.ZnhyAllocationDao;
import com.launch.diagdevice.blockchain.model.ZnhyAllocation;

@Component
public class ZnhyAllocationCreateReceiver
{
	private static Logger log=LoggerFactory.getLogger(ZnhyAllocationCreateReceiver.class);
    @Autowired
    private SmartContractClientService smartContractClientService;
    @Autowired
    private ZnhyAllocationDao znhyAllocationDao;
    
    @RabbitListener(queues = "znhyAllocationCreate")
    @RabbitHandler
    public void process(ZnhyAllocation znhyAllocation)
    {
        CreateAllocationRequest query4CreateZnhyAllocation = znhyAllocationDao.query4CreateZnhyAllocation(znhyAllocation);
        CreateAllocationResult result = smartContractClientService.createAllocation(query4CreateZnhyAllocation);
        znhyAllocation.setAllocationId(result.getAllocation_id());
        znhyAllocationDao.updateZnhyAllocation(znhyAllocation);
        log.info("result:--------------" + znhyAllocation.toString());
    }

}
