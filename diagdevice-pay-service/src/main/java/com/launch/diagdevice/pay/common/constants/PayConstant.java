package com.launch.diagdevice.pay.common.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PayConstant {

	//------------------------------paypal-----------------------------------
		/** paypal商家ID */
		public static String PAYPAL_CLIENT_ID;
		/** paypal商家密钥 */
		public static String PAYPAL_CLIENT_SECRET;
		/** 支付模式：sandbox（沙箱），live（正式环境） */
		public static String PAYPAL_MODE;
		
		/** 支付描述 */
		public static String PAYPAL_DESCRIPTION;
		
		
		
		
		@Value("${paypal.client.id}")
		public void setPAYPAL_CLIENT_ID(String pAYPAL_CLIENT_ID) {
			PayConstant.PAYPAL_CLIENT_ID = pAYPAL_CLIENT_ID;
		}
		@Value("${paypal.client.secret}")
		public void setPAYPAL_CLIENT_SECRET(String pAYPAL_CLIENT_SECRET) {
			PayConstant.PAYPAL_CLIENT_SECRET = pAYPAL_CLIENT_SECRET;
		}
		@Value("${paypal.mode}")
		public void setPAYPAL_MODE(String pAYPAL_MODE) {
			PayConstant.PAYPAL_MODE = pAYPAL_MODE;
		}
		@Value("${paypal.description}")
		public void setPAYPAL_DESCRIPTION(String pAYPAL_DESCRIPTION) {
			PayConstant.PAYPAL_DESCRIPTION = pAYPAL_DESCRIPTION;
		}
		
	
}