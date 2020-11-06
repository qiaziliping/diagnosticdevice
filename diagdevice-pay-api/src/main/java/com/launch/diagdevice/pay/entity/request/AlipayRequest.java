package com.launch.diagdevice.pay.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;


@Data
public class AlipayRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long userId;
	private BigDecimal price;
	

}
