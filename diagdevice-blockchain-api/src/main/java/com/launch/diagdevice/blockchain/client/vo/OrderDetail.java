package com.launch.diagdevice.blockchain.client.vo;

import lombok.Data;

@Data
public class OrderDetail {
	/**
	 * 是 产品编号
	 */
	private String ProductCode;
	/**
	 * 是 产品名称
	 */
	private String ProductName;
	/**
	 * 否 原价
	 */
	private double OriginPrice;
	/**
	 * 是 实际销售价格
	 */
	private double Price;
	/**
	 * 是 数量
	 */
	private double Count;
	/**
	 * 是 小计
	 */
	private double Subtotal;
	/**
	 * 是 单位
	 */
	private String Unit;
	/**
	 * 否 该明细项的SubNumber
	 */
	private String SubNumber;
}
