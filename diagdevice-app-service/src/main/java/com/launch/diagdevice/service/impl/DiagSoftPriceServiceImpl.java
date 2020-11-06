package com.launch.diagdevice.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.dao.DiagSoftDao;
import com.launch.diagdevice.dao.DiagSoftPriceDao;
import com.launch.diagdevice.entity.DiagSoft;
import com.launch.diagdevice.entity.DiagSoftPrice;
import com.launch.diagdevice.entity.request.DiagSoftPriceRequest;
import com.launch.diagdevice.entity.vo.DiagSoftPriceVo;
import com.launch.diagdevice.service.DiagSoftPriceService;

@Service(interfaceClass = DiagSoftPriceService.class)
@Transactional(readOnly = true)
public class DiagSoftPriceServiceImpl implements DiagSoftPriceService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DiagSoftPriceDao diagSoftPriceDao;
	
	@Autowired
	private DiagSoftDao diagSoftDao;

	@Override
	public DiagSoftPrice selectById(Serializable id) {
		return diagSoftPriceDao.selectById(id);
	}

	@Override
	public DiagSoftPrice selectOne(DiagSoftPrice model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	@Override
	public Integer save(DiagSoftPrice e) {
		if (null == e) {
			logger.warn("-----diag soft service save,but model is null" + e);
			return 0;
		}
		int count = diagSoftPriceDao.save(e);
		// 价格添加则默认激活
		if (count > 0) {
			DiagSoft ds = new DiagSoft();
			ds.setSoftId(e.getSoftId());
			ds.setIsActive(1);
			diagSoftDao.update(ds);
		}
		return count;
	}

	@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	@Override
	public Integer update(DiagSoftPrice model) {
		if (null == model || StringUtils.isEmpty(model.getId())) {
			logger.warn("-----diag softprice service update,but model is null" + model);
			return 0;
		}
		return diagSoftPriceDao.update(model);
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
	public PagingData<DiagSoftPriceVo> selectPageVo(DiagSoftPriceRequest dsRequest) {
		PagingData<DiagSoftPriceVo> pagingData = new PagingData<DiagSoftPriceVo>();
		if (null == dsRequest) {
			logger.warn("---DiagSoftPrice service selectPage vo, but model is null---");
			return pagingData;
		}

		int pageNum = dsRequest.getPageNum();
		int pageSize = dsRequest.getPageSize();
		Page<DiagSoftPriceVo> page = PageHelper.startPage(pageNum, pageSize);

		List<DiagSoftPriceVo> users = this.selectVoByRequest(dsRequest);

		long total = page.getTotal(); // 总条数

		pagingData.setRows(users);
		pagingData.setTotal(total);
		return pagingData;
	}

	private List<DiagSoftPriceVo> selectVoByRequest(DiagSoftPriceRequest dsRequest) {
		
		return diagSoftPriceDao.selectVoByRequest(dsRequest);
	}

	@Override
	public List<DiagSoftPriceVo> selectByCondition(Map<String, Object> condition) {
		return diagSoftPriceDao.selectByCondition(condition);
	}

}
