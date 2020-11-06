package com.launch.diagdevice.blockchain.mq.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.blockchain.model.CzlAssetRecord;
import com.launch.diagdevice.blockchain.service.CzService;

@Component
public class CzlAssetRecordCreateReceiver
{
	private static Logger log=LoggerFactory.getLogger(CzlAssetRecordCreateReceiver.class);
    @Autowired
    private CzService czlCzService;
    @RabbitListener(queues = "czlAssetRecordCreate")
    @RabbitHandler
    public void process(CzlAssetRecord assetRecord)
    {
        czlCzService.appendAssetRecord(assetRecord);
        log.info("result:--------------" + assetRecord.toString());
    }

}
