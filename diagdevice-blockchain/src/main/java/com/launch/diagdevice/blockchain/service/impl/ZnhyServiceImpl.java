package com.launch.diagdevice.blockchain.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.launch.diagdevice.blockchain.dao.ZnhyAccountDao;
import com.launch.diagdevice.blockchain.dao.ZnhyAllocationDao;
import com.launch.diagdevice.blockchain.dao.ZnhyAllocationGroupDao;
import com.launch.diagdevice.blockchain.dao.ZnhyAllocationRuleDao;
import com.launch.diagdevice.blockchain.model.ZnhyAccount;
import com.launch.diagdevice.blockchain.model.ZnhyAllocation;
import com.launch.diagdevice.blockchain.model.ZnhyAllocationGroup;
import com.launch.diagdevice.blockchain.model.ZnhyAllocationRule;
import com.launch.diagdevice.blockchain.service.ZnhyService;
import com.launch.diagdevice.blockchain.vo.ZnhyAccountVo;
import com.launch.diagdevice.blockchain.vo.ZnhyAllocationVo;
import com.launch.diagdevice.common.constant.Constants;
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.model.PagingEntity;

@Service(interfaceClass = ZnhyService.class)
@Transactional(readOnly = true)
public class ZnhyServiceImpl implements ZnhyService {
	private static Logger log=LoggerFactory.getLogger(ZnhyServiceImpl.class);
	@Autowired
	private ZnhyAccountDao znhyAccountDao;
	@Autowired
	private ZnhyAllocationDao znhyAllocationDao;
	@Autowired
	private ZnhyAllocationGroupDao znhyAllocationGroupDao;
	@Autowired
	private ZnhyAllocationRuleDao znhyAllocationRuleDao;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void createAccount(ZnhyAccount za) {
		znhyAccountDao.saveZnhyAccount(za);
		this.rabbitTemplate.convertAndSend("znhyAccountCreate"+Constants.getTestQueueFlag(), za);
		log.info("createAccount send success----------------------"+"znhyAccountCreate"+Constants.getTestQueueFlag());
	}

