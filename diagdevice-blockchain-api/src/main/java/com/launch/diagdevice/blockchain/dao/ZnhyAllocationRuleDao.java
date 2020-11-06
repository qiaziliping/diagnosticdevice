package com.launch.diagdevice.blockchain.dao;

import java.util.List;

import com.launch.diagdevice.blockchain.model.ZnhyAllocationRule;

public interface ZnhyAllocationRuleDao {
	/**
	 * 生成角色分配规则
	 * 
	 * @param znhyAllocationRule
	 */
	public void saveZnhyAllocationRule(List<ZnhyAllocationRule> znhyAllocationRuleList);
	
	/**
	 * 更新角色分配规则
	 * @param znhyAllocationRule
	 */
	void updateAllocationRule(List<ZnhyAllocationRule> znhyAllocationRuleList);

	/**
	 * 根据条件查询分配组组内所有记录
	 * 
	 * @param jobGroupId
	 * @return
	 */
	List<ZnhyAllocationRule> queryAllocationRuleByGroupId(int jobGroupId);

	public int updateAllocationRule(ZnhyAllocationRule rule);
}
