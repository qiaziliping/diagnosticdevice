package com.launch.diagdevice.blockchain.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.blockchain.dao.ZnhyAccountDao;
import com.launch.diagdevice.blockchain.dao.mapper.ZnhyAccountMapper;
import com.launch.diagdevice.blockchain.model.ZnhyAccount;
import com.launch.diagdevice.blockchain.vo.ZnhyAccountVo;
@Component
public class ZnhyAccountDaoImpl implements ZnhyAccountDao {

	@Autowired
	private ZnhyAccountMapper znhyAccountMapper;

	@Override
	public void saveZnhyAccount(ZnhyAccount za) {
		znhyAccountMapper.saveZnhyAccount(za);
	}

	@Override
	public void updateZnhyAccount(ZnhyAccount za) {
		znhyAccountMapper.updateZnhyAccount(za);

	}

	@Override
	public ZnhyAccount getZnhyAccount(int accountId) {
		return znhyAccountMapper.getZnhyAccount(accountId);
	}

	@Override
	public List<ZnhyAccount> queryZnhyAccount(ZnhyAccount za) {
		return znhyAccountMapper.queryZnhyAccount(za);
	}

	@Override
	public ZnhyAccount getZnhyAccountByAccountId(String accountId) {
		return znhyAccountMapper.getZnhyAccountByaccountId(accountId);
	}

	@Override
	public void updateAccountNullByAccountTypeOld(ZnhyAccountVo za) {
		znhyAccountMapper.updateAccountNullByAccountTypeOld(za);
	}

}