	@Override
	public ZnhyAccount getZnhyAccount(int accountId) {
		return znhyAccountDao.getZnhyAccount(accountId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void updateAccount(ZnhyAccountVo vo) {
		// 先将原来的账户置空
		Integer atOld = vo.getAccountTypeOld();
		Integer accountType = vo.getAccountType();
		if (null != atOld && atOld > 0 && !accountType.equals(atOld)) {
			updateAccountNullByAccountTypeOld(vo);
		}
		
		ZnhyAccount account = new ZnhyAccount();
		BeanUtils.copyProperties(vo, account);
		String accountName = vo.getAccountName();
		switch (accountType) {
			case Constants.ACCOUNT_NAME_ALIPAY:
				account.setAlipay(accountName);
				break;
			case Constants.ACCOUNT_NAME_WECHAT:
				account.setWeChat(accountName);
				break;
			case Constants.ACCOUNT_NAME_PAYPAL:
				account.setPaypal(accountName);
				break;
			case Constants.ACCOUNT_NAME_BANK_CARD:
				account.setBankCard(accountName);
				break;
			default:
				break;
		}
		
		znhyAccountDao.updateZnhyAccount(account);
		this.rabbitTemplate.convertAndSend("znhyAccountUpdate"+Constants.getTestQueueFlag(), account);
		log.info("updateAccount send success----------------------"+"znhyAccountUpdate"+Constants.getTestQueueFlag());
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateAccountNullByAccountTypeOld(ZnhyAccountVo za) {
		znhyAccountDao.updateAccountNullByAccountTypeOld(za);
	}

	@Override
	public List<ZnhyAccount> queryZnhyAccount(ZnhyAccount za) {
		return znhyAccountDao.queryZnhyAccount(za);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void createAllocationGroup(ZnhyAllocationGroup group) {
		znhyAllocationGroupDao.saveAllocationGroup(group);

	}

	@Override
	public ZnhyAllocationGroup getAllocationGroup(int jobGroupId) {
		return znhyAllocationGroupDao.getAllocationGroup(jobGroupId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void updateAllocationGroup(ZnhyAllocationGroup group) {
		znhyAllocationGroupDao.updateAllocationGroup(group);

	}

	@Override
	public List<ZnhyAllocationGroup> queryAllocationGroup(ZnhyAllocationGroup allocationGroup) {
		List<ZnhyAllocationGroup> list = new ArrayList<ZnhyAllocationGroup>();
		List<ZnhyAllocationGroup> temp = znhyAllocationGroupDao.queryAllocationGroup(allocationGroup);
		return (temp == null) ? list : temp;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void createAllocationRule(List<ZnhyAllocationRule> ruleList) {
		znhyAllocationRuleDao.saveZnhyAllocationRule(ruleList);

	}

	@Override
	public List<ZnhyAllocationRule> queryAllocationRuleByGroupId(int jobGroupId) {
		return znhyAllocationRuleDao.queryAllocationRuleByGroupId(jobGroupId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void updateAllocationRule(List<ZnhyAllocationRule> ruleList) {
		znhyAllocationRuleDao.updateAllocationRule(ruleList);

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void createAllocation(ZnhyAllocation allocation) {
		znhyAllocationDao.saveZnhyAllocation(allocation);
		this.rabbitTemplate.convertAndSend("znhyAllocationCreate"+Constants.getTestQueueFlag(), allocation);
		log.info("znhyAllocationCreate send success----------------------"+"znhyAllocationCreate"+Constants.getTestQueueFlag());
	}
	
	@Override
	public ZnhyAllocation queryZnhyAllocation(ZnhyAllocation znhyAllocation) {
		return znhyAllocationDao.queryZnhyAllocation(znhyAllocation);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void updateAllocation(ZnhyAllocation allocation) {
		znhyAllocationDao.updateZnhyAllocation(allocation);
		this.rabbitTemplate.convertAndSend("znhyAllocationUpdate"+Constants.getTestQueueFlag(), allocation);
		log.info("znhyAllocationUpdate send success----------------------"+"znhyAllocationUpdate"+Constants.getTestQueueFlag());
	}

	@Override
	public void allocateOrder(String orderId) {
		this.rabbitTemplate.convertAndSend("znhyOrderAllocate"+Constants.getTestQueueFlag(), orderId);
		log.info("znhyOrderAllocate send success----------------------"+"znhyOrderAllocate"+Constants.getTestQueueFlag());
	}

	
	@Override
	public PagingData<ZnhyAccount> selectPage(PagingEntity pageEnt, ZnhyAccount account) {
		PagingData<ZnhyAccount> pagingData = new PagingData<ZnhyAccount>();

		int pageNum = pageEnt.getPageNum();
		int pageSize = pageEnt.getPageSize();
		Page<ZnhyAccount> page = PageHelper.startPage(pageNum, pageSize);

		List<ZnhyAccount> users = queryZnhyAccount(account);

		long total = page.getTotal(); // 总条数

		pagingData.setRows(users);
		pagingData.setTotal(total);
		return pagingData;
	}

	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void createGroupAndRule(ZnhyAllocationGroup group,  List<ZnhyAllocationRule> ruleList) {

	    createAllocationGroup(group);
	    Integer jobGroupId = group.getJobGroupId();
	    
	    for (ZnhyAllocationRule rule : ruleList) {
	    	rule.setJobGroupId(jobGroupId);
	    	
	    	double radios = rule.getRadios();
	    	BigDecimal bd = new BigDecimal(radios);
	    	BigDecimal bdRadis = bd.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
	    	radios = bdRadis.doubleValue();
	    	rule.setRadios(radios);
	    }
	    
	    createAllocationRule(ruleList);
	}

	@Override
	public PagingData<ZnhyAllocationGroup> selectAllocationGroupPage(PagingEntity pageEnt, ZnhyAllocationGroup group) {
		PagingData<ZnhyAllocationGroup> pagingData = new PagingData<ZnhyAllocationGroup>();

		int pageNum = pageEnt.getPageNum();
		int pageSize = pageEnt.getPageSize();
		Page<ZnhyAllocationGroup> page = PageHelper.startPage(pageNum, pageSize);

		List<ZnhyAllocationGroup> resultList = queryAllocationGroup(group);

		long total = page.getTotal(); // 总条数

		pagingData.setRows(resultList);
		pagingData.setTotal(total);
		return pagingData;
	}

	@Override
	public PagingData<ZnhyAllocationVo> selectZnhyAllocationVoPage(PagingEntity pageEnt, ZnhyAllocation allocation) {
		
		PagingData<ZnhyAllocationVo> pagingData = new PagingData<ZnhyAllocationVo>();

		int pageNum = pageEnt.getPageNum();
		int pageSize = pageEnt.getPageSize();
		Page<ZnhyAllocationVo> page = PageHelper.startPage(pageNum, pageSize);

		List<ZnhyAllocationVo> resultList = queryZnhyAllocationVo(allocation);

		long total = page.getTotal(); // 总条数

		pagingData.setRows(resultList);
		pagingData.setTotal(total);
		return pagingData;
	}
	
	public List<ZnhyAllocationVo> queryZnhyAllocationVo(ZnhyAllocation allocation) {
		
		List<ZnhyAllocationVo> list = new ArrayList<ZnhyAllocationVo>();
				
		List<ZnhyAllocationVo> temp = znhyAllocationDao.queryZnhyAllocationVo(allocation);
		
		return temp == null ? list : temp;
	}
	
	@Override
	public ZnhyAccount getZnhyAccountByAccountId(String accountId) {
		return znhyAccountDao.getZnhyAccountByAccountId(accountId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void updateGroupAndRule(ZnhyAllocationGroup group, List<ZnhyAllocationRule> ruleList) {
		
		updateAllocationGroup(group);
		
		for (ZnhyAllocationRule rule : ruleList) {
			
			double radios = rule.getRadios();
	    	BigDecimal bd = new BigDecimal(radios);
	    	BigDecimal bdRadis = bd.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
	    	radios = bdRadis.doubleValue();
	    	rule.setRadios(radios);
	    	
			rule.setJobGroupId(group.getJobGroupId());
		}
		updateAllocationRule(ruleList);
	}

	@Override
	public List<Map<String, Object>> getZnhyAccountByGroupId(long groupId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> temp = znhyAllocationGroupDao.getZnhyAccountByGroupId(groupId);
		
		return temp == null ? list : temp;
	}

	
	
	

}
