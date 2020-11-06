package com.launch.diagdevice.dao;

import java.util.List;

import com.launch.diagdevice.common.dao.BaseDAO;
import com.launch.diagdevice.entity.Device;
import com.launch.diagdevice.entity.vo.DeviceVo;

/**
 * 设备DAO
 * @author LIPING
 */
public interface DeviceDao extends BaseDAO<Device>{

	List<DeviceVo> selectVoByCondition(Device model);


}
