package com.launch.diagdevice.blockchain.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 定义一个分配组，方便后台增删改查，建立分配规则
 * 
 * @author ouxiangrui
 *
 */
@Data
public class ZnhyAllocationGroup implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 分配组id
	 */
	private Integer jobGroupId;
	/**
	 * 分配组名
	 */
	private String groupName;
	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新人
	 */
	private String updator;

	/**
	 * 更新时间
	 */
	private Date updateDate;
}
