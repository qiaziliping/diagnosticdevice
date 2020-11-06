package com.launch.diagdevice.common.result;

import java.io.Serializable;
import java.util.Date;

import com.launch.diagdevice.common.constant.Constants;
import com.launch.diagdevice.common.util.DateUtils;

/**
 * 
 * 返回结果
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月8日
 */
public class Result implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	// -- 返回代码定义 --//
	// 按项目的规则进行定义, 比如4xx代表客户端参数错误，5xx代表服务端业务错误等.
	
	
//	public static final String SYSTEM_SUCCESS_MESSAGE = "operate success";
	public static final String SYSTEM_SUCCESS_MESSAGE = "success";
	public static final String SYSTEM_ERROR_MESSAGE = "Runtime unknown internal error.";

	// -- Result基本属性 --//
	private int code = Constants.SUCCESS;
	private String message = SYSTEM_SUCCESS_MESSAGE;
	private String currentTime = DateUtils.convertDateToString(DateUtils.DATETIME, new Date());

	/**
	 * 创建结果.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Result> T setError(int resultCode, String resultMessage) {
		code = resultCode;
		message = resultMessage;
		return (T) this;
	}

	/**
	 * 创建默认异常结果.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Result> T setDefaultError() {
		return (T) setError(Constants.ERROR, SYSTEM_ERROR_MESSAGE);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	
}