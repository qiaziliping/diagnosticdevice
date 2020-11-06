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
public class SignVo implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long loginId;
	
	private String token;


}
