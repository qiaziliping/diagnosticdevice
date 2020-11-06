package com.launch.diagdevice.blockchain.dao;

import java.util.List;

import com.launch.diagdevice.blockchain.client.vo.CreateAllocationRequest;
import com.launch.diagdevice.blockchain.client.vo.UpdateAllocationRequest;
import com.launch.diagdevice.blockchain.model.ZnhyAllocation;
import com.launch.diagdevice.blockchain.vo.ZnhyAllocationVo;

public interface ZnhyAllocationDao {
	/**
	 * 保存智能合约分配设置记录，分配设置主要保存某资产名称（序列号）的分配关系，调用创建分配表接口后，和返回智能合约系统分配的id一起保存，在订单接口传入智能合约系统分配的id
	 * 
	 * @param znhyAllocation
	 */
	void saveZnhyAllocation(ZnhyAllocation znhyAllocation);
	
	/**
	 * 更新智能合约分配设置记录，分配设置主要保存某资产名称（序列号）的分配关系，调用创建分配表接口后，和返回智能合约系统分配的id一起保存，在订单接口传入智能合约系统分配的id
	 * 
	 * @param znhyAllocation
	 */
	void updateZnhyAllocation(ZnhyAllocation znhyAllocation);

	/**
	 * 查询智能合约分配设置记录，主要是查询出来供维护设置数据； 根据资产名查询分配关系相关数据来填充待分配订单
	 * 
	 * @param znhyAllocation
	 * @return
	 */
	ZnhyAllocation queryZnhyAllocation(ZnhyAllocation znhyAllocation);

	/**
	 * 查询相关信息创建分配表
	 * 
	 * @param znhyAllocation
	 * @return
	 */
	CreateAllocationRequest query4CreateZnhyAllocation(ZnhyAllocation znhyAllocation);

	/**
	 * 查询已创建分配表进行更新
	 * 
	 * @param znhyAllocation
	 * @return
	 */
	UpdateAllocationRequest query4UpdateZnhyAllocation(ZnhyAllocation znhyAllocation);
	/**
	 * 查询已创建分配表进行更新
	 * 
	 * @param znhyAllocation
	 * @return
	 */
	List<ZnhyAllocationVo> queryZnhyAllocationVo(ZnhyAllocation allocation);

}
