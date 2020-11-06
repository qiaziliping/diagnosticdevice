package com.launch.diagdevice.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.launch.diagdevice.dao.DiagSoftDao;
import com.launch.diagdevice.entity.DiagSoft;
import com.launch.diagdevice.service.DiagSoftService;

@Service(interfaceClass = DiagSoftService.class)
@Transactional(readOnly = true)
public class DiagSoftServiceImpl implements DiagSoftService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DiagSoftDao diagSoftDao;
	
	@Override
	public DiagSoft selectById(Serializable id) {
		// TODO Auto-generated method stub
		return diagSoftDao.selectById(id);
	}

	@Override
	public DiagSoft selectOne(DiagSoft model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer save(DiagSoft e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer update(DiagSoft e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(DiagSoft e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DiagSoft> selectByIndex(DiagSoft model) {
		List<DiagSoft> temp = new ArrayList<DiagSoft>();
		List<DiagSoft> list = diagSoftDao.selectByIndex(model);
		return (list == null) ? temp : list;
	}

	@Override
//	@Cacheable(cacheNames="diagdevice_app_service",keyGenerator="simpleKeyGenerator",unless="#result == null")
	@Cacheable(value="diagdevice_app_service",keyGenerator="simpleKeyGenerator",unless="#result == null")
	public List<DiagSoft> selectByCondition(Map<String,Object> condition) {
		if (logger.isDebugEnabled()) {
			logger.info("--------condition>:"+condition);
		}
		List<DiagSoft> temp = new ArrayList<DiagSoft>();

		List<DiagSoft> list = diagSoftDao.selectByCondition(condition);
		
		return (list == null) ? temp : list;
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void batchSave(Collection<DiagSoft> entities) {
		diagSoftDao.batchSave(entities);
	}

	@Override
	public List<Long> selectPdtTypeAll() {
		List<Long> temp = new ArrayList<Long>();

		List<Long> list = diagSoftDao.selectPdtTypeAll();
		return (list == null) ? temp : list;
	}

}
