package com.launch.diagdevice.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.dao.DeviceOptRecordDao;
import com.launch.diagdevice.entity.DeviceOptRecord;
import com.launch.diagdevice.service.DeviceOptRecordService;

@Service(interfaceClass = DeviceOptRecordService.class)
@Transactional(readOnly = true)
public class DeviceOptRecordServiceImpl implements DeviceOptRecordService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	
	@Autowired
	private DeviceOptRecordDao deviceOptRecordDao;
	
	
	@Override
	public DeviceOptRecord selectById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeviceOptRecord selectOne(DeviceOptRecord model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer save(DeviceOptRecord e) {
		return deviceOptRecordDao.save(e);
	}

	@Override
	public Integer update(DeviceOptRecord e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(DeviceOptRecord e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DeviceOptRecord> selectByIndex(DeviceOptRecord model) {
		return deviceOptRecordDao.selectByIndex(model);
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<DeviceOptRecord> entities) {
		// TODO Auto-generated method stub
		
	}


	
	@Override
	public PagingData<DeviceOptRecord> selectPage(DeviceOptRecord model) {
		PagingData<DeviceOptRecord> pagingData = new PagingData<DeviceOptRecord>();
		if (null == model) {
			logger.warn("---device selectPage, but model is null---");
			return pagingData;
		}


		int pageNum = model.getPageNum();
		int pageSize = model.getPageSize();
		Page<DeviceOptRecord> page = PageHelper.startPage(pageNum, pageSize);

		List<DeviceOptRecord> record = this.selectByIndex(model);

		long total = page.getTotal(); // 总条数

		pagingData.setRows(record);
		pagingData.setTotal(total);
		return pagingData;
	}
	

	


}
