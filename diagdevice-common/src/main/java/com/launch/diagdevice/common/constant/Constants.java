package com.launch.diagdevice.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource("classpath:diagdevice.properties")
@Component
public class Constants {

	/**
	 * 响应成功
	 */
	public static final int SUCCESS = 0;
	/**
	 * 服务器内部错误
	 */
	public static final int ERROR = -1;
	/**
	 * 参数错误
	 */
	public static final int PARAMETER_ERROR = -2;

	/**
	 * 必填参数不能为空
	 */
	public static final int MUST_PARAMETER_ISNOTNULL = -3;
	
	/** 币种：人民币RMB */
	public static final String CURRENCY_RMB = "RMB";
	/** 币种：美元USD */
	public static final String CURRENCY_USD = "USD";
	/** 币种：欧元EUR */
	public static final String CURRENCY_EUR = "EUR";
	
	/** 1:支付宝账号 */
	public static final int ACCOUNT_NAME_ALIPAY = 1;
	/** 2:微信账号 */
	public static final int ACCOUNT_NAME_WECHAT = 2;
	/** 3:paypal账号 */
	public static final int ACCOUNT_NAME_PAYPAL = 3;
	/** 4:银行卡账号 */
	public static final int ACCOUNT_NAME_BANK_CARD = 4;
	
	

	
	/** 基于区块链诊断设备允许的产品类型ID */
	public static String DIAGDEVICE_ALLOW_PDT_TYPE = "DIAGDEVICE_ALLOW_PDT_TYPE";
	/** 基于区块链诊断设备允许的产品类型下的配置ID */
	public static String DIAGDEVICE_ALLOW_CONFIG_ID = "DIAGDEVICE_ALLOW_CONFIG_ID";
	/** 基于区块链诊断软件固定的价格 */
	public static String DIAGDEVICE_FIXED_PRICE = "DIAGDEVICE_FIXED_PRICE";
	
	/** 远程诊断xmpp相关配置 */
	public static String YC_DIAG_XMPP_DOMAIN;
	public static String YC_DIAG_XMPP_IP;
	public static String YC_DIAG_XMPP_PORT;
	
	@Value("${yc.diag.xmpp.domain}")
	public void setYC_DIAG_XMPP_DOMAIN(String yC_DIAG_XMPP_DOMAIN) {
		YC_DIAG_XMPP_DOMAIN = yC_DIAG_XMPP_DOMAIN;
	}
	@Value("${yc.diag.xmpp.ip}")
	public void setYC_DIAG_XMPP_IP(String yC_DIAG_XMPP_IP) {
		YC_DIAG_XMPP_IP = yC_DIAG_XMPP_IP;
	}
	@Value("${yc.diag.xmpp.port}")
	public void setYC_DIAG_XMPP_PORT(String yC_DIAG_XMPP_PORT) {
		YC_DIAG_XMPP_PORT = yC_DIAG_XMPP_PORT;
	}

	private static String service;
	// private static String communicateId;
	private static String communicatekey;
	private static String tokenMethod;
	// 深圳智能合约配置服务器url
	private static String szznhypzservice;
	// 深圳智能合约分配服务器url
	private static String szznhyfpservice;
	// 北京区块链存证服务器url
	private static String bjqklczservice;

	private static String createAccount;
	private static String updateAccount;

	private static String createDAO;
	private static String createAllocation;
	private static String updateAllocation;
	private static String allocateOrder;
	
	private static String appId;
	private static String developKey;
	private static String company;
	private static String branchShop;
	
	private static String testQueueFlag;
	
	/** 消费token失效时间，单位为：秒 */
	public static long CONSUMER_TOKEN_EXPIRE_TIME;
	
	

	@Value("${consumer.token.expire.time}")
	public void setCONSUMER_TOKEN_EXPIRE_TIME(long cONSUMER_TOKEN_EXPIRE_TIME) {
		Constants.CONSUMER_TOKEN_EXPIRE_TIME = cONSUMER_TOKEN_EXPIRE_TIME;
	}
	
	public static String getService() {
		return service;
	}

	@Value("${service}")
	public void setService(String service) {
		Constants.service = service;
	}

	public static String getCommunicatekey() {
		return communicatekey;
	}

	@Value("${communicatekey}")
	public void setCommunicatekey(String communicatekey) {
		Constants.communicatekey = communicatekey;
	}

	public static String getTokenMethod() {
		return tokenMethod;
	}

	@Value("${tokenMethod}")
	public void setTokenMethod(String tokenMethod) {
		Constants.tokenMethod = tokenMethod;
	}

	public static String getSzznhypzservice() {
		return szznhypzservice;
	}

	@Value("${szznhypzservice}")
	public void setSzznhypzservice(String szznhypzservice) {
		Constants.szznhypzservice = szznhypzservice;
	}

	public static String getSzznhyfpservice() {
		return szznhyfpservice;
	}

	@Value("${szznhyfpservice}")
	public void setSzznhyfpservice(String szznhyfpservice) {
		Constants.szznhyfpservice = szznhyfpservice;
	}

	public static String getBjqklczservice() {
		return bjqklczservice;
	}

	@Value("${bjqklczservice}")
	public void setBjqklczservice(String bjqklczservice) {
		Constants.bjqklczservice = bjqklczservice;
	}

	public static String getCreateAccount() {
		return createAccount;
	}

	@Value("${createAccount}")
	public void setCreateAccount(String createAccount) {
		Constants.createAccount = createAccount;
	}

	public static String getUpdateAccount() {
		return updateAccount;
	}

	@Value("${updateAccount}")
	public void setUpdateAccount(String updateAccount) {
		Constants.updateAccount = updateAccount;
	}

	public static String getCreateDAO() {
		return createDAO;
	}

	@Value("${createDAO}")
	public void setCreateDAO(String createDAO) {
		Constants.createDAO = createDAO;
	}

	public static String getCreateAllocation() {
		return createAllocation;
	}

	@Value("${createAllocation}")
	public void setCreateAllocation(String createAllocation) {
		Constants.createAllocation = createAllocation;
	}

	public static String getUpdateAllocation() {
		return updateAllocation;
	}

	@Value("${updateAllocation}")
	public void setUpdateAllocation(String updateAllocation) {
		Constants.updateAllocation = updateAllocation;
	}

	public static String getAllocateOrder() {
		return allocateOrder;
	}

	@Value("${allocateOrder}")
	public void setAllocateOrder(String allocateOrder) {
		Constants.allocateOrder = allocateOrder;
	}
	public static String getAppId() {
		return appId;
	}
	@Value("${appId}")
	public void setAppId(String appId) {
		Constants.appId = appId;
	}
	
	public static String getDevelopKey() {
		return developKey;
	}
	@Value("${developKey}")
	public  void setDevelopKey(String developKey) {
		Constants.developKey = developKey;
	}
	public static String getCompany() {
		return company;
	}
	@Value("${company}")
	public  void setCompany(String company) {
		Constants.company = company;
	}
	public static String getBranchShop() {
		return branchShop;
	}
	@Value("${branchShop}")
	public  void setBranchShop(String branchShop) {
		Constants.branchShop = branchShop;
	}
	public static String getTestQueueFlag() {
		return testQueueFlag;
	}
	@Value("${testQueueFlag}")
	public void setTestQueueFlag(String testQueueFlag) {
		Constants.testQueueFlag = testQueueFlag;
	}

}
