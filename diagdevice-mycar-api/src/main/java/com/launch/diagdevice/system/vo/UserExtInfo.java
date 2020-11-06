package com.launch.diagdevice.system.vo;

import java.util.Date;

import lombok.Data;
@Data
public class UserExtInfo {
	/**
	 * 用户CC号
	 */
	private Integer userId;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 名
	 */
	private String firstName;
	/**
	 * 姓
	 */
	private String lastName;
	/**
	 * 家庭电话
	 */
	private String familyPhone;
	/**
	 * 办公电话
	 */
	private String officePhone;
	/**
	 * 洲代号
	 */
	private Integer continent;
	/**
	 * 国家
	 */
	private Integer country;
	/**
	 * 省
	 */
	private Integer province;
	/**
	 * 市/县
	 */
	private Integer city;
	/**
	 * 详细地址
	 */
	private String address;
	/**
	 * 邮编
	 */
	private String zipCode;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 纬度
	 */
	private String latitude;
	/**
	 * 定位位置
	 */
	private String markAddress;
	/**
	 * 驾驶证号码
	 */
	private String drivingLicense;
	/**
	 * 驾驶证发放日期, 十位字符长度
	 */
	private String issueDate;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
}
