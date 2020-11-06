package com.launch.diagdevice.common.result;

/**
 * 
 * APP客户端返回结果
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月8日
 */
public class AppListResult extends AppResult{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 总条数 */
	private Long count = 0L; 

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "AppListResult [count=" + count + ", getData()=" + getData() + ", getCode()=" + getCode()
				+ ", getMessage()=" + getMessage() + ", getCurrentTime()=" + getCurrentTime() + "]";
	}

	

}
