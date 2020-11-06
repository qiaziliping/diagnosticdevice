package com.launch.diagdevice.dao;

import java.util.List;

import com.launch.diagdevice.common.dao.BaseDAO;
import com.launch.diagdevice.entity.DeviceGroup;

/**
 * 设备分组DAO
 * @author LIPING
 */
public interface DeviceGroupDao extends BaseDAO<DeviceGroup>{

	List<DeviceGroup> selectAll(DeviceGroup deviceGroup);


}
