package com.launch.diagdevice.system.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class CcUserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1529636839082449205L;
	private String cc;
	private String country;
	private String province;
	private String city;
	private String zipcode;
	private String address;
	private String mobile;
	private String email;
	private String reg_ip;
	private String login_ip;
	private String family_phone;
	private String office_phone;
	private String company_name;
	private String sex;
	private String create_time;
	private String user_name;
	private String nick_name;
	private String fax;

}
