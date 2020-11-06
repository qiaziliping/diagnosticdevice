package com.launch.diagdevice.blockchain.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.blockchain.client.vo.AllocationDetail;
import com.launch.diagdevice.blockchain.client.vo.CreateAllocationRequest;
import com.launch.diagdevice.blockchain.client.vo.UpdateAllocationRequest;
import com.launch.diagdevice.blockchain.dao.ZnhyAllocationDao;
import com.launch.diagdevice.blockchain.dao.mapper.ZnhyAccountMapper;
import com.launch.diagdevice.blockchain.dao.mapper.ZnhyAllocationMapper;
import com.launch.diagdevice.blockchain.dao.mapper.ZnhyAllocationRuleMapper;
import com.launch.diagdevice.blockchain.model.ZnhyAccount;
import com.launch.diagdevice.blockchain.model.ZnhyAllocation;
import com.launch.diagdevice.blockchain.model.ZnhyAllocationRule;
import com.launch.diagdevice.blockchain.vo.ZnhyAllocationVo;
import com.launch.diagdevice.common.constant.Constants;
@Component
public class ZnhyAllocationDaoImpl implements ZnhyAllocationDao {

	@Autowired
	private ZnhyAllocationMapper znhyAllocationMapper;

	@Autowired
	private ZnhyAllocationRuleMapper znhyAllocationRuleMapper;

	@Autowired
	private ZnhyAccountMapper znhyAccountMapper;

	@Override
	public void saveZnhyAllocation(ZnhyAllocation znhyAllocation) {
		znhyAllocationMapper.saveZnhyAllocation(znhyAllocation);

	}
	

	@Override
	public void updateZnhyAllocation(ZnhyAllocation znhyAllocation) {
		znhyAllocationMapper.updateZnhyAllocation(znhyAllocation);
	}


	@Override
	public ZnhyAllocation queryZnhyAllocation(ZnhyAllocation znhyAllocation) {
		ZnhyAllocation result = znhyAllocationMapper.queryZnhyAllocation(znhyAllocation);
		return result;
	}

	@Override
	public CreateAllocationRequest query4CreateZnhyAllocation(ZnhyAllocation znhyAllocation) {
		CreateAllocationRequest request = new CreateAllocationRequest();
		List<AllocationDetail> allocation_list = new ArrayList<AllocationDetail>();
		// 根据分配表id查询规则列表
		List<ZnhyAllocationRule> groupRules = znhyAllocationRuleMapper
				.queryAllocationRuleByGroupId(znhyAllocation.getJobGroupId());
		// 根据规则列表查询相关账号、比例，并进行设置
		for (ZnhyAllocationRule znhyAllocationRule : groupRules) {
			AllocationDetail allocationDetail = new AllocationDetail();
			// 查询取出对应的账号信息进行设置
			ZnhyAccount za = znhyAccountMapper.getZnhyAccountByaccountId(znhyAllocationRule.getAccountId());
			if (null != za) { // updateBy 20180105 liping
				AllocationDetail.Account account = allocationDetail.new Account();
				account.setAlipay(za.getAlipay());
				account.setTelephone(za.getTelephone());
				account.setName(za.getName());
				allocationDetail.setAccount(account);
			}
			
			// 设置相应角色和比例
			allocationDetail.setJob(znhyAllocationRule.getJob());
			allocationDetail.setRadios(znhyAllocationRule.getRadios());

			allocation_list.add(allocationDetail);
		}
		request.setAssigners(allocation_list);
		request.setName(znhyAllocation.getName());
		request.setApp_id(Constants.getAppId());
		request.setCreator(znhyAllocation.getCreatorId());

		return request;
	}

	@Override
	public UpdateAllocationRequest query4UpdateZnhyAllocation(ZnhyAllocation znhyAllocation) {
		UpdateAllocationRequest request = new UpdateAllocationRequest();
		List<AllocationDetail> allocation_list = new ArrayList<AllocationDetail>();

		// 根据分配表id查询规则列表
		List<ZnhyAllocationRule> groupRules = znhyAllocationRuleMapper
				.queryAllocationRuleByGroupId(znhyAllocation.getJobGroupId());
		// 根据规则列表查询相关账号、比例，并进行设置
		for (ZnhyAllocationRule znhyAllocationRule : groupRules) {
			AllocationDetail allocationDetail = new AllocationDetail();
			// 查询取出对应的账号信息进行设置
			ZnhyAccount za = znhyAccountMapper.getZnhyAccountByaccountId(znhyAllocationRule.getAccountId());
			AllocationDetail.Account account = allocationDetail.new Account();
			account.setAlipay(za.getAlipay());
			account.setTelephone(za.getTelephone());
			account.setName(za.getName());
			allocationDetail.setAccount(account);
			// 设置相应角色和比例
			allocationDetail.setJob(znhyAllocationRule.getJob());
			allocationDetail.setRadios(znhyAllocationRule.getRadios());

			allocation_list.add(allocationDetail);
		}
		request.setAssigners(allocation_list);
		request.setCreator(znhyAllocation.getCreatorId());
		request.setApp_id(Constants.getAppId());
		request.setAllocation_id(znhyAllocation.getAllocationId());
		request.setName(znhyAllocation.getName());

		return request;
	}


	@Override
	public List<ZnhyAllocationVo> queryZnhyAllocationVo(ZnhyAllocation allocation) {
		List<ZnhyAllocationVo> result = znhyAllocationMapper.queryZnhyAllocationVo(allocation);
		return result;
	}

}
