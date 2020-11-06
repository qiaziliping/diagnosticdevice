package com.launch.diagdevice.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.launch.diagdevice.dao.DiagSoftDao;
import com.launch.diagdevice.dao.mapper.DiagSoftMapper;
import com.launch.diagdevice.entity.DiagSoft;

@Repository
public class DiagSoftDaoImpl implements DiagSoftDao {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private DiagSoftMapper diagSoftMapper;
	
	
	@Override
	public DiagSoft selectById(Serializable id) {
		// TODO Auto-generated method stub
		return diagSoftMapper.selectById(id);
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
		if (null == e || e.getSoftId() == null) {
			logger.warn("-----diag soft dao update,but model is null" + e);
			return 0;
		}
		e.setUpdateTime(new Date());
		return diagSoftMapper.update(e);
	}

	@Override
	public Integer delete(DiagSoft e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DiagSoft> selectByIndex(DiagSoft model) {
		return diagSoftMapper.selectByIndex(model);
	}
	
	@Override
	public List<DiagSoft> selectByCondition(Map<String,Object> condition) {
		return diagSoftMapper.selectByCondition(condition);
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<DiagSoft> entities) {
		if (entities == null || entities.size() == 0) {
			logger.warn("-------diagSoftDao batchSave ,but list is null");
			return;
		}
		diagSoftMapper.batchSave(entities);
	}

	@Override
	public List<Long> selectPdtTypeAll() {
		return diagSoftMapper.selectPdtTypeAll();
	}


}
