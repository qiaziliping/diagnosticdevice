package com.launch.diagdevice.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.launch.diagdevice.dao.DeviceGroupDao;
import com.launch.diagdevice.dao.mapper.DeviceGroupMapper;
import com.launch.diagdevice.entity.DeviceGroup;

@Repository
public class DeviceGroupDaoImpl implements DeviceGroupDao{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DeviceGroupMapper deviceGroupMapper;

	@Override
	public DeviceGroup selectById(Serializable id) {
		// TODO Auto-generated method stub
		return deviceGroupMapper.selectById(id);
	}

	@Override
	public DeviceGroup selectOne(DeviceGroup model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer save(DeviceGroup e) {
		if (e == null) {
			logger.warn("----deviceDao save,but param is null");
			return 0;
		}
		
		Date date = new Date();
		e.setCreateTime(date);
		e.setUpdateTime(date);
		return deviceGroupMapper.save(e);
	}


	@Override
	public Integer update(DeviceGroup e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(DeviceGroup e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DeviceGroup> selectByIndex(DeviceGroup model) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<DeviceGroup> selectAll(DeviceGroup model) {
		
		return deviceGroupMapper.selectAll(model);
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<DeviceGroup> entities) {
		// TODO Auto-generated method stub
		
	}

	
	

}
