package com.launch.diagdevice.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.launch.diagdevice.common.dao.mapper.BaseMapper;
import com.launch.diagdevice.entity.ConsumerRecord;
import com.launch.diagdevice.entity.request.ConsumerRecordRequest;
import com.launch.diagdevice.entity.vo.ConsumerRecordVo;

/**
 * 消费记录Mapper
 * <p>
 * TODO
 * <p>
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月16日
 */
@Mapper
public interface ConsumerRecordMapper extends BaseMapper<ConsumerRecord>{

	Integer batchUpdateStatus(Map<String, Object> condition);

	List<ConsumerRecordVo> selectVoByRequest(ConsumerRecordRequest crRequest);

}
