package com.launch.diagdevice.common.util;

public class ParameteToken extends BaseObject {

	/**
	 * 用户CC号
	 */
	private Integer user_id;
	/**
	 * 用户类型1：手机用户；2：平板用户
	 */
	private String app_id = "1";
	
	private String sign;

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public ParameteToken(Integer user_id) {
		super();
		this.user_id = user_id;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public ParameteToken() {
		// TODO 自动生成的构造函数存根
	}

//	public static void main(String args[]) throws Exception {
//		ParameteToken p = new ParameteToken(10007);
//		// p.user_id = 10007;
//		// p.app_id = "1";
//		String result = RemoteCallTool.getToken(p);
//		System.out.println(result);
//	}

}
