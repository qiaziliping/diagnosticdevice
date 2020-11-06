package com.launch.diagdevice.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.launch.diagdevice.common.constant.PayConstants;
import com.launch.diagdevice.dao.RechargeRecordDao;
import com.launch.diagdevice.dao.UserExtDao;
import com.launch.diagdevice.entity.RechargeRecord;
import com.launch.diagdevice.entity.UserExt;
import com.launch.diagdevice.service.UserExtService;

@Service(interfaceClass = UserExtService.class)
@Transactional(readOnly = true)
public class UserExtServiceImpl implements UserExtService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserExtDao userExtDao;

	@Autowired
	private RechargeRecordDao rechargeRecordDao;
	
	@Override
	public UserExt selectById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserExt selectOne(UserExt model) {
		return userExtDao.selectOne(model);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer save(UserExt e) {
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer update(UserExt e) {
		return userExtDao.update(e);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer delete(UserExt e) {
		return null;
	}

	@Override
	public List<UserExt> selectByIndex(UserExt model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void batchSave(Collection<UserExt> entities) {
		// TODO Auto-generated method stub

	}

	
	/**
	 * 废弃原因：目前APP没有预充值，直接扫描支付使用设备
	 */
	@Deprecated
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer updateMoneyByRechargeRecord(RechargeRecord rRecord,Map<String, String> rrMap) {
		Integer count = 0;
		// 订单号
		String orderNo = rRecord.getOrderNo();
		// 是否支付
		int isPay = rRecord.getIsPay();

		if (rRecord == null || StringUtils.isEmpty(orderNo)) {
			logger.warn("-----userextServiceImpl.updateMoneyByOrderNo but orderNo is null");
			return count;
		}

		// 如果支付成功先保存充值记录，再修改账户金额
		if (PayConstants.IS_PAY_YES == isPay) {
			
			Long userId = Long.parseLong(rrMap.get("userId"));
			// 实际充值金额
			BigDecimal realTotalMoney = new BigDecimal(rrMap.get("realTotalMoney"));
			
			rRecord.setUserId(userId);
			rRecord.setOrderNo(orderNo);
			// 支付回调成功之后再修改账户余额
			rRecord.setRealTotalMoney(realTotalMoney);
			rRecord.setPayFrom(PayConstants.PAY_FROM_ALIPAY);
			rRecord.setPayType(PayConstants.PAY_TYPE_ONLINE);
			rRecord.setIsPay(isPay);
			
			rechargeRecordDao.save(rRecord);
			
			// 查询目前的账户余额
			UserExt ext = new UserExt();
			ext.setUserId(userId);
			ext = this.selectOne(ext);
			// 账户余额加上充值金额
			BigDecimal userMoney = ext.getUserMoney();
			ext.setUserMoney(userMoney.add(realTotalMoney));
			count = this.update(ext);
		}
		return count;
	}

}
