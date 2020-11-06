package com.launch.diagdevice.blockchain.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.blockchain.dao.ZnhyOrderAllocateFailDao;
import com.launch.diagdevice.blockchain.dao.mapper.ZnhyOrderAllocateFailMapper;
import com.launch.diagdevice.blockchain.model.ZnhyOrderAllocateFail;

@Component
public class ZnhyOrderAllocateFailDaoImpl implements ZnhyOrderAllocateFailDao {

	@Autowired
	private ZnhyOrderAllocateFailMapper znhyOrderAllocateFailMapper;

	@Override
	public void saveZnhyOrderAllocateFail(ZnhyOrderAllocateFail zaf) {
		znhyOrderAllocateFailMapper.saveZnhyOrderAllocateFail(zaf);
	}

	@Override
	public void updateZnhyOrderAllocateFail(ZnhyOrderAllocateFail zaf) {
		// TODO 自动生成的方法存根
		znhyOrderAllocateFailMapper.updateZnhyOrderAllocateFail(zaf);
	}

	@Override
	public ZnhyOrderAllocateFail getZnhyOrderAllocateFail(int orderId) {
		// TODO 自动生成的方法存根
		return znhyOrderAllocateFailMapper.getZnhyOrderAllocateFail(orderId);
	}

	@Override
	public List<ZnhyOrderAllocateFail> queryZnhyOrderAllocateFail(ZnhyOrderAllocateFail zaf) {
		// TODO 自动生成的方法存根
		return null;
	}

}
