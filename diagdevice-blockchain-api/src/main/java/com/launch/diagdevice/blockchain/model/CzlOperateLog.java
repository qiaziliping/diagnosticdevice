package com.launch.diagdevice.blockchain.model;

import java.util.Date;

import lombok.Data;
@Data
public class CzlOperateLog {
	private int id;
	/**
	 * 记录对应的值
	 */
	private String recordId;
	/**
	 * 记录对应的表名
	 */
	private String recordType;
	/**
	 * json原始数据
	 */
	private String jsonDate;
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	
}
