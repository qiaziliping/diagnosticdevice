package com.launch.diagdevice.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.launch.diagdevice.common.dao.mapper.BaseMapper;
import com.launch.diagdevice.entity.DiagSoft;

/**
 * 诊断软件mapper
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月16日
 */
@Mapper
public interface DiagSoftMapper extends BaseMapper<DiagSoft>{

	List<DiagSoft> selectByCondition(Map<String, Object> condition);

	List<Long> selectPdtTypeAll();


}
