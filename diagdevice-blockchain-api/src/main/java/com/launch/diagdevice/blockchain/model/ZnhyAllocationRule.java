package com.launch.diagdevice.blockchain.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 数据表model对象，角色分配比例管理，同一组内分配比例需等于1
 * 
 * @author ouxiangrui
 *
 */
@Data
public class ZnhyAllocationRule implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 分配角色组id
	 */
	private Integer jobGroupId;
	/**
	 * 职位、角色
	 */
	private String job;
	/**
	 * 分配比例，加起来需等于1
	 */
	private double radios;
	/**
	 * 钱包地址id
	 */
	private String accountId;

}
