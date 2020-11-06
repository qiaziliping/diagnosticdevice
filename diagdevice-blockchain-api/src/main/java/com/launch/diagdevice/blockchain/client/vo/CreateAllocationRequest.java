package com.launch.diagdevice.blockchain.client.vo;

import java.util.List;

import lombok.Data;

/**
 * creator必须包含在分配列表中的账户中，
 * 
 * @author ouxiangrui
 *
 */
@Data
public class CreateAllocationRequest {
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
	 * 分配表id
	 */
	private String app_id;
	/**
	 * 区块链唯DAO组织ID
	 */
	private String dao_id;
	/**
	 * 创建分配表的账户ID
	 */
	private String creator;
//	/**
//	 * 数字资产类型 分配表类型 ，如设置为：诊断设备(zdsb)
//	 */
//	private int assets_type;
	/**
	 * 数字资产名字 ，如为序列号（987542156324）
	 */
	private String name;
//	/**
//	 * 待注册数字资产的备注信息
//	 */
//	private String desc;
	/**
	 * 分配表详情列表
	 */
	private List<AllocationDetail> assigners;
}
