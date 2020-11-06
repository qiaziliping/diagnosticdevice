package com.launch.diagdevice.common.constant;

/**
 * 
 * APP返回Code常量类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月8日
 */
public class AppCodeConstant extends Constants {
	
	
	
	
	/**
	 * 请求参数为空。
	 */
	public static final int REQUEST_PARAMETER_EMPTY = 401;
	/**
	 * 请求参数不合法.
	 */
	public static final int REQUEST_PARAMETER_ILLEGAL = 402;
	
	
	/** 输入参数填写有误 */
	public static final int PARAM_ERROR = 1022; 
	/** 缺少参数 */
	public static final int PARAM_LACK = 1023; 
	/** 用户名已存在 */
	public static final int USER_EXIST = 1024; 
	/** 验证码错误 */
	public static final int VER_CODE_ERROR = 1025; 
	/** 存在敏感词 */
//	public static final int EXIST_SENSITIVE_WORD = 1026; 
	
	/** 签名无效 */
	public static final int SIGN_FAIL = 10001; 
	/** 密码有误 */
	public static final int PASSWORD_ERROR = 30001; 
	/** 邮箱被注册 */
	public static final int EMAIL_EXIST = 30006; 
	/** 手机号已经被绑定 */
	public static final int MOBILE_EXIST = 30007; 
	/** 邮箱不正确 */
	public static final int EMAIL_ERROR = 30009; 
	/** 注册源有误 */
	public static final int SOURCE_REGISTER_ERROR = 30010; 
	/** zipcode过长10 */
//	public static final int xx = 30011; 
	/** loginKey填写有误 */
//	public static final int xx = 30012; 
	/** nickname过长255 */
//	public static final int xx = 30013; 
	/** 手机号未绑定 */
//	public static final int xx = 30030; 
	/** 操作频繁操作，60s/2次 */
//	public static final int xx = 30031; 
	/** 超过日发送次数 mobile/3次，email/10次 */
//	public static final int xx = 30040; 
	
	/** 登录失败 */
	public static final int LOGIN_FAIL = 100001; 
	/** 用户不存在 */
	public static final int USER_NOT_EXIST = 100002; 
	
	/** 用户名6-20位由英文字母开头,包含英文字母,数字或下划线的字符 */
	public static final int USERNAME_FORMAT_ERROR = 100003; 
	/** 请输入密码（由字母、数字、@和_组成，长度6-20位） */
	public static final int PASSWORD_FORMAT_ERROR = 100004; 
	
	/** 该序列号不允许此操作 */
	public static final int SERIAL_NO_ERROR = 100020; 
	/** 请勿重复提交 */
	public static final int REPEAT_COMMIT_DATA = 100021;
	/** 该序列号不存在 */
	public static final int SERIAL_NO_NOT_EXIST = 100022;
	/** 序列号非法 */
	public static final int SERIAL_NO_ILLEGAL = 100023;
	/** 序列号已存在分配 */
	public static final int SERIAL_NO_EXIST_ALLOCATION = 100024;
	
	/** 该订单号不存在 */
	public static final int ORDER_NO_NOT_EXIST = 100050;
	/** 该订单号和用户的消费token不一致  */
	public static final int ORDER_NO_USER_CONSUMER_TOKEN_DIFFERENCE = 100051;
	/** 该订单不属于该用户  */
	public static final int ORDER_NO_NOT_BELONG_USER = 100052;
	/** 软件名称和订单不一致  */
	public static final int ORDER_NO_SOFTNAME_DIFFERENCE = 100053;
	
	/** 该币种不支持支付宝  */
	public static final int CURRENCY_NOT_SUPPORT_ALIPAY = 100060;
	/** 该币种不支持PAYPAL  */
	public static final int CURRENCY_NOT_SUPPORT_PAYPAL = 100061;
	
	/** 时间格式错误 */
	public static final int DATE_FORMART_ERROR = 100100;
	/** 开始日期大于结束日期 */
	public static final int STARTDATE_GT_ENDDATE = 100101;
	
