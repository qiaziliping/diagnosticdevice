package com.launch.diagdevice.service;

import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.service.BaseService;
import com.launch.diagdevice.entity.DeviceOptRecord;

/**
 * 设备售后操作记录service
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月14日
 */
public interface DeviceOptRecordService extends BaseService<DeviceOptRecord>{

	PagingData<DeviceOptRecord> selectPage(DeviceOptRecord record);

	
}
