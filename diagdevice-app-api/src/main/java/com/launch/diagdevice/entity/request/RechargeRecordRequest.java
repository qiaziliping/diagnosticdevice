package com.launch.diagdevice.entity.request;

import java.math.BigDecimal;

import com.launch.diagdevice.common.model.request.RequestPaging;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 充值记录Request
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月10日
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RechargeRecordRequest extends RequestPaging {

	
	public RechargeRecordRequest() {
	}

	/** ID */
	private Long id;
	/** 用户ID */
	private Long userId;
	/** 订单号 yyyyMMddHHmmssxxxx */
	private String orderNo;
	/** 诊断金额（未打折金额） */
	private BigDecimal diagMoney;
	/** 优惠金额 */
	private BigDecimal discountsMoney;
	/** 实际付款金额：进行各种折扣之后的金额 */
	private BigDecimal realTotalMoney;
	/** 支付方式:0:货到付款 1:在线支付(暂时都是1) */
	private Integer payType;
	/** 支付来源:1:支付宝，2：微信 */
	private Integer payFrom;
	/** 是否支付:0:未支付 1:已支付 */
	private Integer isPay;
	
	private Integer status;
	private String remark;
	
	private String createTime;
	private String updateTime;

	/** 后台获取订单记录列表需要根据用户查询 */
	private String username;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
