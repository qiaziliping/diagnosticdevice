package com.launch.diagdevice.service;

import java.util.List;

import com.launch.diagdevice.common.service.BaseService;
import com.launch.diagdevice.entity.DeviceGroup;

/**
 * 设备分组service
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月14日
 */
public interface DeviceGroupService extends BaseService<DeviceGroup>{

	List<DeviceGroup> selectAll(DeviceGroup deviceGroup);

	

	
}
