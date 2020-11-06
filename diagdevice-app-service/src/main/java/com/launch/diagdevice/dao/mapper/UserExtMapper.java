package com.launch.diagdevice.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.launch.diagdevice.common.dao.mapper.BaseMapper;
import com.launch.diagdevice.entity.UserExt;

@Mapper
public interface UserExtMapper extends BaseMapper<UserExt>{

	Integer updateByUserId(UserExt userExt);

}
