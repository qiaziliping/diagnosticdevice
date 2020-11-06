package com.launch.diagdevice.pay.service.impl;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.launch.diagdevice.pay.service.TestPayService;


@Service(interfaceClass=TestPayService.class)
@Component
public class TestPayServiceImpl implements TestPayService {

	@Override
	public String getAmount(String name) {
		
		return "pay "+name;
	}

}
