package com.launch.diagdevice.blockchain.client.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.launch.diagdevice.blockchain.client.service.SmartContractClientService;
import com.launch.diagdevice.blockchain.client.vo.AllocateOrderRequest;
import com.launch.diagdevice.blockchain.client.vo.AllocateOrderResult;
import com.launch.diagdevice.blockchain.client.vo.CreateAccountRequest;
import com.launch.diagdevice.blockchain.client.vo.CreateAccountResult;
import com.launch.diagdevice.blockchain.client.vo.CreateAllocationRequest;
import com.launch.diagdevice.blockchain.client.vo.CreateAllocationResult;
import com.launch.diagdevice.blockchain.client.vo.CreateDAORequest;
import com.launch.diagdevice.blockchain.client.vo.CreateDAOResult;
import com.launch.diagdevice.blockchain.client.vo.Result;
import com.launch.diagdevice.blockchain.client.vo.UpdateAccountRequest;
import com.launch.diagdevice.blockchain.client.vo.UpdateAccountResult;
import com.launch.diagdevice.blockchain.client.vo.UpdateAllocationRequest;
import com.launch.diagdevice.blockchain.utils.JsonUtil;
import com.launch.diagdevice.common.constant.Constants;
import com.launch.diagdevice.common.util.DateUtils;
import com.launch.diagdevice.common.util.MD5Util;

@Service
public class SmartContractClientServiceImpl implements SmartContractClientService {
	private static Logger log=LoggerFactory.getLogger(SmartContractClientServiceImpl.class);
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public CreateAccountResult createAccount(CreateAccountRequest request) {
		// 根据配置拼接请求url
		String url = Constants.getSzznhypzservice() + Constants.getCreateAccount();
		// 请求json序列化和设置请求头
		HttpEntity<String> paramrequest = preparePost(request);
		// 调用远程业务方法
		CreateAccountResult result = restTemplate.postForObject(url, paramrequest, CreateAccountResult.class);
		processException(result,url);
		return result;

	}

	@Override
	public UpdateAccountResult updateAccount(UpdateAccountRequest request) {
		// 根据配置拼接请求url
		String url = Constants.getSzznhypzservice() + Constants.getUpdateAccount();
		// 请求json序列化和设置请求头
		HttpEntity<String> paramrequest = preparePost(request);
		// 调用远程业务方法
		UpdateAccountResult result = restTemplate.postForObject(url, paramrequest, UpdateAccountResult.class);
		processException(result,url);
		return result;
	}

	@Override
	public CreateDAOResult createDAO(CreateDAORequest request) {
		// 根据配置拼接请求url
		String url = Constants.getSzznhypzservice() + Constants.getCreateDAO();
		// 请求json序列化和设置请求头
		HttpEntity<String> paramrequest = preparePost(request);
		// 调用远程业务方法
		CreateDAOResult result = restTemplate.postForObject(url, paramrequest, CreateDAOResult.class);
		processException(result,url);
		return result;
	}

	@Override
	public CreateAllocationResult createAllocation(CreateAllocationRequest request) {
		// 根据配置拼接请求url
		String url = Constants.getSzznhypzservice() + Constants.getCreateAllocation();
		// 请求json序列化和设置请求头
		HttpEntity<String> paramrequest = preparePost(request);
		// 调用远程业务方法
		CreateAllocationResult result = restTemplate.postForObject(url, paramrequest, CreateAllocationResult.class);
		processException(result,url);
		return result;
	}

	@Override
	public Result updateAllocation(UpdateAllocationRequest request) {
		// 根据配置拼接请求url
		String url = Constants.getSzznhypzservice() + Constants.getUpdateAllocation();
		// 请求json序列化和设置请求头
		HttpEntity<String> paramrequest = preparePost(request);
		// 调用远程业务方法
		Result result = restTemplate.postForObject(url, paramrequest, Result.class);
		processException(result,url);
		return result;
	}

	@Override
	public AllocateOrderResult allocateOrder(AllocateOrderRequest request) {
		// 根据配置拼接请求url
		String url = Constants.getSzznhyfpservice() + Constants.getAllocateOrder();
		Long currentTime=DateUtils.getCurrentTime().getTime();
		url=url+"&Timestamp="+currentTime+"&Sign="+MD5Util.MD5(currentTime+Constants.getDevelopKey());
		// 请求json序列化和设置请求头
		HttpEntity<String> paramrequest = preparePost(request);
		// 调用远程业务方法
		String resultJson = restTemplate.postForObject(url, paramrequest, String.class);
		AllocateOrderResult result=(AllocateOrderResult)JsonUtil.fromJson(resultJson, AllocateOrderResult.class);
		processException4Order(result,url);
		return result;
	}
	
	/**
	 * 设置http请求头和请求对象序列化为json字符串
	 * 
	 * @param request
	 * @return
	 */
	private HttpEntity<String> preparePost(Object request) {
		String param = JsonUtil.toJsonNoEmptyField(request);
		log.debug("request:"+param);
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		HttpEntity<String> paramrequest = new HttpEntity<String>(param, headers);
		return paramrequest;
	}
	
	private void processException(Result result,String url){
		if(result.getCode()!=0) {
			log.info(url+" call fail reason:"+result.getMessage());
			throw new RuntimeException(url+" call fail reason:"+result.getMessage());
		}
	}
	
	private void processException4Order(AllocateOrderResult result,String url){
		if(result.getCode().compareTo(0)!=0) {
			log.error(url+" call fail reason:"+result.getMsg());
		}
	}

}
