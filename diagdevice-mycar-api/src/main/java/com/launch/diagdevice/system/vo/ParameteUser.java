package com.launch.diagdevice.system.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ParameteUser extends BaseObject {

	/**
	 * 用户CC号
	 */
	private Integer user_id;
	/**
	 * 用户类型1：手机用户；2：平板用户
	 */
	private Integer userTypeId;
	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 用户头像
	 */
	private String purikuraurl;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 性别 1:男0:女
	 */
	private Integer sex;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 区域ID
	 */
	private String districtId;
	/**
	 * 地区ID
	 */
	private String areaId;
	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;
	/**
	 * 密码更新时间
	 */
	private Date updatePwdTime;
	/**
	 * 是否绑定邮箱 0:否1:是
	 */
	private Integer isBindEmail;
	/**
	 * 是否绑定手机 0:否1:是
	 */
	private Integer isBindMobile;
	/**
	 * 是否设置密保 0:否1:是
	 */
	private Integer isSecurity;
	/**
	 * 用户状态 1:正常2:冻结
	 */
	private Integer userStatus;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

	@Override
	public String toString() {
		return super.toString();
	}

}
