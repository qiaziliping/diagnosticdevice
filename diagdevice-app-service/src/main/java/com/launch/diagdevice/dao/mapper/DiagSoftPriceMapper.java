package com.launch.diagdevice.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.launch.diagdevice.common.dao.mapper.BaseMapper;
import com.launch.diagdevice.entity.DiagSoftPrice;
import com.launch.diagdevice.entity.request.DiagSoftPriceRequest;
import com.launch.diagdevice.entity.vo.DiagSoftPriceVo;

/**
 * 诊断软件价格mapper
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月16日
 */
@Mapper
public interface DiagSoftPriceMapper extends BaseMapper<DiagSoftPrice>{

	List<DiagSoftPriceVo> selectVoByRequest(DiagSoftPriceRequest dsRequest);

	List<DiagSoftPriceVo> selectByCondition(Map<String, Object> condition);


}
