package com.launch.diagdevice.blockchain.dao;

import java.util.List;

import com.launch.diagdevice.blockchain.model.ZnhyOrderAllocateFail;

public interface ZnhyOrderAllocateFailDao {
	/**
	 * 保存分账失败信息
	 * 
	 * @param za
	 */
	void saveZnhyOrderAllocateFail(ZnhyOrderAllocateFail zaf);

	/**
	 * 更新分账失败信息
	 * 
	 * @param za
	 */
	void updateZnhyOrderAllocateFail(ZnhyOrderAllocateFail zaf);

	/**
	 * 用订单号查询分账失败信息
	 * 
	 * @param za
	 * @return
	 */
	ZnhyOrderAllocateFail getZnhyOrderAllocateFail(int orderId);

	/**
	 * 条件查询分账列表
	 * 
	 * @param za
	 * @return
	 */
	List<ZnhyOrderAllocateFail> queryZnhyOrderAllocateFail(ZnhyOrderAllocateFail zaf);
	
}
