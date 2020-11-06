package com.launch.diagdevice.dao;

import java.util.List;

import com.launch.diagdevice.common.dao.BaseDAO;
import com.launch.diagdevice.entity.ConsumerRecord;
import com.launch.diagdevice.entity.request.ConsumerRecordRequest;
import com.launch.diagdevice.entity.vo.ConsumerRecordVo;

/**
 * 消费记录DAO
 * <p>
 * TODO
 * <p>
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月16日
 */
public interface ConsumerRecordDao extends BaseDAO<ConsumerRecord>{

	Integer batchUpdateStatus(Long userId, List<String> listIds);

	List<ConsumerRecordVo> selectVoByRequest(ConsumerRecordRequest crRequest);

}
