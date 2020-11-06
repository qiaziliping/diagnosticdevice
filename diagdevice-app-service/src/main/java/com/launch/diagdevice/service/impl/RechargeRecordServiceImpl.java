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
import com.launch.diagdevice.dao.RechargeRecordDao;
import com.launch.diagdevice.entity.RechargeRecord;
import com.launch.diagdevice.entity.request.RechargeRecordRequest;
import com.launch.diagdevice.entity.vo.RechargeRecordVo;
import com.launch.diagdevice.service.RechargeRecordService;

@Service(interfaceClass=RechargeRecordService.class)
@Transactional(readOnly = true)
public class RechargeRecordServiceImpl implements RechargeRecordService {

	private Logger logger = LoggerFactory.getLogger(RechargeRecordServiceImpl.class);
	@Autowired
	private RechargeRecordDao rechargeRecordDao;
	
	
	
	@Override
	public PagingData<RechargeRecord> selectPage(RechargeRecord model) {
		PagingData<RechargeRecord> pagingData = new PagingData<RechargeRecord>();
        if( null == model ) {
            logger.warn("---RechargeRecord selectPage, but model is null---");
            return pagingData;
        }

        /*Integer queryCount = selectByIndexCount( model );
        
        if( null != queryCount && queryCount <= 0 ) {
            logger.info("RechargeRecord selectPage, but count {} == 0 ...",queryCount);
            return pagingData;
        }*/
        
        int pageNum = model.getPageNum();
		int pageSize = model.getPageSize();
		Page<RechargeRecord> page = PageHelper.startPage(pageNum, pageSize);
		

        List<RechargeRecord> users =  selectByIndex( model );
        
        // int pages = page.getPages(); 共多少页
		long total = page.getTotal(); // 总条数
        
        pagingData.setRows(users);
        pagingData.setTotal( total );
        return pagingData;
	}
	
	@Override
	public PagingData<RechargeRecordVo> selectPage(RechargeRecordRequest rrRequest) {
		PagingData<RechargeRecordVo> pageData = new PagingData<RechargeRecordVo>();
		if (rrRequest == null) {
			logger.warn("-----rechargeService selectPage vo ,but param is null");
			return pageData;
		}
		Page<RechargeRecordVo> page = PageHelper.startPage(rrRequest.getPageNum(), rrRequest.getPageSize());
		
		List<RechargeRecordVo> listvo = this.selectVoByRequest(rrRequest);
		
		pageData.setRows(listvo);
		pageData.setTotal(page.getTotal());
		return pageData;
	}
	
	
	private List<RechargeRecordVo> selectVoByRequest(RechargeRecordRequest rrRequest) {
		
		return rechargeRecordDao.selectVoByRequest(rrRequest);
	}

	@Override
	public RechargeRecord selectById(Serializable id) {
		return rechargeRecordDao.selectById(id);
	}

	@Override
	public RechargeRecord selectOne(RechargeRecord model) {
		return rechargeRecordDao.selectOne(model);
	}
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	@Override
	public Integer save(RechargeRecord e) {
		return rechargeRecordDao.save(e);
	}
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	@Override
	public Integer update(RechargeRecord e) {
		// TODO Auto-generated method stub
		return null;
	}
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	@Override
	public Integer delete(RechargeRecord e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RechargeRecord> selectByIndex(RechargeRecord model) {
		List<RechargeRecord> result = new ArrayList<RechargeRecord>();
		if (model == null) {
			logger.warn("---RechargeRecordService selectByIndex,bug model is null---");
			return result;
		}
		
		List<RechargeRecord> temp =  rechargeRecordDao.selectByIndex(model);
		return (temp == null) ? result : temp;
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

	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	@Override
	public Integer batchUpdateStatus(Long userId, List<String> listIds) {
		Integer count = 0;
		if (userId == null || listIds == null || listIds.size() == 0) {
			logger.warn("---RechargeRecordService batchUpdate,bug param is null---");
			return count;
		}
		
		Integer temp =  rechargeRecordDao.batchUpdateStatus(userId,listIds);
		return (temp == null) ? count : temp;
	}

	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
	@Override
	public Integer updateByOrderNo(RechargeRecord record) {
		return rechargeRecordDao.updateByOrderNo(record);
	}



}
