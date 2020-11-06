package com.launch.diagdevice.pay.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.launch.diagdevice.pay.common.constant.PaypalPaymentIntent;
import com.launch.diagdevice.pay.common.constant.PaypalPaymentMethod;
import com.launch.diagdevice.pay.common.constants.PayConstant;
import com.launch.diagdevice.pay.service.PaypalService;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
/**
 * 支付service类
 * 
 * Payment 对象信息如下json对象
 * {
	"intent": "sale",
	"payer": {
		"payment_method": "paypal"
	},
	"transactions": [{
		"amount": {
			"total": "30.11",
			"currency": "USD",
			"details": {
				"subtotal": "30.00",
				"tax": "0.07",
				"shipping": "0.03",
				"handling_fee": "1.00",
				"shipping_discount": "-1.00",
				"insurance": "0.01"
			}
		},
		"description": "The payment transaction description.",
		"custom": "EBAY_EMS_90048630024435",
		"invoice_number": "48787589673",
		"payment_options": {
			"allowed_payment_method": "INSTANT_FUNDING_SOURCE"
		},
		"soft_descriptor": "ECHI5786786",
		"item_list": {
			"items": [{
					"name": "hat",
					"description": "Brown hat.",
					"quantity": "5", // 数量
					"price": "3",
					"tax": "0.01",   // 税
					"sku": "1",
					"currency": "USD"
				},
				{
					"name": "handbag",
					"description": "Black handbag.",
					"quantity": "1",
					"price": "15",
					"tax": "0.02",
					"sku": "product34",
					"currency": "USD"
				}
			],
			"shipping_address": {
				"recipient_name": "Brian Robinson",
				"line1": "4th Floor",
				"line2": "Unit #34",
				"city": "San Jose",
				"country_code": "US",
				"postal_code": "95131",
				"phone": "011862212345678",
				"state": "CA"
			}
		}
	}],
	"note_to_payer": "Contact us for any questions on your order.",
	"redirect_urls": {
		"return_url": "https://example.com/return",
		"cancel_url": "https://example.com/cancel"
	}
}
 * @author liping
 *
 */
@Service(interfaceClass = PaypalService.class)
@Component
public class PaypalServiceImpl implements PaypalService {

//	@Autowired
//	private APIContext apiContext;

	/**
	 * 创建支付
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
	@Override
	public Payment createPayment(String softName,BigDecimal total, String currency, PaypalPaymentMethod method, PaypalPaymentIntent intent,
			String cancelUrl, String successUrl) throws PayPalRESTException {

		/**
		 * payment 一级对象
		 */
		Payment payment = new Payment();
		
		/**
		 * transactions 二级对象
		 */
		List<Transaction> transactions = new ArrayList<Transaction>();
		/**
		 * payer,支付信息  二级对象
		 */
		Payer payer = new Payer();
		payer.setPaymentMethod(method.toString());
		/**
		 * RedirectUrls 二级对象 回调地址
		 */
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		
		/**
		 *  transaction 三级对象(属于transactions)
		 */
		Transaction transaction = new Transaction();
		
		/**
		 *  Amount 四级对象（属于transaction）
		 */
		Amount amount = new Amount();
		amount.setCurrency(currency);
		// 支付的总价，paypal会校验 total = subTotal + tax + ..
		String price = String.format("%.2f", total);
		amount.setTotal(price);
		
		/**
		 *  itemList 四级对象 (商品明细，paypal支付页面需要显示的)
		 */
		ItemList itemList = new ItemList();
		List<Item> items = new ArrayList<Item>();
		Item item = new Item();
		item.setName("software-name");  // 就填诊断软件名称
		item.setCurrency(currency);
		item.setPrice(price);
		item.setDescription(softName);  // 物品描述
		item.setQuantity("1"); // item对象中，必填
		items.add(item);
		itemList.setItems(items);
				
		transaction.setDescription(PayConstant.PAYPAL_DESCRIPTION);
		transaction.setAmount(amount);
		transaction.setItemList(itemList);
		// transaction.setCustom(orderId);
		
		transactions.add(transaction);
		
		payment.setIntent(intent.toString());
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		payment.setRedirectUrls(redirectUrls);

		// 创建paypal API对象
        APIContext apiContext = getApiContext();
        //	APIContext apiContext = new APIContext(PayConstant.PAYPAL_CLIENT_ID,PayConstant.PAYPAL_CLIENT_SECRET,PayConstant.PAYPAL_MODE);
        return payment.create(apiContext);
	}

	/**
	 * 执行支付
	 * paymentId 支付ID
	 * payerId 付款人ID
	 */
	@Override
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		// 创建paypal API对象
        APIContext apiContext = getApiContext();
//		APIContext apiContext = new APIContext(PayConstant.PAYPAL_CLIENT_ID,
//    			PayConstant.PAYPAL_CLIENT_SECRET,PayConstant.PAYPAL_MODE);
		
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecute);
	}
	
	
	private APIContext getApiContext() throws PayPalRESTException {
		
		String accessToken = authTokenCredential().getAccessToken();
        APIContext apiContext = new APIContext(accessToken);
        apiContext.setConfigurationMap(paypalSdkConfig());
        return apiContext;
	}
	
	public Map<String, String> paypalSdkConfig() {
        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", PayConstant.PAYPAL_MODE);
        return sdkConfig;
    }

    public OAuthTokenCredential authTokenCredential(){
        return new OAuthTokenCredential(PayConstant.PAYPAL_CLIENT_ID, PayConstant.PAYPAL_CLIENT_SECRET, paypalSdkConfig());
    }
    
	/*@Override
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		MyPayment payment = new MyPayment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		
		payment = payment.execute(apiContext, paymentExecute);
		return payment;
	}*/
}
