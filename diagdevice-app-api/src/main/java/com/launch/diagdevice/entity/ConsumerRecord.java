package com.launch.diagdevice.entity;

import java.util.Date;

import com.launch.diagdevice.common.model.PagingEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消费记录实体
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月10日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ConsumerRecord extends PagingEntity {

	public ConsumerRecord() {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2384700756451231286L;

	/** 订单ID */
	private Long orderId;
	/** 车型软件名称 */
	private String softName;
	/** VIN码(诊断时获取车的唯一标识) */
	private String vinCode;
	
	/** 诊断开始时间 */
	private Date diagStartTime;
	/** 诊断结束时间 */
	private Date diagEndTime;
	

}
