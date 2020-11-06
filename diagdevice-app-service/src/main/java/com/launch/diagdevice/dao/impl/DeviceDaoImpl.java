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

import com.launch.diagdevice.dao.DeviceDao;
import com.launch.diagdevice.dao.mapper.DeviceMapper;
import com.launch.diagdevice.entity.Device;
import com.launch.diagdevice.entity.vo.DeviceVo;

@Repository
public class DeviceDaoImpl implements DeviceDao{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DeviceMapper deviceMapper;

	@Override
	public Device selectById(Serializable id) {
		// TODO Auto-generated method stub
		return deviceMapper.selectById(id);
	}

	@Override
	public Device selectOne(Device model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer save(Device e) {
		if (e == null) {
			logger.warn("----deviceDao save,but param is null");
			return 0;
		}
		
		Date date = new Date();
		e.setCreateTime(date);
		e.setUpdateTime(date);
		return deviceMapper.save(e);
	}

	@Override
	public Integer update(Device e) {
		if (e == null || e.getId() == null) {
			logger.warn("----deviceDao update,but param is null");
			return 0;
		}
		e.setUpdateTime(new Date());
		
		return deviceMapper.update(e);
	}

	@Override
	public Integer delete(Device e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Device> selectByIndex(Device model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<Device> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DeviceVo> selectVoByCondition(Device model) {
		List<DeviceVo> list = new ArrayList<DeviceVo>();
		if (model == null) {
			logger.warn(" selectVoByCondition bug model is null ");
			return list;
		}
		List<DeviceVo> temp = deviceMapper.selectVoByCondition(model);
		return (temp == null) ? list : temp;
	}
	
	

}
