package com.launch.diagdevice.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

/**
 * 用户和用户扩展表VO对象
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月10日
 */
@Data
public class UserAndExtVo implements Serializable {

	
	private Long id;
	/** 用户名 */
	private String username;
	/** 密码 */
	//private String password;
	/** 昵称 */
	private String nickName;
	/** 性别：0未知，1男，2女 */
	private String sex;
	/** 邮箱 */
	private String email;
	/** 电话号码（手机） */
	private String mobile;
	/** 头像地址 */
	private String imagePathUrl;
	/** 用户来源: 0:邮箱或手机注册，1:微信，2:QQ */
	private String source;
	/** OPENID */
	private String openId;
	/** UNIONID */
	private String unionId;
	
	/** 用户钱包金额 */
	private BigDecimal userMoney;
	/** 冻结金额 */
	private BigDecimal lockMoney;
	/** 最后登录时间 */
	private String lastTime;
	/** 账号是否被锁： 0否，1是 */
	private Integer isLock;
	
	/** 注册时间 */
	private String createTime;
	
	
	public void setLastTime(Object objectTime) {
		if (objectTime instanceof Date) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.lastTime = format.format(objectTime);
		} else {
			this.lastTime = String.valueOf(objectTime);
		}
	}
	
	public void setCreateTime(Object objectTime) {
		if (objectTime instanceof Date) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.createTime = format.format(objectTime);
		} else {
			this.createTime = String.valueOf(objectTime);
		}
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
