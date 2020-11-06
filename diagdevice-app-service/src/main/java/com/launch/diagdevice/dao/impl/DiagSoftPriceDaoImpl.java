package com.launch.diagdevice.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.launch.diagdevice.dao.DiagSoftPriceDao;
import com.launch.diagdevice.dao.mapper.DiagSoftPriceMapper;
import com.launch.diagdevice.entity.DiagSoftPrice;
import com.launch.diagdevice.entity.request.DiagSoftPriceRequest;
import com.launch.diagdevice.entity.vo.DiagSoftPriceVo;

@Repository
public class DiagSoftPriceDaoImpl implements DiagSoftPriceDao {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Autowired
	private DiagSoftPriceMapper diagSoftPriceMapper; 
	
	@Override
	public DiagSoftPrice selectById(Serializable id) {
		if (id == null) {
			logger.warn("-----diag soft dao selectById,but id is null");
			return null;
		}
		return diagSoftPriceMapper.selectById(id);
	}

	@Override
	public DiagSoftPrice selectOne(DiagSoftPrice model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer save(DiagSoftPrice e) {
		if (null == e) {
			logger.warn("-----diag soft dao save,but model is null" + e);
			return 0;
		}
		e.setCreateTime(new Date());
		return diagSoftPriceMapper.save(e);
	}

	@Override
	public Integer update(DiagSoftPrice model) {
		if (null == model || StringUtils.isEmpty(model.getId())) {
			logger.warn("-----diag softprice dao update,but model is null" + model);
			return 0;
		}
		return diagSoftPriceMapper.update(model);
	}

	@Override
	public Integer delete(DiagSoftPrice e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DiagSoftPrice> selectByIndex(DiagSoftPrice model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<DiagSoftPrice> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DiagSoftPriceVo> selectVoByRequest(DiagSoftPriceRequest dsRequest) {
		
		return diagSoftPriceMapper.selectVoByRequest(dsRequest);
	}

	@Override
	public List<DiagSoftPriceVo> selectByCondition(Map<String, Object> condition) {
		List<DiagSoftPriceVo> list = new ArrayList<DiagSoftPriceVo>();
		
		if (null == condition || condition.size() == 0) {
			logger.warn("-----diag softprice dao selectByCondition,but condition is null>:" + condition);
			return list;
		}
		List<DiagSoftPriceVo> temp = diagSoftPriceMapper.selectByCondition(condition);
		return (null == temp) ? list : temp;
	}
	
	
}
