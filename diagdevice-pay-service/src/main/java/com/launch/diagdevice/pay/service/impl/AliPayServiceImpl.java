package com.launch.diagdevice.pay.service.impl;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.launch.diagdevice.pay.entity.AlipayModel;
import com.launch.diagdevice.pay.service.AlipayService;

/**
 * 支付宝支付服务接口
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月2日
 */
@Service(interfaceClass = AlipayService.class)
@Component
public class AliPayServiceImpl implements AlipayService {

	
	@Override
	public AlipayResponse preparePay(AlipayModel aliModel) throws AlipayApiException {

		AlipayClient alipayClient = new DefaultAlipayClient(aliModel.getOpenApiDomain(), aliModel.getAppId(),
				aliModel.getAppPrivateKey(), aliModel.getFormat(), aliModel.getCharset(), aliModel.getPublicKey(),
				aliModel.getSignType());
		
		
		
		AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
		model.setSubject(aliModel.getSubject());
        model.setTotalAmount(aliModel.getTotalAmount());
        model.setStoreId(aliModel.getStoreId());
        model.setTimeoutExpress(aliModel.getTimeoutExpress());
        // 订单号
        model.setOutTradeNo(aliModel.getOutTradeNo());
    
        // 调用支付宝接口
    	AlipayTradePrecreateRequest aliRequest = new AlipayTradePrecreateRequest();
    	aliRequest.setBizModel(model);
    	aliRequest.setNotifyUrl(aliModel.getNotifyUrl());
    	AlipayResponse aliResponse = alipayClient.execute(aliRequest);

		return aliResponse;
	}

}
