package com.launch.diagdevice.entity.request;

import java.io.Serializable;

import lombok.Data;


@Data
public class RequestHead implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户登录ID，就是用户ID
	 */
	private Long loginId;
	/**
	 * 校验用户在同一个地方登录
	 */
//	private String sessionId;
	

}
