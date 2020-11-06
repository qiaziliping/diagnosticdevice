package com.launch.diagdevice.common.result;

/**
 * 
 * APP客户端返回结果
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月8日
 */
public class AppResult extends Result{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5589830233411590424L;
	/** 返回客户端数据对象 */
	private Object data;

	public Object getData() {
		if (data == null)
			data = new Object();
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "AppResult [data=" + data + ", getCode()=" + getCode() + ", getMessage()=" + getMessage()
				+ ", getCurrentTime()=" + getCurrentTime() + "]";
	}

}
