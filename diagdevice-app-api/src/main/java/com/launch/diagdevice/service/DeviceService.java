package com.launch.diagdevice.service;

import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.service.BaseService;
import com.launch.diagdevice.entity.Device;
import com.launch.diagdevice.entity.vo.DeviceVo;

/**
 * 设备service
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月14日
 */
public interface DeviceService extends BaseService<Device>{

	
	PagingData<DeviceVo> selectPage(Device model);

	
}
