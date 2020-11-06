package com.launch.diagdevice.entity.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户VO对象
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月10日
 */
@Data
public class UserVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5049865931722898934L;
	// @JsonIgnore  对象转json时忽略该注解字段
	private Long id;
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
	
	private Long userId;
	/** app传过来的时间，用到加密的 */
	private String dateTime;
	
	/** 验证码 */
	private String verCode;
	private String uuid;


}
