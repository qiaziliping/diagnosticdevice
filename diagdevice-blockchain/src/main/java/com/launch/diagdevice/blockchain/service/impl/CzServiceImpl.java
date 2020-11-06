package com.launch.diagdevice.blockchain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.launch.diagdevice.blockchain.client.service.CzlClientService;
import com.launch.diagdevice.blockchain.dao.CzlAssetDao;
import com.launch.diagdevice.blockchain.dao.CzlAssetRecordDao;
import com.launch.diagdevice.blockchain.model.CzlAsset;
import com.launch.diagdevice.blockchain.model.CzlAssetRecord;
import com.launch.diagdevice.blockchain.model.CzlOperateLog;
import com.launch.diagdevice.blockchain.model.UserConsumerRecord;
import com.launch.diagdevice.blockchain.model.UserOrder;
import com.launch.diagdevice.blockchain.service.CzService;
import com.launch.diagdevice.blockchain.utils.JsonUtil;
import com.launch.diagdevice.common.util.MD5Util;

@Service(interfaceClass = CzService.class)
public class CzServiceImpl implements CzService {
	private static Logger log=LoggerFactory.getLogger(CzService.class);
	@Autowired
	private CzlAssetDao czlAssetDao;
	@Autowired
	private CzlAssetRecordDao czlAssetRecordDao;
	@Autowired
	private CzlClientService CzlClient;

	@Override
	public void createAsset(CzlAsset czlAsset) {
		CzlAsset asset = czlAssetDao.saveCzlAsset(czlAsset);
		CzlClient.createAsset(asset.getAssetId(), asset.getAssetName(), asset.getAssetType(), czlAsset.getAssetHash());
		log.info("createAsset:--------------" + asset.toString());
	}

	@Override
	public void appendAssetRecord(CzlAssetRecord assetRecord) {
		if (assetRecord.getRecordType().equals("设备变更记录")) {
			assetRecord = appendDeviceRecord(assetRecord.getRecordId(), assetRecord.getRecordType());
		} else if (assetRecord.getRecordType().equals("用户订单记录")) {
			assetRecord = appendUserOrderRecord(Integer.valueOf(assetRecord.getRecordId()),
					assetRecord.getRecordType());
		} else if (assetRecord.getRecordType().equals("用户消费记录")) {
			assetRecord = appendUserCosumeRecord(Integer.valueOf(assetRecord.getRecordId()),
					assetRecord.getRecordType());
		}
		// 资产记录上链
		CzlClient.appendRecord(assetRecord.getAssetId(), assetRecord.getRecordType() + assetRecord.getRecordId(),
				assetRecord.getContent(), assetRecord.getMetaHash());

		// 保存本地记录
		czlAssetRecordDao.saveCzlAssetRecord(assetRecord);
		log.info("appendAssetRecord:--------------" + assetRecord.toString());

	}

	/**
	 * 待改进，需建一个业务日志表
	 * 
	 * @param deviceId
	 * @return
	 */
	private CzlAssetRecord appendDeviceRecord(String logId, String recordType) {
		CzlOperateLog log = new CzlOperateLog();
		log.setId(Integer.valueOf(logId));
		CzlOperateLog record = czlAssetRecordDao.queryCzlOperateLog(log);
		// 通过订单id查询到资产记录信息
		CzlAsset asset = czlAssetDao.queryCzlAsset(record.getRecordId(), record.getRecordType());

		return buildCzlAssetRecord(record, asset, recordType, logId, CzlOperateLog.class.getSimpleName());
	}

	private CzlAssetRecord appendUserOrderRecord(Integer orderId, String recordType) {
		// 待上链数据记录
		UserOrder record = czlAssetRecordDao.queryUserOrderById(orderId);
		// 通过订单id查询到资产记录信息
		CzlAsset asset = czlAssetDao.queryCzlUserAsset(orderId);

		return buildCzlAssetRecord(record, asset, recordType, String.valueOf(orderId), UserOrder.class.getSimpleName());
	}

	private CzlAssetRecord appendUserCosumeRecord(Integer orderId, String recordType) {
		// 待上链数据记录
		UserConsumerRecord record = czlAssetRecordDao.queryConsumeRecordByOrderId(orderId);
		// 通过订单id查询到资产记录信息
		CzlAsset asset = czlAssetDao.queryCzlUserAsset(orderId);

		return buildCzlAssetRecord(record, asset, recordType, String.valueOf(orderId),
				UserConsumerRecord.class.getSimpleName());
	}

	private CzlAssetRecord buildCzlAssetRecord(Object record, CzlAsset asset, String recordType, String recordId,
			String className) {
		// 构建本地记录和链上记录关联关系
		CzlAssetRecord rc = new CzlAssetRecord();
		// 构建本地记录和链上记录关联关系
		rc.setAssetId(asset.getAssetId());
		String content = JsonUtil.toJson(record);
		String metaHash = MD5Util.MD5(content);
		// 设置记录hash值，查询资产记录用
		rc.setMetaHash(metaHash);
		// 构建本地坐标
		rc.setRecordId(recordId);
		rc.setRecordType(recordType);
		rc.setClassName(className);
		// 内容不能超过100长度处理
		if (content.length() > 99)
			content = content.substring(0, 80);
		rc.setContent(content);
		return rc;

	}

}
