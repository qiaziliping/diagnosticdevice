package com.launch.diagdevice.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.launch.diagdevice.dao.DeviceGroupDao;
import com.launch.diagdevice.entity.DeviceGroup;
import com.launch.diagdevice.service.DeviceGroupService;

@Service(interfaceClass = DeviceGroupService.class)
@Transactional(readOnly = true)
public class DeviceGroupServiceImpl implements DeviceGroupService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DeviceGroupDao deviceGroupDao;
	
	@Override
	public DeviceGroup selectById(Serializable id) {
		// TODO Auto-generated method stub
		return deviceGroupDao.selectById(id);
	}

	@Override
	public DeviceGroup selectOne(DeviceGroup model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer save(DeviceGroup e) {
		return deviceGroupDao.save(e);
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
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<DeviceGroup> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DeviceGroup> selectAll(DeviceGroup deviceGroup) {
		List<DeviceGroup> list = new ArrayList<DeviceGroup>();
		List<DeviceGroup> temp = deviceGroupDao.selectAll(deviceGroup);
		
		return (temp == null) ? list : temp;
	}
	
	


	
	/*@Override
	public PagingData<DeviceVo> selectPage(Device model) {
		PagingData<DeviceVo> pagingData = new PagingData<DeviceVo>();
		if (null == model) {
			logger.warn("---device selectPage, but model is null---");
			return pagingData;
		}


		int pageNum = model.getPageNum();
		int pageSize = model.getPageSize();
		Page<RechargeRecord> page = PageHelper.startPage(pageNum, pageSize);

		List<DeviceVo> users = selectVoByCondition(model);

		long total = page.getTotal(); // 总条数

		pagingData.setRows(users);
		pagingData.setTotal(total);
		return pagingData;
	}*/
	





}
