package com.launch.diagdevice.blockchain.model;

import java.util.Date;

import lombok.Data;
@Data
public class ZnhyOrderAllocateFail {
	private int orderId;
	private String serialNo;
	private int code;
	private String message;
	private Date createTime;
	private int success;
}
