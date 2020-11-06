package com.launch.diagdevice.blockchain.service;

import java.util.List;
import java.util.Map;

import com.launch.diagdevice.blockchain.model.ZnhyAccount;
import com.launch.diagdevice.blockchain.model.ZnhyAllocation;
import com.launch.diagdevice.blockchain.model.ZnhyAllocationGroup;
import com.launch.diagdevice.blockchain.model.ZnhyAllocationRule;
import com.launch.diagdevice.blockchain.vo.ZnhyAccountVo;
import com.launch.diagdevice.blockchain.vo.ZnhyAllocationVo;
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.model.PagingEntity;

/**
 * 智能合约相关服务
 * 
 * @author ouxiangrui
 * @version 1.0 2018年9月27日
 * @since DBS V100
 */
public interface ZnhyService {
	/**
	 * 创建账户
	 * 
	 * @param czlAsset
	 * @since DBS V100
	 */
	public void createAccount(ZnhyAccount za);
	
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public ZnhyAccount getZnhyAccount(int accountId);
	
	/**
	 * 条件查询智能合约账号列表
	 * 
	 * @param za
	 * @return
	 */
	List<ZnhyAccount> queryZnhyAccount(ZnhyAccount za);

	/**
	 * 创建设备资产
	 * 
	 * @since DBS V100
	 */
	public void updateAccount(ZnhyAccountVo za);
	
	/**
	 * 根据条件查询某一条分配组记录
	 * 
	 * @param allocationGroup
	 * @return
	 */
	ZnhyAllocationGroup getAllocationGroup(int jobGroupId);

	/**
	 * 根据条件查询分配组列表，供后台展示用
	 * 
	 * @param allocationGroup
	 * @return
	 */
	List<ZnhyAllocationGroup> queryAllocationGroup(ZnhyAllocationGroup allocationGroup);

	/**
	 * 创建用户资产
	 * 
	 * @since DBS V100
	 */
	public void createAllocationGroup(ZnhyAllocationGroup group);

	/**
	 * 创建软件价格资产
	 * 
	 * @since DBS V100
	 */
	public void updateAllocationGroup(ZnhyAllocationGroup group);

	/**
	 * @param assetRecord
	 * @since DBS V100
	 */
	public void createAllocationRule(List<ZnhyAllocationRule> ruleList);
	
	/**
	 * 根据条件查询分配组组内所有记录
	 * 
	 * @param jobGroupId
	 * @return
	 */
	List<ZnhyAllocationRule> queryAllocationRuleByGroupId(int jobGroupId);

	/**
	 * 创建用户资产记录，包含订单记录
	 * 
	 * @since DBS V100
	 */
	public void updateAllocationRule(List<ZnhyAllocationRule> ruleList);

	/**
	 * 创建用户消费记录
	 * 
	 * @since DBS V100
	 */
	public void createAllocation(ZnhyAllocation allocation);
	
	/**
	 * 查询智能合约分配设置记录，主要是查询出来供维护设置数据； 根据资产名查询分配关系相关数据来填充待分配订单
	 * 
	 * @param znhyAllocation
	 * @return
	 */
	ZnhyAllocation queryZnhyAllocation(ZnhyAllocation znhyAllocation);
	


	/**
	 * @param device
	 * @since DBS V100
	 */
	public void updateAllocation(ZnhyAllocation allocation);

	/***
	 * 对订单，调用智能合约进行分配
	 * 
	 * @param order
	 */
	public void allocateOrder(String orderId);

	/**
	 * 分页查询智能合约账户信息
	 * TODO
	 * LIPING
	 */
	public PagingData<ZnhyAccount> selectPage(PagingEntity page, ZnhyAccount account);

	/**
	 * 创建分组和规则
	 * TODO
	 * liping
	 */
	public void createGroupAndRule(ZnhyAllocationGroup group, List<ZnhyAllocationRule> ruleList);

	/**
	 * 分页查询智能合约分配规则组信息
	 * LIPING
	 */
	public PagingData<ZnhyAllocationGroup> selectAllocationGroupPage(PagingEntity page,
			ZnhyAllocationGroup group);

	/**
	 * 根据系统分配的钱包地址id查询账户信息
	 * LIPING
	 */
	public ZnhyAccount getZnhyAccountByAccountId(String accountId);

	/**
	 * 根据分组ID修改组名称和规则
	 * LIPING
	 */
	public void updateGroupAndRule(ZnhyAllocationGroup group, List<ZnhyAllocationRule> ruleList);

	/**
	 * 分页查询智能合约分配信息
	 * LIPING
	 */
	public PagingData<ZnhyAllocationVo> selectZnhyAllocationVoPage(PagingEntity page, ZnhyAllocation allocation);

	/**
	 * 根据分配组ID查询对应的分配账户信息
	 * @param groupId
	 * @return
	 */
	public List<Map<String, Object>> getZnhyAccountByGroupId(long groupId);

}
