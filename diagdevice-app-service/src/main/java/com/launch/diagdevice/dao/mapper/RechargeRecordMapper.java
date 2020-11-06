package com.launch.diagdevice.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.launch.diagdevice.common.dao.mapper.BaseMapper;
import com.launch.diagdevice.entity.RechargeRecord;
import com.launch.diagdevice.entity.request.RechargeRecordRequest;
import com.launch.diagdevice.entity.vo.RechargeRecordVo;

/**
 * 充值记录Mapper
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月14日
 */
@Mapper
public interface RechargeRecordMapper extends BaseMapper<RechargeRecord> {

	Integer batchUpdateStatus(Map<String, Object> condition);

	
	Integer updateByOrderNo(RechargeRecord record);


	List<RechargeRecordVo> selectVoByRequest(RechargeRecordRequest rrRequest);

}
