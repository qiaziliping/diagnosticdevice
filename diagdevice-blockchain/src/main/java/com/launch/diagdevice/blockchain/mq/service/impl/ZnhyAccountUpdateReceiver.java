package com.launch.diagdevice.blockchain.mq.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.blockchain.client.service.SmartContractClientService;
import com.launch.diagdevice.blockchain.client.vo.Account;
import com.launch.diagdevice.blockchain.client.vo.UpdateAccountRequest;
import com.launch.diagdevice.blockchain.client.vo.UpdateAccountResult;
import com.launch.diagdevice.blockchain.model.ZnhyAccount;
import com.launch.diagdevice.common.constant.Constants;

@Component
public class ZnhyAccountUpdateReceiver
{
	private static Logger log=LoggerFactory.getLogger(ZnhyAccountUpdateReceiver.class);
    @Autowired
    private SmartContractClientService smartContractClientService;
    
    @RabbitListener(queues = "znhyAccountUpdate")
    @RabbitHandler
    public void process(ZnhyAccount za)
    {
        
        //更新账号
        UpdateAccountRequest updateAccountRequest=new UpdateAccountRequest();
        updateAccountRequest.setApp_id(Constants.getAppId());
        Account account=new Account();
        account.setAlipay(za.getAlipay());
        account.setName(za.getName());
        account.setWe_chat(za.getWeChat());
        account.setBank_card(za.getBankCard());
        account.setNo(za.getAccountId());
        updateAccountRequest.setAccount(account);
        UpdateAccountResult updateAccountResult=smartContractClientService.updateAccount(updateAccountRequest);
        log.info("updateAccountResult:--------------" + updateAccountResult.toString());
    }

}
