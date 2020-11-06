package com.launch.diagdevice.service;

import java.util.List;
import java.util.Map;

import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.service.BaseService;
import com.launch.diagdevice.entity.DiagSoftPrice;
import com.launch.diagdevice.entity.request.DiagSoftPriceRequest;
import com.launch.diagdevice.entity.vo.DiagSoftPriceVo;

/**
 * 诊断软件价格service
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月16日
 */
public interface DiagSoftPriceService extends BaseService<DiagSoftPrice> {

	PagingData<DiagSoftPriceVo> selectPageVo(DiagSoftPriceRequest dsRequest);

	/**
	 * 根据条件查询诊断价格列表，不分页
	 */
	List<DiagSoftPriceVo> selectByCondition(Map<String, Object> condition);



}
