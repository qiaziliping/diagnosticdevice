package com.launch.diagdevice.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

/**
 * 用户订单VO
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月10日
 */
@Data
public class UserOrderVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8067755644056805846L;
	
	public UserOrderVo() {
	}


	private Long id;
	/** 用户ID */
	private Long userId;
	/** 设备序列号 */
	private String serialNo;
	/** 订单编号 */
	private String orderNo;
	/** 诊断价格 */
	private BigDecimal price;
	/** 支付来源:1:支付宝，2：微信 */
	private Integer payFrom;
	/** 支付时间 */
	private Date payTime;
	
	/** 第三方订单号 */
	private String thirdTradeNo;

	private Integer status;
	private String createTime;
	
	private String username;
	
	public void setCreateTime(Object objectTime) {
		if (objectTime instanceof Date) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.createTime = format.format(objectTime);
		} else {
			this.createTime = String.valueOf(objectTime);
		}
	}
	
}
