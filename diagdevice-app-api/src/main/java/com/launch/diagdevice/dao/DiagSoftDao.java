package com.launch.diagdevice.dao;

import java.util.List;
import java.util.Map;

import com.launch.diagdevice.common.dao.BaseDAO;
import com.launch.diagdevice.entity.DiagSoft;

/**
 * 诊断软件service
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月27日
 */
public interface DiagSoftDao extends BaseDAO<DiagSoft>{

	List<DiagSoft> selectByCondition(Map<String,Object> condition);

	List<Long> selectPdtTypeAll();


}
