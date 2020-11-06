package com.launch.diagdevice.entity;

import com.launch.diagdevice.common.model.PagingEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends PagingEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4563823179063853639L;

	/** 用户名 */
	private String username;
	/** 密码 */
	private String password;
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

}
