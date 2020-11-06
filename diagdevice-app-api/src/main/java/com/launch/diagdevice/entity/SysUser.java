package com.launch.diagdevice.entity;

import java.util.Date;

import com.launch.diagdevice.common.model.PagingEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 后台用户实体类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser extends PagingEntity {

	
	
	/** 用户名 */
	private String username;
	/** 密码 */
	private String password;
	/** 昵称 */
	private String nickname;
	/** 性别：0女，1男 */
	private Integer sex;
	/** 是否激活：0否，1是 */
	private Integer isActive;
	/** 手机号码 */
	private String mobile;
	/** 邮箱 */
	private String email;
	/** 排序  */
	private Integer sort;
	/** 最后登录时间 */
	private Date lastLoginTime;
	/** 头像地址  */
	private String imagePathUrl;
	
	
	/*public Integer getSex(){
		if (sex == null) sex = 0;
		return sex;
	}*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
