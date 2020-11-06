package com.launch.diagdevice.service;

import java.util.List;

import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.service.BaseService;
import com.launch.diagdevice.entity.RechargeRecord;
import com.launch.diagdevice.entity.request.RechargeRecordRequest;
import com.launch.diagdevice.entity.vo.RechargeRecordVo;

/**
 * 充值记录Service
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月14日
 */
public interface RechargeRecordService extends BaseService<RechargeRecord> {

	
	PagingData<RechargeRecord> selectPage(RechargeRecord model);

	/**
	 * 批量修改，根据主键进行修改
	 * LIPING
	 */
	Integer batchUpdateStatus(Long userId, List<String> listIds);
	/**
	 * 根据订单号进行修改
	 * LIPING
	 */
	Integer updateByOrderNo(RechargeRecord record);

	PagingData<RechargeRecordVo> selectPage(RechargeRecordRequest rrRequest);
	
	

}
