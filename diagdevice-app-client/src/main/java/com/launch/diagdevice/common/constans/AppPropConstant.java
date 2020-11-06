package com.launch.diagdevice.common.constans;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * APP属性常量类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月14日
 */
@Component
public class AppPropConstant {

	/** 车辆诊断价格 */
	public static BigDecimal CAR_PRICE;


	@Value(value="${app.car.price}")
	public void setCar_price(BigDecimal CAR_PRICE) {
		AppPropConstant.CAR_PRICE = CAR_PRICE;
	}
	
	
}
