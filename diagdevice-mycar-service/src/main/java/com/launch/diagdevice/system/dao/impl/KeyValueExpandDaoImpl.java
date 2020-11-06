package com.launch.diagdevice.system.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.launch.diagdevice.system.dao.KeyValueExpandDao;
import com.launch.diagdevice.system.dao.mapper.KeyValueExpandMapper;
import com.launch.diagdevice.system.model.KeyValueExpand;

@Repository
public class KeyValueExpandDaoImpl implements KeyValueExpandDao {
	@Autowired
	private KeyValueExpandMapper mapper;

	@Override
	public KeyValueExpand queryKeyValueByKey(String key) {
		return mapper.queryKeyValueByKey(key);
	}

	@Override
	public String queryValueByKey(String key) {
		// TODO 自动生成的方法存根
		return null;
	}
}
