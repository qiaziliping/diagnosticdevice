package com.launch.diagdevice.entity.request;

import java.math.BigDecimal;
import java.util.Date;

import com.launch.diagdevice.common.model.request.RequestPaging;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消费记录Request
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月10日
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ConsumerRecordRequest extends RequestPaging {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConsumerRecordRequest() {
	}


	/** 用户ID */
	private Long userId;
	/** 消费编号 */
	private String orderNo;
	/** 车型软件名称 */
	private String softName;
	/** VIN码(诊断时获取车的唯一标识) */
	private String vinCode;
	/** 诊断价格 */
	private BigDecimal price;

	/** 支付来源:1:支付宝，2：微信 */
	private Integer payFrom;
	/** 支付时间 */
	private Date payTime;
	/** 设备序列号 */
	private String serialNo;
	
	
	/** 后台获取记录列表需要根据用户查询 */
	private String username;
	
	
}
