package com.launch.diagdevice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.launch.diagdevice.service.TestHelloService;


@Service(interfaceClass=TestHelloService.class)
public class TestHelloServiceImpl implements TestHelloService{

	
	
	@Override
	public String sayHello(String name) {
		
		
		
		return name+" hello";
	}


}
