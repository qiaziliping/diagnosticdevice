package com.launch.diagdevice.blockchain.mq.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.blockchain.client.service.SmartContractClientService;
import com.launch.diagdevice.blockchain.client.vo.Account;
import com.launch.diagdevice.blockchain.client.vo.CreateAccountRequest;
import com.launch.diagdevice.blockchain.client.vo.CreateAccountResult;
import com.launch.diagdevice.blockchain.client.vo.UpdateAccountRequest;
import com.launch.diagdevice.blockchain.client.vo.UpdateAccountResult;
import com.launch.diagdevice.blockchain.dao.ZnhyAccountDao;
import com.launch.diagdevice.blockchain.model.ZnhyAccount;
import com.launch.diagdevice.common.constant.Constants;

@Component
public class ZnhyAccountCreateReceiver
{
	private static Logger log=LoggerFactory.getLogger(ZnhyAccountCreateReceiver.class);
    @Autowired
    private SmartContractClientService smartContractClientService;
    @Autowired
    private ZnhyAccountDao znhyAccountDao;
    
    @RabbitListener(queues = "znhyAccountCreate")
    @RabbitHandler
    public void process(ZnhyAccount za)
    {
    	//先创建账号
        CreateAccountRequest request = new CreateAccountRequest();
        request.setTelephone(za.getTelephone());
        request.setEmail(za.getEmail());
        CreateAccountResult result = smartContractClientService.createAccount(request);
        log.info("CreateAccountResult:--------------" + result.toString());
        //更新账号
        UpdateAccountRequest updateAccountRequest=new UpdateAccountRequest();
        updateAccountRequest.setApp_id(Constants.getAppId());
        Account account=new Account();
        account.setAlipay(za.getAlipay());
        account.setName(za.getName());
        account.setWe_chat(za.getWeChat());
        account.setBank_card(za.getBankCard());
        account.setNo(result.getNo());
        updateAccountRequest.setAccount(account);
        UpdateAccountResult updateAccountResult=smartContractClientService.updateAccount(updateAccountRequest);
        log.info("updateAccountResult:--------------" + updateAccountResult.toString());
        //更新数据库
        za.setAccountId(result.getNo());
        znhyAccountDao.updateZnhyAccount(za);
        log.info("result:--------------" + za.toString());
    }

}
