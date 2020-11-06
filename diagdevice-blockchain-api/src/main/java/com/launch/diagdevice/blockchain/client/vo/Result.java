package com.launch.diagdevice.blockchain.client.vo;

import lombok.Data;

@Data
public class Result {
	/**
	 * 状态码，0：成功
	 */
	private int code;
	/**
	 * 错误信息，”success”为成功
	 */
	private String message;
	/**
	 * 区块连调用的交易hash
	 */
	private String transaction_id;
}
