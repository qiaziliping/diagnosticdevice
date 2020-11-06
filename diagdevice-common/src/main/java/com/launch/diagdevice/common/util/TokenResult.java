package com.launch.diagdevice.common.util;

import lombok.Data;

@Data
public class TokenResult {

	private Integer code;
	private String msg;
	private String data;
	private String trace;
}