	/** 用户名大于20字符 */
//	public static final int xx = 100004;
	/** 用户名小于5字符 */
//	public static final int xx = 100005;
	/** 密码错误 */
//	public static final int xx = 100011;
	/** 已经被注册 */
//	public static final int xx = 110001; 
	/** 没有注册过用户 */
//	public static final int xx = 110002; 
	
	
	/**
	 * 为了兼容MYCAR
	 * LIPING
	 */
	public static String checkCodeOld(int code) {
		
		String msg = "系统内部错误";
		switch (code) {
		case SUCCESS:
			msg = "success";
			break;
		case ERROR:
			msg = "服务器内部错误";
			break;
		case PARAM_ERROR:
			msg = "输入参数有误";
			break;	
		case PARAM_LACK:
			msg = "缺少参数";
			break;	
		case USER_EXIST:
			msg = "用户名已存在";
			break;	
		case VER_CODE_ERROR:
			msg = "验证码错误";
			break;	
		case SIGN_FAIL:
			msg = "签名无效";
			break;	
		case PASSWORD_ERROR:
			msg = "密码有误";
			break;	
		case EMAIL_EXIST:
			msg = "邮箱被注册";
			break;	
		case MOBILE_EXIST:
			msg = "手机号已经被绑定";
			break;	
		case EMAIL_ERROR:
			msg = "邮箱不正确";
			break;	
		case LOGIN_FAIL:
			msg = "登录失败";
			break;	
		case USER_NOT_EXIST:
			msg = "用户不存在";
			break;	
		case SERIAL_NO_ERROR:
			msg = "该序列号不允许此操作";
			break;	
		case USERNAME_FORMAT_ERROR:
			msg = "请输入6-20位由英文字母开头,包含英文字母,数字或下划线的字符";
			break;	
		case PASSWORD_FORMAT_ERROR:
			msg = "请输入密码（由字母、数字、@和_组成，长度不超过20位）";
			break;	
		case REPEAT_COMMIT_DATA:
			msg = "请勿重复提交";
			break;	
		case SERIAL_NO_NOT_EXIST:
			msg = "该序列号不存在";
			break;	
		case SERIAL_NO_ILLEGAL:
			msg = "序列号非法";
			break;	
		case SERIAL_NO_EXIST_ALLOCATION:
			msg = "序列号已存在分配";
			break;	
		case ORDER_NO_NOT_EXIST:
			msg = "该订单号不存在";
			break;	
		case ORDER_NO_USER_CONSUMER_TOKEN_DIFFERENCE:
			msg = "该订单号和用户的消费token不一致";
			break;	
		case ORDER_NO_NOT_BELONG_USER:
			msg = "该订单不属于该用户";
			break;	
		case ORDER_NO_SOFTNAME_DIFFERENCE:
			msg = "软件名称和订单不一致";
			break;	
		case CURRENCY_NOT_SUPPORT_ALIPAY:
			msg = "该币种不支持支付宝";
			break;	
		case CURRENCY_NOT_SUPPORT_PAYPAL:
			msg = "该币种不支持PAYPAL";
			break;	
		case DATE_FORMART_ERROR:
			msg = "时间格式错误";
			break;	
		case STARTDATE_GT_ENDDATE:
			msg = "开始日期大于结束日期";
			break;	
		}
		return msg;
	}
	
	

	// ------------------------------------------user-----------------------------------------/
	/** 登录验证失败  */
	public static final int USER_AUTH_FAIL = 100;
	/** 101:用户不存在，请注册再登录   */
	public static final int USER_NAME_NO_EXIST = 101;
	/** 102:用户密码错误   */
	public static final int USER_PASSWORD_ERROR = 102;
	/** 103:不能为空或长度不符合  */
	public static final int USER_NAME_INVALID = 103;
	/** 104:密码不能为空或长度超过其允许的最大长度   */
	public static final int USER_PASSWORD_INVALID = 104;
	/** 105:手机号码不能为空或长度超过其允许的最大长度   */
	public static final int USER_MOBILE_FORMAT_ERROR = 105;
	/** 106:手机号码格式不正确  */
	public static final int USER_MOBILE_INVALID = 106;
	/** 107:验证码验证失败  */
	public static final int VERIFY_CODE_ERROR = 107;
	/** 108:该用户名已经注册   */
	public static final int USER_NAME_REPEATED = 108;
	/** 109:该手机号码或邮箱已经注册   */
	public static final int REGINFO_EXIST = 109;
	/** 110:该手机号码或邮箱没有注册，请先注册   */
	public static final int USER_NO_EXIST = 110;
	/** 111:短信发送失败  */
	public static final int SMS_SEND_FAILURE = 111;
	/** 112:用户未登录  */
	public static final int USER_NOT_LOGGED = 112;
	// ------------------------------------------user-----------------------------------------/

	/** 205:充值金额有误  */
	public static final int RECHARGE_MONEY_ERROR = 205;
	
