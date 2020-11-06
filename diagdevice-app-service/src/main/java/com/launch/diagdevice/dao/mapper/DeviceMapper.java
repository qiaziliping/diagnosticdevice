package com.launch.diagdevice.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.launch.diagdevice.common.dao.mapper.BaseMapper;
import com.launch.diagdevice.entity.Device;
import com.launch.diagdevice.entity.vo.DeviceVo;

/**
 * 设备Mapper
 * @author LIPING
 * @version 0.0.1
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

	
	List<DeviceVo> selectVoByCondition(Device model);

}
