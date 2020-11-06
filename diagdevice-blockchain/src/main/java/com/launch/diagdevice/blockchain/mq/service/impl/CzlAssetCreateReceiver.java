package com.launch.diagdevice.blockchain.mq.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.blockchain.model.CzlAsset;
import com.launch.diagdevice.blockchain.service.CzService;

@Component
public class CzlAssetCreateReceiver
{
	private static Logger log=LoggerFactory.getLogger(CzlAssetCreateReceiver.class);
    @Autowired
    private CzService czlCzService;
    
    @RabbitListener(queues = "czlAssetCreate")
    @RabbitHandler
    public void process(CzlAsset czlAsset)
    {
        czlCzService.createAsset(czlAsset);
        log.info("result:--------------" + czlAsset.toString());
    }

}
