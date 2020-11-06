package com.launch.diagdevice.blockchain.client.vo;

import java.util.List;

import lombok.Data;

/**
 * 添加订单请求参数
 * 
 * @author ouxiangrui
 *
 */
@Data
public class AllocateOrderRequest {
	/**
	 * 是 第三方平台的流水号，例如支付宝支付：支付宝平台的订单号
	 */
	private String ThirdTradeNo;
	/**
	 * 是 商家自己的订单号
	 */
	private String OrderNo;
	/**
	 * 否 分配编号，首先会去订单明细中取SubNumber，如果订单明细中没有设置
	 * SubNumber，那么会取该SubNumber；支持在一张订单中，为订单明细设置不同的 SubNumber，即支持按明细进行分账
	 */
	private String SubNumber;
	/**
	 * 是 订单时间，格式为:yyyy-MM-dd HH:mm:ss
	 */
	private String OrderTime;
	/**
	 * 是 公司名称
	 */
	private String Company;
	/**
	 * 是 分店编号
	 */
	private String BranchShop;
	/**
	 * 是 支付方式,取值如下： 支付宝、微信
	 */
	private String PayWay;
	/**
	 * 是 总价
	 */
	private Double SumPrice;
	/**
	 * 是 折扣金额
	 */
	private Double Discount;
	/**
	 * 是 实际支付价格
	 */
	private Double Price;
	/**
	 * 是 订单状态，1正常订单，2退款订单
	 */
	private Integer OrderState;
	/**
	 * 否 销售员
	 */
	private String Salesmen;
	/**
	 * 否 备注，长度限制200字符
	 */
	private String Remarks;
	/**
	 * 是 订单明细
	 */
	private List<OrderDetail> OrderDetails;

}
