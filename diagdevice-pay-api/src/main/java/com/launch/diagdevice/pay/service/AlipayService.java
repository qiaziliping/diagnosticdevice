package com.launch.diagdevice.pay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayResponse;
import com.launch.diagdevice.pay.entity.AlipayModel;

/**
 * 支付宝service接口
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月30日
 */
public interface AlipayService {

	/**
	 * 预支付获取二维码地址
	 * AlipayResponse成功getBody的结果
		 * {
				"alipay_trade_precreate_response": {
				"code": "10000",
				"msg": "Success",
				"out_trade_no": "201808072041589545",
				"qr_code": "https:\/\/qr.alipay.com\/bax02816xxtfg7rlfnn600b0"   //二维码地址
				},
				"sign": "dEb11OFM3nGhGrbsVxqNybtQTUCope1m7rVFUabsdV7dID/i/nNsyILJHMsPvBDu9471S+UwVMHzDYV4G/3etI1+6Ft1DrJj4YOLm2RLy75aevvKiC42cD0OGKN2QK6NLDcxUDFIakKyRExI0qdHsv12GyOOmXNkheN62LZLvE3vvPab1YNHmWlG2x7DanZ3TaNg3n3fAER/68unyHaoNpn+rJ3f2IRbbY4+LzsURoJIUIaF8agNipPAVlqLEfm9i9Kqg9Ar05HvFkso5grHc9UsBKst2tVfybQd/QydkOquW54OQcS2Umr3K/SPqubzsDmLJH19opaOoZhrti9fHg=="
		   }
	 * TODO
	 * liping
	 */
	AlipayResponse preparePay(AlipayModel aliModel) throws AlipayApiException;
	
	

}
