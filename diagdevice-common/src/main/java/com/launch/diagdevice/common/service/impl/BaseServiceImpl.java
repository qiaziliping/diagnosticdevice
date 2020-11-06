

package com.launch.diagdevice.common.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.launch.diagdevice.common.dao.BaseDAO;
import com.launch.diagdevice.common.model.IdEntity;
import com.launch.diagdevice.common.service.BaseService;

/**
 * @description TODO
 * @author liping
 * @date 2019年11月29日
 */
public abstract class BaseServiceImpl<T extends IdEntity> implements BaseService<T> {

	
	public abstract BaseDAO<T> getDao();
	
	@Override
	public T selectById(Serializable id) {
		return getDao().selectById(id);
	}

	@Override
	public T selectOne(T model) {
		// TODO Auto-generated method stub
		return getDao().selectOne(model);
	}

	@Override
	public Integer save(T e) {
		// TODO Auto-generated method stub
		return getDao().save(e);
	}

	@Override
	public Integer update(T e) {
		// TODO Auto-generated method stub
		return getDao().update(e);
	}

	@Override
	public Integer delete(T e) {
		// TODO Auto-generated method stub
		return getDao().delete(e);
	}

	@Override
	public List<T> selectByIndex(T model) {
		// TODO Auto-generated method stub
		return getDao().selectByIndex(model);
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return getDao().logicDelete(id);
	}

	@Override
	public void batchSave(Collection<T> entities) {
		// TODO Auto-generated method stub
		getDao().batchSave(entities);
	}

	
	

}

