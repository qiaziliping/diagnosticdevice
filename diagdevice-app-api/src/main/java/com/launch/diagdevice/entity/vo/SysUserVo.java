package com.launch.diagdevice.entity.vo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 后台用户VO
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月11日
 */
@Data
public class SysUserVo implements Serializable {

	
	private Long id;
	
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
	
	private List<SysUserRoleVo> urVolist;
	
	private String remark;
	private Integer status;
	private String createTime;
	private String updateTime;
	private String createUserId;
	private String updateUserId;
	
	public void setId(Object id) {
		if (null != id) {
			if (id instanceof String) {
				this.id = Long.parseLong(id.toString());
			} else if (id instanceof Long) {
				this.id = Long.parseLong(id.toString());
			}
		}
	}
	
	public void setCreateTime(Object objectTime) {
		if (null != objectTime) {
			if (objectTime instanceof Date) {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				this.createTime = format.format(objectTime);
			} else {
				this.createTime = String.valueOf(objectTime);
			}
		}
	}
	
	public void setUpdateTime(Object objectTime) {
		if (null != objectTime) {
			if (objectTime instanceof Date) {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				this.updateTime = format.format(objectTime);
			} else {
				this.updateTime = String.valueOf(objectTime);
			}
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
