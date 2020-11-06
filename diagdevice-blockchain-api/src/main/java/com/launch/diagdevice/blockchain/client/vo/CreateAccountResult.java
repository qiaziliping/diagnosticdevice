package com.launch.diagdevice.blockchain.client.vo;

import lombok.Data;

/**
 * 组织对象
 * 
 * @author ouxiangrui
 *
 */
@Data
public class CreateAccountResult extends Result {
	/**
	 * 区块链账户ID（钱包地址）
	 */
	private String no;
	
	private String uuid;
}
