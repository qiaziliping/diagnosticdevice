package com.launch.diagdevice.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.launch.diagdevice.dao.DeviceOptRecordDao;
import com.launch.diagdevice.dao.mapper.DeviceOptRecordMapper;
import com.launch.diagdevice.entity.DeviceOptRecord;

@Repository
public class DeviceOptRecordDaoImpl implements DeviceOptRecordDao{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private DeviceOptRecordMapper deviceOptRecordMapper;

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

	@Override
	public Integer save(DeviceOptRecord e) {
		if (e == null) {
			logger.warn("----deviceOptRecordDao save,but param is null");
			return 0;
		}
		
		Date date = new Date();
		e.setCreateTime(date);
		e.setUpdateTime(date);
		e.setRecordDate(date);
		return deviceOptRecordMapper.save(e);
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
		List<DeviceOptRecord> list = new ArrayList<DeviceOptRecord>();
		List<DeviceOptRecord> temp = deviceOptRecordMapper.selectByIndex(model);
		return (temp == null) ? list : temp;
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
	


	
	

}
