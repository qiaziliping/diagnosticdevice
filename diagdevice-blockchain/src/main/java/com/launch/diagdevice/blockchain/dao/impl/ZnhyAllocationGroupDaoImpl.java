package com.launch.diagdevice.blockchain.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.blockchain.dao.ZnhyAllocationGroupDao;
import com.launch.diagdevice.blockchain.dao.mapper.ZnhyAllocationGroupMapper;
import com.launch.diagdevice.blockchain.model.ZnhyAllocationGroup;
@Component
public class ZnhyAllocationGroupDaoImpl implements ZnhyAllocationGroupDao {

	@Autowired
	private ZnhyAllocationGroupMapper znhyAllocationGroupMapper;

	@Override
	public void saveAllocationGroup(ZnhyAllocationGroup allocationGroup) {
		znhyAllocationGroupMapper.saveAllocationGroup(allocationGroup);
	}

	@Override
	public void updateAllocationGroup(ZnhyAllocationGroup allocationGroup) {
		znhyAllocationGroupMapper.updateAllocationGroup(allocationGroup);

	}

	@Override
	public ZnhyAllocationGroup getAllocationGroup(int jobGroupId) {
		return znhyAllocationGroupMapper.getAllocationGroup(jobGroupId);
	}

	@Override
	public List<ZnhyAllocationGroup> queryAllocationGroup(ZnhyAllocationGroup allocationGroup) {
		return znhyAllocationGroupMapper.queryAllocationGroup(allocationGroup);
	}

	@Override
	public List<Map<String, Object>> getZnhyAccountByGroupId(long groupId) {
		return znhyAllocationGroupMapper.getZnhyAccountByGroupId(groupId);
	}

}
