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
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.dao.ConsumerRecordDao;
import com.launch.diagdevice.entity.ConsumerRecord;
import com.launch.diagdevice.entity.RechargeRecord;
import com.launch.diagdevice.entity.request.ConsumerRecordRequest;
import com.launch.diagdevice.entity.vo.ConsumerRecordVo;
import com.launch.diagdevice.service.ConsumerRecordService;

@Service(interfaceClass = ConsumerRecordService.class)
@Transactional(readOnly = true)
public class ConsumerRecordServiceImpl implements ConsumerRecordService {

	private Logger logger = LoggerFactory.getLogger(ConsumerRecordServiceImpl.class);

	@Autowired
	private ConsumerRecordDao consumerRecordDao;
	

	@Override
	public ConsumerRecord selectById(Serializable id) {
		// TODO Auto-generated method stub
		return consumerRecordDao.selectById(id);
	}

	@Override
	public ConsumerRecord selectOne(ConsumerRecord model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer save(ConsumerRecord e) {
		if (e == null) {
			logger.warn("---ConsumerRecordService save,bug model is null---");
			return 0;
		}
		
		int count = consumerRecordDao.save(e);
		// 上传成功修改账户余额
		/*if (count > 0) {
			Long userId = e.getUserId();
			BigDecimal price = e.getPrice();
			
			UserExt ext = new UserExt();
			ext.setUserId(userId);
			// 获取扩展表对应的金额
			ext = userExtDao.selectOne(ext);
			BigDecimal total = ext.getUserMoney();
			ext.setUserMoney(total.subtract(price));
			// 再根据扩展表ID进行修改
			userExtDao.updateByUserId(ext);
		}*/
		return count;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer update(ConsumerRecord e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer delete(ConsumerRecord e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsumerRecord> selectByIndex(ConsumerRecord model) {
		List<ConsumerRecord> result = new ArrayList<ConsumerRecord>();
		if (model == null) {
			logger.warn("---ConsumerRecordService selectByIndex,bug model is null---");
			return result;
		}

		List<ConsumerRecord> temp = consumerRecordDao.selectByIndex(model);
		return (temp == null) ? result : temp;
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
	public PagingData<ConsumerRecord> selectPage(ConsumerRecord model) {
		PagingData<ConsumerRecord> pagingData = new PagingData<ConsumerRecord>();
		if (null == model) {
			logger.warn("---RechargeRecord selectPage, but model is null---");
			return pagingData;
		}

		// Integer queryCount = selectByIndexCount( model );
		//
		// if( null != queryCount && queryCount <= 0 ) {
		// logger.info("RechargeRecord selectPage, but count {} == 0
		// ...",queryCount);
		// return pagingData;
		// }

		int pageNum = model.getPageNum();
		int pageSize = model.getPageSize();
		Page<RechargeRecord> page = PageHelper.startPage(pageNum, pageSize);

		List<ConsumerRecord> users = selectByIndex(model);

		long total = page.getTotal(); // 总条数

		pagingData.setRows(users);
		pagingData.setTotal(total);
		return pagingData;
	}
	
	@Override
	public PagingData<ConsumerRecordVo> selectPage(ConsumerRecordRequest crRequest) {
		PagingData<ConsumerRecordVo> pagingData = new PagingData<ConsumerRecordVo>();
		if (null == crRequest) {
			logger.warn("---RechargeRecord selectPage vo, but model is null---");
			return pagingData;
		}

		int pageNum = crRequest.getPageNum();
		int pageSize = crRequest.getPageSize();
		Page<ConsumerRecordVo> page = PageHelper.startPage(pageNum, pageSize);

		List<ConsumerRecordVo> users = this.selectVoByRequest(crRequest);

		long total = page.getTotal(); // 总条数

		pagingData.setRows(users);
		pagingData.setTotal(total);
		return pagingData;
	}

	private List<ConsumerRecordVo> selectVoByRequest(ConsumerRecordRequest crRequest) {
		return consumerRecordDao.selectVoByRequest(crRequest);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer batchUpdateStatus(Long userId, List<String> listIds) {
		Integer count = 0;
		if (userId == null || listIds == null || listIds.size() == 0) {
			logger.warn("---consumerRecordService batchUpdateStatus,but param is null---");
			return count;
		}

		Integer temp = consumerRecordDao.batchUpdateStatus(userId, listIds);
		return (temp == null) ? count : temp;
	}

	

}
