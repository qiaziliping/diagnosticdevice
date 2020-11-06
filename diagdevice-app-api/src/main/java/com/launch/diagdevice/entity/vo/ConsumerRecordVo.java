package com.launch.diagdevice.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

/**
 * 消费记录VO
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月10日
 */
@Data
public class ConsumerRecordVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8067755644056805846L;
	
	public ConsumerRecordVo() {
	}


	/** 订单ID */
	private Long orderId;
	/** 车型软件名称 */
	private String softName;
	/** VIN码(诊断时获取车的唯一标识) */
	private String vinCode;
	
	/** 诊断开始时间 */
	private String diagStartTime;
	/** 诊断结束时间 */
	private String diagEndTime;

	private String createTime;
	
	private BigDecimal price;
	
	private String username;
	
	private String orderNo;
	/** 币种 */
	private String currency;
	
	
	public void setCreateTime(Object objectTime) {
		if (objectTime instanceof Date) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.createTime = format.format(objectTime);
		} else {
			this.createTime = String.valueOf(objectTime);
		}
	}
	public void setDiagStartTime(Object objectTime) {
		if (objectTime instanceof Date) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.diagStartTime = format.format(objectTime);
		} else {
			this.diagStartTime = String.valueOf(objectTime);
		}
	}
	public void setDiagEndTime(Object objectTime) {
		if (objectTime instanceof Date) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.diagEndTime = format.format(objectTime);
		} else {
			this.diagEndTime = String.valueOf(objectTime);
		}
	}
	
}
