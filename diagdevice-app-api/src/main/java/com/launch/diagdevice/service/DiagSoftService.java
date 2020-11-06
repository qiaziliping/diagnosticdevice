package com.launch.diagdevice.service;

import java.util.List;
import java.util.Map;

import com.launch.diagdevice.common.service.BaseService;
import com.launch.diagdevice.entity.DiagSoft;

/**
 * 诊断软件service
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月16日
 */
public interface DiagSoftService extends BaseService<DiagSoft> {

	List<DiagSoft> selectByCondition(Map<String,Object> condition);

	/**
	 * 获取诊断软件表所有的产品类型ID
	 */
	List<Long> selectPdtTypeAll();


}