	/** 300:版本更新失败，客户端版本高于服务端最新版本   */
	public static final int VERSION_UPDATE_FAILURE = 300;
	/** 301:用户反馈意见不能为空或长度超过其允许的最大长度  */
	public static final int USER_FEEDBACK_INVALID = 301;
	/** 请求中包含错误的参数 */
	public static final int BAD_REQUEST = 400;
	/** 401:未授权，当前请求需要用户验证    */
	public static final int UNAUTHORIZED = 401;
	/**  403:用户session过期，服务器拒绝执行该请求  */
	public static final int FORBIDDEN = 403;
	/** 404:请求的资源不存在  */
	public static final int NOT_FOUND = 404;
	/** 406:验证码未校验  */
	public static final int VERIFY_CODE_NOT_CHECK = 406;
	/** 407:验证码获取频率太快  */
	public static final int SEND_FREQUENCY_FAST = 407;
	/**  408:未提供该第三方登陆 */
	public static final int SYS_NOT_SUPORT_THIRD_PARTY = 408;
	/** 409:授权失败  */
	public static final int SYS_THIRD_PARTY_AUTH_ERROR = 409;
	/** 1022:参数不合法  */
	public static final int PARAM_NOT_VALID = 1022;
	/**  1023:缺少参数 */
	public static final int PARAM_MISS = 1023;
	/** 1024:json格式错误  */
	public static final int JSON_NOT_VALID = 1024;
	/** 1025 sign 不能为空  */
	public static final int SIGN_ISNULL = 1025;
	/** 1026  签名失败  */
	//public static final int SIGN_FAIL = 1026;
	/** 1027 消费token验证失败  */
	public static final int CONSUMER_TOKEN_FAIL = 1027;

	public static String checkCode(int code) {
		String msg = "系统内部错误";
		switch (code) {
		case SUCCESS:
			msg = "success";
			break;
		case ERROR:
			msg = "服务器内部错误";
			break;
		case USER_AUTH_FAIL:
			msg = "登录验证失败";
			break;
		case USER_NAME_NO_EXIST:
			msg = "用户不存在，请注册再登录";
			break;
		case USER_PASSWORD_ERROR:
			msg = "用户密码错误";
			break;
		case USER_NAME_INVALID:
			msg = "不能为空或长度不符合";
			break;
		case USER_PASSWORD_INVALID:
			msg = "密码不能为空或长度超过其允许的最大长度";
			break;
		case USER_MOBILE_FORMAT_ERROR:
			msg = "手机号码不能为空或长度超过其允许的最大长度";
			break;
		case USER_MOBILE_INVALID:
			msg = "手机号码格式不正确";
			break;
		case VERIFY_CODE_ERROR:
			msg = "验证码验证失败";
			break;
		case USER_NAME_REPEATED:
			msg = "该用户名已经注册";
			break;
		case REGINFO_EXIST:
			msg = "该手机号码或邮箱已经注册";
			break;
		case USER_NO_EXIST:
			msg = "该手机号码或邮箱未注册，请先注册";
			break;
		case SMS_SEND_FAILURE:
			msg = "短信发送失败";
			break;
		case USER_NOT_LOGGED:
			msg = "用户未登录";
			break;
		case RECHARGE_MONEY_ERROR:
			msg = "金额有误";
			break;
		case VERSION_UPDATE_FAILURE:
			msg = "版本更新失败，客户端版本高于服务端最新版本";
			break;
		case USER_FEEDBACK_INVALID:
			msg = "用户反馈意见不能为空或长度超过其允许的最大长度";
			break;
		case BAD_REQUEST:
			msg = "请求中包含错误的参数";
			break;
		case UNAUTHORIZED:
			msg = "当前应用访问未授权";
			break;
		case FORBIDDEN:
			msg = "用户session过期，服务器拒绝执行该请求";
			break;
		case NOT_FOUND:
			msg = "请求的资源不存在";
			break;
		case VERIFY_CODE_NOT_CHECK:
			msg = "未验证验证码";
			break;
		case SEND_FREQUENCY_FAST:
			msg = "验证码获取频率太快，请稍后重试";
			break;
		case SYS_NOT_SUPORT_THIRD_PARTY:
			msg = "未提供该第三方登陆";
			break;
		case SYS_THIRD_PARTY_AUTH_ERROR:
			msg = "授权登陆失败";
			break;
		case PARAM_NOT_VALID:
			msg = "参数不合法";
			break;
		case PARAM_MISS:
			msg = "缺少参数";
			break;
		case JSON_NOT_VALID:
			msg = "JSON格式错误";
			break;
		case SIGN_ISNULL:
			msg = "sign 不能为空";
			break;
		case SIGN_FAIL:
			msg = "签名失败";
			break;
		case CONSUMER_TOKEN_FAIL:
			msg = "消费token验证失败";
			break;
		}
		return msg;
	}
}
