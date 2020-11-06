package com.launch.diagdevice.system.dao.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.launch.diagdevice.system.model.SysUser;
//@Mapper
public interface SysUserMapper {
	@Select("SELECT * FROM SYS_USER WHERE USER_ID = #{userId} ")
	@Results({ @Result(property = "userId", column = "USER_ID"), @Result(property = "cc", column = "CC"),
			@Result(property = "email", column = "EMAIL"), @Result(property = "gender", column = "GENDER"),
			@Result(property = "mobile", column = "MOBILE"), @Result(property = "regTime", column = "REG_TIME"),
			@Result(property = "state", column = "STATE"),@Result(property = "userName", column = "USER_NAME") })
	public SysUser getSysUser(Integer userId);
}
