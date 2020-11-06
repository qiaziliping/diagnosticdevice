package com.launch.diagdevice.system.vo;

import lombok.Data;

@Data
public class CcUserResult extends BaseObject {
	private String code;
	private String msg;
	private String data;

	@Override
	public String toString() {
		return super.toString();
	}
}
