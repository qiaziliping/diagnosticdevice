package com.launch.diagdevice.blockchain.dao;

import java.util.List;
import java.util.Map;

import com.launch.diagdevice.blockchain.model.ZnhyAllocationGroup;

public interface ZnhyAllocationGroupDao {

	/**
	 * 数据库保存，智能合约组操作新增操作
	 * 
	 * @param znhyAllocation
	 */
	void saveAllocationGroup(ZnhyAllocationGroup allocationGroup);

	/**
	 * 数据库保存，智能合约组操作更新操作
	 * 
	 * @param znhyAllocation
	 */
	void updateAllocationGroup(ZnhyAllocationGroup allocationGroup);

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

	List<Map<String, Object>> getZnhyAccountByGroupId(long groupId);
}
