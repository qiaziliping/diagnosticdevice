package com.launch.diagdevice.dao;

import java.util.List;

import com.launch.diagdevice.common.dao.BaseDAO;
import com.launch.diagdevice.entity.RechargeRecord;
import com.launch.diagdevice.entity.request.RechargeRecordRequest;
import com.launch.diagdevice.entity.vo.RechargeRecordVo;

/**
 * 充值记录DAO
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月14日
 */
public interface RechargeRecordDao extends BaseDAO<RechargeRecord> {

	Integer batchUpdateStatus(Long userId, List<String> listIds);

	Integer updateByOrderNo(RechargeRecord record);

	List<RechargeRecordVo> selectVoByRequest(RechargeRecordRequest rrRequest);
}
