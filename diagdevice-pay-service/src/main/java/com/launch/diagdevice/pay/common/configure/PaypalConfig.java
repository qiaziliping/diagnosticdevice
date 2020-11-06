package com.launch.diagdevice.pay.common.configure;

/**
 * paypal配置类
 * 主要加载paypay的API上下文
 * TODO
 * <p>
 * @author liping
 * @version 0.0.1
 * @since 2018年11月27日
 * 
 * useless_delete
 */
//@Configuration
public class PaypalConfig {
	
//    private String clientId = "AZX9-6wIurTrCFJucGMGC7hqzg6hAZyKlcWUKKeKy9hZL1ouR8Wx2sVyQpojLg7WMuvrC5f4o8o_C9xM";
//    private String clientSecret = "EOZoyOhKu64iyKE4XF76UcGfyLjAE7M7con2kv2Iz8-8FF8L4Ghg9ViGtGi1dCd4S5rJNpQVacEWrsHN";
//    private String mode = "sandbox";
	

    /*@Bean
    public Map<String, String> paypalSdkConfig(){
        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", PayConstant.PAYPAL_MODE);
        return sdkConfig;
    }*/

   /* @Bean
    public OAuthTokenCredential authTokenCredential(){
        return new OAuthTokenCredential(PayConstant.PAYPAL_CLIENT_ID, PayConstant.PAYPAL_CLIENT_SECRET, paypalSdkConfig());
    }*/

    /*@Bean
    public APIContext apiContext() throws PayPalRESTException{
    	String accessToken = authTokenCredential().getAccessToken();
        APIContext apiContext = new APIContext(accessToken);
        return apiContext;
    }*/
}
