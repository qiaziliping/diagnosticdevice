package com.launch.diagdevice.system.service;


import com.launch.diagdevice.system.model.KeyValueExpand;

public interface KeyValueExpandManager {

	/**
	 * 根据key获取表记录对应的实体
	 * @param key
	 * @return
	 */
	public KeyValueExpand queryKeyValueByKey(String key);

	/**
	 * 根据key获取值
	 * @param key
	 * @return
	 */
	public String queryValueByKey(String key);
}
