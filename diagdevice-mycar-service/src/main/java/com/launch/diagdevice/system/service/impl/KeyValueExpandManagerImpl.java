package com.launch.diagdevice.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.alibaba.dubbo.config.annotation.Service;
import com.launch.diagdevice.system.dao.KeyValueExpandDao;
import com.launch.diagdevice.system.model.KeyValueExpand;
import com.launch.diagdevice.system.service.KeyValueExpandManager;

@Service(interfaceClass = KeyValueExpandManager.class)
public class KeyValueExpandManagerImpl implements KeyValueExpandManager {
	@Autowired
	private KeyValueExpandDao keyValueExpandDao;

	@Override
	@Cacheable(cacheNames={"diaglog_bus"},keyGenerator="cacheKeyGenerator",unless="#result == null")
	public KeyValueExpand queryKeyValueByKey(String key) {
		return keyValueExpandDao.queryKeyValueByKey(key);
	}

	@Override
	@Cacheable(cacheNames={"diaglog_bus"},keyGenerator="cacheKeyGenerator",unless="#result == null")
	public String queryValueByKey(String key) {
		if (key == null || key.isEmpty())
			return null;

		KeyValueExpand expand = keyValueExpandDao.queryKeyValueByKey(key);
		if (expand == null)
			return null;

		return expand.getValue();
	}

}
