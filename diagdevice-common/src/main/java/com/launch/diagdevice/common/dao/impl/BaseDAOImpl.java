

package com.launch.diagdevice.common.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.launch.diagdevice.common.dao.BaseDAO;
import com.launch.diagdevice.common.dao.mapper.BaseMapper;
import com.launch.diagdevice.common.model.IdEntity;
import com.launch.diagdevice.common.service.BaseService;

/**
 * @description TODO
 * @author liping
 * @date 2019年11月29日
 */
public abstract class BaseDAOImpl<T extends IdEntity> implements BaseService<T> {

	
	public abstract BaseMapper<T> getMapper();
	
	@Override
	public T selectById(Serializable id) {
		return getMapper().selectById(id);
	}

	@Override
	public T selectOne(T model) {
		// TODO Auto-generated method stub
		return getMapper().selectOne(model);
	}

	@Override
	public Integer save(T e) {
		// TODO Auto-generated method stub
		return getMapper().save(e);
	}

	@Override
	public Integer update(T e) {
		// TODO Auto-generated method stub
		return getMapper().update(e);
	}

	@Override
	public Integer delete(T e) {
		// TODO Auto-generated method stub
		return getMapper().delete(e);
	}

	@Override
	public List<T> selectByIndex(T model) {
		// TODO Auto-generated method stub
		return getMapper().selectByIndex(model);
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return getMapper().logicDelete(id);
	}

	@Override
	public void batchSave(Collection<T> entities) {
		// TODO Auto-generated method stub
		getMapper().batchSave(entities);
	}

	
	

}

