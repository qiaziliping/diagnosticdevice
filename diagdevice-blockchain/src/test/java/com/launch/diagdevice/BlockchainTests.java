package com.launch.diagdevice;

import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.launch.diagdevice.blockchain.AssetReply;
import com.launch.diagdevice.blockchain.client.service.CzlClientService;
import com.launch.diagdevice.blockchain.dao.CzlAssetRecordDao;
import com.launch.diagdevice.blockchain.model.CzlAsset;
import com.launch.diagdevice.blockchain.model.CzlAssetRecord;
import com.launch.diagdevice.blockchain.service.CzService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiagdeviceBlockchainApplication.class)
public class BlockchainTests {
	private static Logger log = LoggerFactory.getLogger(BlockchainTests.class);
	@Autowired
	private CzlClientService clientService;

	@Autowired
	private CzService czlCzService;
	
	@Autowired
	private CzlAssetRecordDao czlAssetRecordDao;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	 @Ignore
	@Test
	public void createAssetTest() {
//		CzlAsset czlAsset = new CzlAsset();
//		czlAsset.setAssetName("983541234567");
//		czlAsset.setAssetType("诊断设备");
//
//		this.rabbitTemplate.convertAndSend("czlAssetCreateL", czlAsset);

		 CzlAsset czlAsset = new CzlAsset();
		 czlAsset.setAssetName("983541234567");
		 czlAsset.setAssetType("诊断设备");
		 czlCzService.createAsset(czlAsset);

	}

	@Ignore
	@Test
	public void assetOfTest() {
		AssetReply result = clientService.assetOf("41940874-9599-4e31-a521-0f51b6d8ac3e");
		log.debug("-------------assetOfTest result:" + new Gson().toJson(result));
	}

	@Ignore
	@Test
	public void appendRecordTest() {
		
//		Device device= czlAssetRecordDao.queryDeviceById("14");
//		System.out.println(new Gson().toJson(device));

		CzlAssetRecord czlAssetRecord = new CzlAssetRecord();
		czlAssetRecord.setRecordId("2");
		czlAssetRecord.setRecordType("设备变更记录");
		czlCzService.appendAssetRecord(czlAssetRecord);
//		rabbitTemplate.convertAndSend("czlAssetRecordCreateL", czlAssetRecord);
	}

	@Ignore
	@Test
	public void assetRecordOfTest() {
		// (String assetId, String recordName, String content, String metaHash)
		Map<String, Object> result = clientService.assetRecordOf("s123456", "sdfsdfsdf");
		log.debug("assetRecordOf result:" + new Gson().toJson(result));
	}

}
