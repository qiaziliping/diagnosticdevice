package com.launch.diagdevice.system.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserLoginInfo implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String cc;
	private String token;
	private String serialNo;
	private Integer userId;
	private Integer pdtTypeId;
	private Integer softConfId;
	private Integer pdtState;
	private Integer venderId;
	private Integer filialeId;
}
