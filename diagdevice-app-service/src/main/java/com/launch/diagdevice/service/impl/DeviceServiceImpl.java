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
import com.launch.diagdevice.dao.DeviceDao;
import com.launch.diagdevice.entity.Device;
import com.launch.diagdevice.entity.vo.DeviceVo;
import com.launch.diagdevice.service.DeviceService;

@Service(interfaceClass = DeviceService.class)
@Transactional(readOnly = true)
public class DeviceServiceImpl implements DeviceService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DeviceDao deviceDao;

	
	@Override
	public PagingData<DeviceVo> selectPage(Device model) {
		PagingData<DeviceVo> pagingData = new PagingData<DeviceVo>();
		if (null == model) {
			logger.warn("---device selectPage, but model is null---");
			return pagingData;
		}


		int pageNum = model.getPageNum();
		int pageSize = model.getPageSize();
		Page<DeviceVo> page = PageHelper.startPage(pageNum, pageSize);

		List<DeviceVo> users = selectVoByCondition(model);

		long total = page.getTotal(); // 总条数

		pagingData.setRows(users);
		pagingData.setTotal(total);
		return pagingData;
	}
	
	@Override
	public Device selectById(Serializable id) {
		return deviceDao.selectById(id);
	}

	@Override
	public Device selectOne(Device model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer save(Device e) {
		return deviceDao.save(e);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer update(Device e) {
		return deviceDao.update(e);
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
	
	public List<DeviceVo> selectVoByCondition(Device model) {
		return deviceDao.selectVoByCondition(model);
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



}
