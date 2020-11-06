package com.launch.diagdevice.blockchain.vo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

/**
 * 智能合约分配表（智能合约），即某一序列号的分配关系
 * 
 * @author ouxiangrui
 *
 */
@Data
public class ZnhyAllocationVo implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = -7924404385007927387L;
    /**
	 * 系统返回的分配表id
	 */
	private Integer id;
	/**
	 * 要应用的分配规则id
	 */
	private Integer jobGroupId;
	/**
	 * 要应用的分配规则组名称
	 */
	private String groupName;
	
	/**
	 * 创建者钱包地址id，由资产所有者创建对应智能合约，一般为设备所有者
	 */
	private String creatorId;
	/**
	 * 是znhy_account表中的name，编辑需要用到
	 */
	private String accountName;

	/**
	 * 智能合约数字资产分配名称，如序列号
	 */
	private String name;

	/**
	 * 资产类型，如zdsb（诊断设备）
	 */
	private Integer assetsType;
	/**
	 * 分配表链上ID
	 */
	private String allocationId;

	/**
	 * 后台生成时间
	 */
	private String createDate;
	
	/**
	 * 后台更新时间
	 */
	private String updateDate;
	/**
	 * 后台更新者
	 */
	private String updator;
	/**
	 * 后台创建者
	 */
	private String creator;
	
	
	public void setCreateDate(Object objectTime) {
		if (objectTime != null) {
			if (objectTime instanceof Date) {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				this.createDate = format.format(objectTime);
			} else {
				this.createDate = String.valueOf(objectTime);
			}
		}
	}
	
	public void setUpdateDate(Object objectTime) {
		if (objectTime != null) {
			if (objectTime instanceof Date) {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				this.updateDate = format.format(objectTime);
			} else {
				this.updateDate = String.valueOf(objectTime);
			}
		}
	}
}
