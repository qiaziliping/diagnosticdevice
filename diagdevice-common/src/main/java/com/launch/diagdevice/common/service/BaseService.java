package com.launch.diagdevice.common.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.launch.diagdevice.common.model.IdEntity;

/**
 * service
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月13日
 */
public interface BaseService<T extends IdEntity> {

	/**
	 * 根据id获取实体对象.
	 * @param entity
	 * @return e
	 */
	public T selectById(Serializable id);

	/**
	 * 根据id获取实体对象.
	 * @param entity
	 * @return e
	 */
	public T selectOne(T model);

	/**
	 * 存储实体对象.
	 * @param entity
	 * @return void
	 */
	public Integer save(T e);

	/**
	 * 更新实体对象.
	 * @param entity
	 * @return void
	 */
	public Integer update(T e);

	/**
	 * 删除实体对象.
	 * @param entity
	 * @return int
	 */
	public Integer delete(T e);

	/**
	 * 条件查询列表
	 * @param baseForm
	 * @return
	 */
	public abstract List<T> selectByIndex(T model);
	
//	public abstract Integer selectByIndexCount(E model);


	/**
	 * 逻辑删除一个实体对象___将其isDel字段的值为1.
	 * 
	 * @param id
	 * @return integer
	 */
	public abstract Integer logicDelete(Serializable id);

	/**
	 * 批量更新实体.
	 * 
	 * @param entities 要更新的对象集合
	 * @return void
	 */
	public abstract void batchSave(Collection<T> entities);



}