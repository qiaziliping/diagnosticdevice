package com.launch.diagdevice.pay.service;

import java.math.BigDecimal;

import com.launch.diagdevice.pay.common.constant.PaypalPaymentIntent;
import com.launch.diagdevice.pay.common.constant.PaypalPaymentMethod;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

/**
 * paypal的Service接口
 * @version 0.0.1
 * @since 2018年11月27日
 */
public interface PaypalService {
	
	/**
     * 创建支付
     * @param softName 诊断软件名称
     * @param total 金额
     * @param currency 币种
     * @param method 支付方式
     * @param intent 支付目的
     * @param description 订单描述
     * @param cancelUrl 取消的URL
     * @param successUrl 支付成功的URL
     * @return
     * @throws PayPalRESTException
     */
	Payment createPayment(String softName,BigDecimal total, String currency, PaypalPaymentMethod method, PaypalPaymentIntent intent, 
	            String cancelUrl, String successUrl) throws PayPalRESTException;
	 
	 
	/**
     * 执行支付
     * paymentId 支付ID
     * payerId 付款人ID
     */
	Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;



}
