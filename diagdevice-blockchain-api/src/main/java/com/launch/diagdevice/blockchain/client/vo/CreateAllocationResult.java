package com.launch.diagdevice.blockchain.client.vo;

import lombok.Data;

@Data
public class CreateAllocationResult extends Result {
	/**
	 * 分配表链上ID
	 */
	private String allocation_id;
}
