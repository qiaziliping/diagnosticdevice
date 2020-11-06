package com.launch.diagdevice.common.exception;

public class ParameterNullErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1061959828517375908L;

	private String parameterName = "";

	public ParameterNullErrorException(String parameterName) {
		super();
		this.parameterName = parameterName;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	
}
