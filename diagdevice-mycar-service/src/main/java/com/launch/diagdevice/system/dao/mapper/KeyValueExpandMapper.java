package com.launch.diagdevice.system.dao.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.launch.diagdevice.system.model.KeyValueExpand;
//@Mapper
public interface KeyValueExpandMapper {
	@Select("select * from KEY_VALUE_EXPAND where KEY = #{key} ")
	@Results({ @Result(property = "key", column = "KEY"), @Result(property = "value", column = "VALUE"),
			@Result(property = "functionDesc", column = "FUNCTION_DESC"),
			@Result(property = "remark", column = "REMARK"), @Result(property = "oldValue", column = "OLD_VALUE"),
			@Result(property = "updatePeople", column = "UPDATE_PEOPLE"),
			@Result(property = "updateTime", column = "UPDATE_TIME") })
	public KeyValueExpand queryKeyValueByKey(String key);

//	@Select("select * from dual")
	public String queryValueByKey(String key);
}
