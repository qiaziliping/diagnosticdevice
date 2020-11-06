package com.launch.diagdevice.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.launch.diagdevice.common.dao.mapper.BaseMapper;
import com.launch.diagdevice.entity.DeviceGroup;

/**
 * 设备分组Mapper
 * @author LIPING
 * @version 0.0.1
 */
@Mapper
public interface DeviceGroupMapper extends BaseMapper<DeviceGroup> {

	List<DeviceGroup> selectAll(DeviceGroup model);

	
}
