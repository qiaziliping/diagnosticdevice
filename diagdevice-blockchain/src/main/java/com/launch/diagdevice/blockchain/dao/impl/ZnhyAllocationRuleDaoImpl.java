package com.launch.diagdevice.blockchain.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.blockchain.dao.ZnhyAllocationRuleDao;
import com.launch.diagdevice.blockchain.dao.mapper.ZnhyAllocationRuleMapper;
import com.launch.diagdevice.blockchain.model.ZnhyAllocationRule;

@Component
public class ZnhyAllocationRuleDaoImpl implements ZnhyAllocationRuleDao {

	@Autowired
	private ZnhyAllocationRuleMapper znhyAllocationRuleMapper;

	@Override
	public void saveZnhyAllocationRule(List<ZnhyAllocationRule> znhyAllocationRuleList) {
		for (ZnhyAllocationRule znhyAllocationRule : znhyAllocationRuleList) {
			znhyAllocationRuleMapper.saveZnhyAllocationRule(znhyAllocationRule);
		}

	}

	@Override
	public void updateAllocationRule(List<ZnhyAllocationRule> znhyAllocationRuleList) {
		// 先根据组规则id删除相关记录
		znhyAllocationRuleMapper.deleteZnhyAllocationRule(znhyAllocationRuleList.get(0).getJobGroupId());
		// 重新插入相关数据
		for (ZnhyAllocationRule znhyAllocationRule : znhyAllocationRuleList) {
			znhyAllocationRuleMapper.saveZnhyAllocationRule(znhyAllocationRule);
		}
	}
	
	@Override
	public int updateAllocationRule(ZnhyAllocationRule rule) {
		return znhyAllocationRuleMapper.updateAllocationRule(rule);
	}

	@Override
	public List<ZnhyAllocationRule> queryAllocationRuleByGroupId(int jobGroupId) {
		List<ZnhyAllocationRule> list = znhyAllocationRuleMapper.queryAllocationRuleByGroupId(jobGroupId);
		return list;
	}


}
