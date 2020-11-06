package com.launch.diagdevice.service;

import java.util.List;

import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.service.BaseService;
import com.launch.diagdevice.entity.ConsumerRecord;
import com.launch.diagdevice.entity.request.ConsumerRecordRequest;
import com.launch.diagdevice.entity.vo.ConsumerRecordVo;

/**
 * 消费记录service
 * <p>
 * TODO
 * <p>
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月16日
 */
public interface ConsumerRecordService extends BaseService<ConsumerRecord> {

	PagingData<ConsumerRecord> selectPage(ConsumerRecord model);

	Integer batchUpdateStatus(Long userId, List<String> listIds);

	/**
	 * 
	 * 获取设备列表
	 * @return 返回消费记录VO对象，包括用户名
	 * LIPING
	 */
	PagingData<ConsumerRecordVo> selectPage(ConsumerRecordRequest crRequest);

}
