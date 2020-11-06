package com.launch.diagdevice.blockchain.client.vo;

import lombok.Data;

/**
 * user_name、org_name、name必填，其它选填
 * 
 * @author ouxiangrui
 *
 */
@Data
public class CreateDAORequest {
	/**
	 * 注册CA的用户名
	 */
	private String user_name;
	/**
	 * 注册CA的组织名
	 */
	private String org_name;
	/**
	 * 注册CA的密码
	 */
	private String secret;
	/**
	 * 待注册DAO的名字
	 */
	private String name;
	/**
	 * 待注册DAO的备注信息
	 */
	private String desc;

}
