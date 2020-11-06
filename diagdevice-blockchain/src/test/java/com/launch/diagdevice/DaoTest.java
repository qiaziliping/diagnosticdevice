package com.launch.diagdevice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.launch.diagdevice.blockchain.dao.CzlAssetRecordDao;
import com.launch.diagdevice.blockchain.dao.ZnhyAccountDao;
import com.launch.diagdevice.blockchain.dao.ZnhyAllocationDao;
import com.launch.diagdevice.blockchain.dao.ZnhyAllocationGroupDao;
import com.launch.diagdevice.blockchain.dao.ZnhyAllocationRuleDao;
import com.launch.diagdevice.blockchain.dao.ZnhyOrderAllocateFailDao;
import com.launch.diagdevice.blockchain.model.CzlOperateLog;
import com.launch.diagdevice.blockchain.model.ZnhyAccount;
import com.launch.diagdevice.blockchain.model.ZnhyAllocation;
import com.launch.diagdevice.blockchain.model.ZnhyAllocationGroup;
import com.launch.diagdevice.blockchain.model.ZnhyAllocationRule;
import com.launch.diagdevice.blockchain.model.ZnhyOrderAllocateFail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiagdeviceBlockchainApplication.class)
public class DaoTest {
	private static Logger log = LoggerFactory.getLogger(DaoTest.class);
	@Autowired
	private ZnhyAccountDao znhyAccountDao;
	@Autowired
	private ZnhyAllocationDao znhyAllocationDao;
	@Autowired
	private ZnhyAllocationGroupDao znhyAllocationGroupDao;
	@Autowired
	private ZnhyAllocationRuleDao znhyAllocationRuleDao;
	@Autowired
	private CzlAssetRecordDao czlAssetRecordDao;
	@Autowired
	private ZnhyOrderAllocateFailDao znhyOrderAllocateFailDao;

	@Ignore
	@Test
	public void createAccountTest() {
		ZnhyAccount za = new ZnhyAccount();
		za.setName("张三");
		za.setAlipay("13987654321");
		za.setTelephone("13987654321");
		za.setCreator("lili.wei");
		znhyAccountDao.saveZnhyAccount(za);
		log.debug("result:" + za.toString());
	}

	@Ignore
	@Test
	public void updateAccountTest() {
		ZnhyAccount za = new ZnhyAccount();
		za.setName("张三");
		za.setAlipay("13987654323");
		za.setAccountId("fenpeibiao_id_21");
		za.setId(54322);
		za.setUpdator("yaoyao");
		znhyAccountDao.updateZnhyAccount(za);
		log.debug("result:" + za.toString());
	}

	@Ignore
	@Test
	public void createAllocationGroupTest() {
		ZnhyAllocationGroup group = new ZnhyAllocationGroup();
		group.setGroupName("智能分账组");
		group.setCreator("xiangrui.ou");
		znhyAllocationGroupDao.saveAllocationGroup(group);
		log.debug("result:");
	}

	@Ignore
	@Test
	public void updateAllocationGroupTest() {
		ZnhyAllocationGroup group = new ZnhyAllocationGroup();
		group.setGroupName("智能分账组");
		group.setUpdator("yaoyao.wei");
		group.setJobGroupId(2);
		znhyAllocationGroupDao.updateAllocationGroup(group);
		log.debug("result:");
	}

	@Ignore
	@Test
	public void createAllocationRuleTest() {
		List<ZnhyAllocationRule> ruleList = new ArrayList<ZnhyAllocationRule>();
		ZnhyAllocationRule rule = new ZnhyAllocationRule();
		rule.setJob("平台罗");
		rule.setRadios(0.2);
		rule.setAccountId("0xdd5f731a37af2f4381f786a85dff4c1407fbbb56");
		rule.setJobGroupId(2);
		ZnhyAllocationRule rule2 = new ZnhyAllocationRule();
		rule2.setJob("经销商欧");
		rule2.setRadios(0.8);
		rule2.setAccountId("0xb8176d9c5bfca056463edd86e7990b3acc301a30");
		rule2.setJobGroupId(2);
		ruleList.add(rule);
		ruleList.add(rule2);
		znhyAllocationRuleDao.saveZnhyAllocationRule(ruleList);
		log.debug("result:");
	}

	@Ignore
	@Test
	public void updateAllocationRuleTest() {
		List<ZnhyAllocationRule> ruleList = new ArrayList<ZnhyAllocationRule>();
		ZnhyAllocationRule rule = new ZnhyAllocationRule();
		rule.setJob("platform");
		rule.setRadios(0.4);
		rule.setAccountId("id_54321");
		rule.setJobGroupId(2);
		ZnhyAllocationRule rule2 = new ZnhyAllocationRule();
		rule2.setJob("ower");
		rule2.setRadios(0.6);
		rule2.setAccountId("id_54322");
		rule2.setJobGroupId(2);
		ruleList.add(rule);
		ruleList.add(rule2);
		znhyAllocationRuleDao.updateAllocationRule(ruleList);
		log.debug("result:");
	}

	@Ignore
	@Test
	public void createAllocationTest() {
		ZnhyAllocation allocation = new ZnhyAllocation();
		allocation.setJobGroupId(2);
		allocation.setName("978547654321");
		allocation.setCreator("54322");
		allocation.setAssetsType(0);
		znhyAllocationDao.saveZnhyAllocation(allocation);
		log.debug("result:");
	}

	@Ignore
	@Test
	public void updateAllocationTest() {
		ZnhyAllocation allocation = new ZnhyAllocation();
		allocation.setJobGroupId(2);
		allocation.setName("978547654321");
		allocation.setAllocationId("fenpei_id_002");
		allocation.setUpdator("system");
		allocation.setId(3);
		znhyAllocationDao.updateZnhyAllocation(allocation);
		log.debug("result:");
	}

	@Ignore
	@Test
	public void queryAllocationTest() {
		ZnhyAllocation allocation = new ZnhyAllocation();
		allocation.setAllocationId("fenpei_id_002");
		allocation.setId(3);
		allocation = znhyAllocationDao.queryZnhyAllocation(allocation);
		log.debug("result:" + allocation.toString());
	}

	@Ignore
	@Test
	public void saveCzlOperateLogTest() {
		CzlOperateLog czlOperateLog = new CzlOperateLog();
		czlOperateLog.setRecordType("测试记录");
		czlOperateLog.setJsonDate("{json date}");
		czlOperateLog.setRecordId("234");
		czlOperateLog.setCreateDate(new Date());
		czlAssetRecordDao.saveCzlOperateLog(czlOperateLog);
		log.debug("result:" + czlOperateLog.toString());
	}
//	@Ignore
	@Test
	public void saveZnhyOrderAllocateFailTest() {
		ZnhyOrderAllocateFail zaf=new ZnhyOrderAllocateFail();
		zaf.setOrderId(102);
		zaf.setSerialNo("987654321021");
		zaf.setCode(4001);
		zaf.setMessage("账户余额不足");
		zaf.setCreateTime(new Date());
		zaf.setSuccess(0);
		znhyOrderAllocateFailDao.saveZnhyOrderAllocateFail(zaf);
	}

}
