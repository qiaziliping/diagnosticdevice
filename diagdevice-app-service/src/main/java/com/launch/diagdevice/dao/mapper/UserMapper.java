package com.launch.diagdevice.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.launch.diagdevice.common.dao.mapper.BaseMapper;
import com.launch.diagdevice.entity.User;
import com.launch.diagdevice.entity.vo.UserAndExtVo;

/**
 * @Mapper表示为映射对象类
 * 
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月1日
 */
@Mapper
public interface UserMapper extends BaseMapper<User>{

	User selectByUniqueFlag(String unique);

	List<UserAndExtVo> selectUserAndExtVoByIndex(User user);

	

}
