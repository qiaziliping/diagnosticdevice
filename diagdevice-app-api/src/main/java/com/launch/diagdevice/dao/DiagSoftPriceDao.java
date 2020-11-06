package com.launch.diagdevice.dao;

import java.util.List;
import java.util.Map;

import com.launch.diagdevice.common.dao.BaseDAO;
import com.launch.diagdevice.entity.DiagSoftPrice;
import com.launch.diagdevice.entity.request.DiagSoftPriceRequest;
import com.launch.diagdevice.entity.vo.DiagSoftPriceVo;

/**
 * 诊断软件价格service
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月27日
 */
public interface DiagSoftPriceDao extends BaseDAO<DiagSoftPrice>{

	List<DiagSoftPriceVo> selectVoByRequest(DiagSoftPriceRequest dsRequest);

	List<DiagSoftPriceVo> selectByCondition(Map<String, Object> condition);



}
