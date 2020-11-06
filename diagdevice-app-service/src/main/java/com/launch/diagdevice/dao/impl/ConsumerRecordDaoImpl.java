package com.launch.diagdevice.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.launch.diagdevice.common.util.DateUtils;
import com.launch.diagdevice.dao.ConsumerRecordDao;
import com.launch.diagdevice.dao.mapper.ConsumerRecordMapper;
import com.launch.diagdevice.entity.ConsumerRecord;
import com.launch.diagdevice.entity.request.ConsumerRecordRequest;
import com.launch.diagdevice.entity.vo.ConsumerRecordVo;

@Repository
public class ConsumerRecordDaoImpl implements ConsumerRecordDao{

//	private Logger logger = LoggerFactory.getLogger(ConsumerRecordDaoImpl.class);
	
	
	@Autowired
	private ConsumerRecordMapper consumerRecordMapper;
	
	
	@Override
	public ConsumerRecord selectById(Serializable id) {
		// TODO Auto-generated method stub
		return consumerRecordMapper.selectById(id);
	}

	@Override
	public ConsumerRecord selectOne(ConsumerRecord model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer save(ConsumerRecord e) {
		// TODO Auto-generated method stub
		e.setCreateTime(DateUtils.getCurrentDate());
		e.setUpdateTime(DateUtils.getCurrentDate());
		e.setStatus(0);
		return consumerRecordMapper.save(e);
	}

	@Override
	public Integer update(ConsumerRecord e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(ConsumerRecord e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsumerRecord> selectByIndex(ConsumerRecord model) {
		return consumerRecordMapper.selectByIndex(model);
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<ConsumerRecord> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer batchUpdateStatus(Long userId, List<String> listIds) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("userId", userId);
		condition.put("listIds", listIds);
		return consumerRecordMapper.batchUpdateStatus(condition);
	}

	@Override
	public List<ConsumerRecordVo> selectVoByRequest(ConsumerRecordRequest crRequest) {
		return consumerRecordMapper.selectVoByRequest(crRequest);
	}

}
