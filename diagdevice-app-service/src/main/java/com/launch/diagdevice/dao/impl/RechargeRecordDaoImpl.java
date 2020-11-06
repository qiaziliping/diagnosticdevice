package com.launch.diagdevice.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.launch.diagdevice.dao.RechargeRecordDao;
import com.launch.diagdevice.dao.mapper.RechargeRecordMapper;
import com.launch.diagdevice.entity.RechargeRecord;
import com.launch.diagdevice.entity.request.RechargeRecordRequest;
import com.launch.diagdevice.entity.vo.RechargeRecordVo;

@Repository
public class RechargeRecordDaoImpl implements RechargeRecordDao {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RechargeRecordMapper rechargeRecordMapper;
	
	@Override
	public RechargeRecord selectById(Serializable id) {
		if (StringUtils.isEmpty(id)) {
			logger.warn("RechargeRecord selectById ,but id is null");
			return null;
		}
		return rechargeRecordMapper.selectById(id);
	}

	@Override
	public RechargeRecord selectOne(RechargeRecord model) {
		if (null == model) {
			logger.warn("RechargeRecord selectOne ,but param is null");
			return null;
		}
		return rechargeRecordMapper.selectOne(model);
	}

	@Override
	public Integer save(RechargeRecord model) {
		if (model == null || model.getUserId() == null) {
			logger.warn("RechargeRecord save ,but param is null");
			return 0;
		}
		Date date = new Date();
		model.setCreateTime(date);
		model.setUpdateTime(date);
		return rechargeRecordMapper.save(model);
	}

	@Override
	public Integer update(RechargeRecord e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(RechargeRecord e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RechargeRecord> selectByIndex(RechargeRecord model) {
		
		List<RechargeRecord> list = rechargeRecordMapper.selectByIndex(model);
		return list;
	}
	
	@Override
	public List<RechargeRecordVo> selectVoByRequest(RechargeRecordRequest rrRequest) {
		List<RechargeRecordVo> list = new ArrayList<RechargeRecordVo>();
		List<RechargeRecordVo> temp = rechargeRecordMapper.selectVoByRequest(rrRequest);
		return (temp == null) ? list : temp;
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<RechargeRecord> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer batchUpdateStatus(Long userId, List<String> listIds) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("userId", userId);
		condition.put("listIds", listIds);
		return rechargeRecordMapper.batchUpdateStatus(condition);
	}

	@Override
	public Integer updateByOrderNo(RechargeRecord record) {
		if (record == null || !StringUtils.hasText(record.getOrderNo())) {
			logger.info("RechargeRecord updateByOrderNo,but param is null>:"+record);
			return 0;
		}
		record.setUpdateTime(new Date());
		return rechargeRecordMapper.updateByOrderNo(record);
	}



}
