package com.launch.diagdevice.system.dao;

import com.launch.diagdevice.system.model.KeyValueExpand;

public interface KeyValueExpandDao {
	/**
	 * 
	 * @param key
	 * @return
	 */
	public KeyValueExpand queryKeyValueByKey(String key);

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String queryValueByKey(String key);
}
