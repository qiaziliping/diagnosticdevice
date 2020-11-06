package com.launch.diagdevice.service;

import java.util.Map;

import com.launch.diagdevice.common.service.BaseService;
import com.launch.diagdevice.entity.RechargeRecord;
import com.launch.diagdevice.entity.UserExt;


public interface UserExtService extends BaseService<UserExt> {


	/**
	 * 根据充值记录计算账户金额
	 * LIPING
	 * @param rrMap 
	 */
	@Deprecated
	Integer updateMoneyByRechargeRecord(RechargeRecord record, Map<String, String> rrMap);

}
