package com.launch.diagdevice.blockchain.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.blockchain.dao.CzlAssetRecordDao;
import com.launch.diagdevice.blockchain.dao.mapper.CzlAssetRecordMapper;
import com.launch.diagdevice.blockchain.model.CzlAssetRecord;
import com.launch.diagdevice.blockchain.model.CzlOperateLog;
import com.launch.diagdevice.blockchain.model.Device;
import com.launch.diagdevice.blockchain.model.UserConsumerRecord;
import com.launch.diagdevice.blockchain.model.UserOrder;

@Component
public class CzlAssetRecordDaoImpl implements CzlAssetRecordDao {

	@Autowired
	private CzlAssetRecordMapper czlAssetRecordMapper;

	@Override
	public void saveCzlAssetRecord(CzlAssetRecord assetRecord) {
		// 保存本地记录
		czlAssetRecordMapper.saveCzlAssetRecord(assetRecord);
	}

	@Override
	public CzlAssetRecord queryCzlAssetRecord() {

		return null;
	}

	@Override
	public Device queryDeviceById(String deviceId) {
		return czlAssetRecordMapper.queryDeviceById(deviceId);
	}

	// @Override
	// public Device queryDeviceBySerialNo(String serialNo)
	// {
	// return czlAssetRecordMapper.queryDeviceBySerialNo(serialNo);
	// }

	@Override
	public UserConsumerRecord queryConsumeRecordByOrderId(Integer orderId) {
		return czlAssetRecordMapper.queryConsumeRecordByOrderId(orderId);
	}

	@Override
	public UserOrder queryUserOrderById(Integer id) {
		return czlAssetRecordMapper.queryUserOrderById(id);
	}

	@Override
	public void saveCzlOperateLog(CzlOperateLog czlOperateLog) {
		czlAssetRecordMapper.saveCzlOperateLog(czlOperateLog);
	}

	@Override
	public CzlOperateLog queryCzlOperateLog(CzlOperateLog czlOperateLog) {
		return czlAssetRecordMapper.queryCzlOperateLog(czlOperateLog);
	}

}
