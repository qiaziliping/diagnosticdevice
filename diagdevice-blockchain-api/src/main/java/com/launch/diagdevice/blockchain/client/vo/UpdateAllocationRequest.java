package com.launch.diagdevice.blockchain.client.vo;

import java.util.List;

import lombok.Data;

@Data
public class UpdateAllocationRequest {
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
	 * 应用id
	 */
	private String app_id;
	/**
	 * 数字资产名字 分配表
	 */
	private String name;
	/**
	 * 组织id
	 */
	private String dao_id;
	/**
	 * 
	 */
	private String creator;
	/**
	 * 分配表ID
	 */
	private String allocation_id;
	/**
	 * 分配表详情列表
	 */
	private List assigners;
}
