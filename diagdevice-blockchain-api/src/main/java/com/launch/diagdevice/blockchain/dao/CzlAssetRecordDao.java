package com.launch.diagdevice.blockchain.dao;

import com.launch.diagdevice.blockchain.model.CzlAssetRecord;
import com.launch.diagdevice.blockchain.model.CzlOperateLog;
import com.launch.diagdevice.blockchain.model.Device;
import com.launch.diagdevice.blockchain.model.UserConsumerRecord;
import com.launch.diagdevice.blockchain.model.UserOrder;

public interface CzlAssetRecordDao {

	void saveCzlAssetRecord(CzlAssetRecord assetRecord);

	CzlAssetRecord queryCzlAssetRecord();

	/**
	 * 根据序列号查询设备，以生成记录hash，供设备记录上链
	 * 
	 * @param serialNo
	 * @return
	 * @since DBS V100
	 */
	Device queryDeviceById(String deviceId);
	// /**
	// * 根据序列号
	// * @param serialNo
	// * @return
	// */
	// public Device queryDeviceBySerialNo(String serialNo);

	/**
	 * 根据orderId查询信息，以生成记录hash，供用户资产消费记录上链
	 * 
	 * @param orderId
	 * @return
	 * @since DBS V100
	 */
	UserConsumerRecord queryConsumeRecordByOrderId(Integer orderId);

	/**
	 * 根据订单id查询支付记录信息，以生成记录hash，供用户资产订单上链
	 * 
	 * @param id
	 * @return
	 * @since DBS V100
	 */
	UserOrder queryUserOrderById(Integer id);

	/**
	 * 记录操作日志，以备作为数字资产变更记录上链
	 * 
	 * @param CzlOperateLog
	 */
	void saveCzlOperateLog(CzlOperateLog czlOperateLog);
	
	/**
	 * 根据条件查询操作日志记录
	 * @param czlOperateLog
	 * @return
	 */
	CzlOperateLog queryCzlOperateLog(CzlOperateLog czlOperateLog);
}
