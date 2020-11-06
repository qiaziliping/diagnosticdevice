package com.launch.diagdevice.remoteuc.dao.impl;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.launch.diagdevice.common.constant.Constants;
import com.launch.diagdevice.common.util.Md5SumUtil;
import com.launch.diagdevice.common.util.ParameteToken;
import com.launch.diagdevice.common.util.TokenResult;

@Component
public class RemoteCallTool {
	private static Logger logger = LoggerFactory.getLogger(RemoteCallTool.class);
	@Autowired
	private RestTemplate restTemplate;

	public String getToken(String cc) throws Exception {
		ParameteToken user = new ParameteToken();
		user.setUser_id(Integer.valueOf(cc));
		user.setAction(Constants.getTokenMethod());
		user.setApp_id("mycar");

		user.setSign(Md5SumUtil.md5((user.toString() + Constants.getCommunicatekey())));
		String getParameter = user.toString() + "&sign=" + user.getSign();

		String path = Constants.getService() + getParameter;

		TokenResult result = restTemplate.getForObject(new URI(path), TokenResult.class);
		return result.getData();
	}
}
