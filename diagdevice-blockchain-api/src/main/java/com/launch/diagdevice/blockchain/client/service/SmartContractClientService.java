package com.launch.diagdevice.blockchain.client.service;

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

/**
 * 封装深圳元征研究院接口
 * 
 * @author ouxiangrui
 *
 */
public interface SmartContractClientService {
	/**
	 * 用户注册，关联支付宝等信息分账用
	 * 
	 * @param request
	 * @return
	 */
	CreateAccountResult createAccount(CreateAccountRequest request);

	/**
	 * 更新账号信息，分账用
	 * 
	 * @param request
	 * @return
	 */
	UpdateAccountResult updateAccount(UpdateAccountRequest request);

	/**
	 * 注册DAO组织，协商表决分配比例用
	 * 
	 * @param request
	 * @return
	 */
	CreateDAOResult createDAO(CreateDAORequest request);

	/**
	 * 发布分配表，设置分配比例、角色、关联账号
	 * 
	 * @param request
	 * @return
	 */
	CreateAllocationResult createAllocation(CreateAllocationRequest request);

	/**
	 * 修改分配表，修改相关比例设置
	 * 
	 * @param request
	 * @return
	 */
	Result updateAllocation(UpdateAllocationRequest request);

	/**
	 * 分配订单
	 * 
	 * @param request
	 * @return
	 */
	AllocateOrderResult allocateOrder(AllocateOrderRequest request);
}
