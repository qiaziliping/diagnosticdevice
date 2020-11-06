package com.launch.diagdevice.blockchain.dao;

import java.util.List;

import com.launch.diagdevice.blockchain.model.ZnhyAccount;
import com.launch.diagdevice.blockchain.vo.ZnhyAccountVo;

public interface ZnhyAccountDao {
	/**
	 * 保存智能合约账号信息
	 * 
	 * @param za
	 */
	void saveZnhyAccount(ZnhyAccount za);

	/**
	 * 更新智能合约账号
	 * 
	 * @param za
	 */
	void updateZnhyAccount(ZnhyAccount za);

	/**
	 * 查询智能合约账号信息
	 * 
	 * @param za
	 * @return
	 */
	ZnhyAccount getZnhyAccount(int id);

	/**
	 * 条件查询智能合约账号列表
	 * 
	 * @param za
	 * @return
	 */
	List<ZnhyAccount> queryZnhyAccount(ZnhyAccount za);
	
	/**
	 * 根据accountId查询账户信息
	 * LIPING
	 */
	ZnhyAccount getZnhyAccountByAccountId(String accountId);

	void updateAccountNullByAccountTypeOld(ZnhyAccountVo za);
}
