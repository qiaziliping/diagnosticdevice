package com.launch.diagdevice.common.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.launch.diagdevice.common.constant.AppCodeConstant;

@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class GlobalControllerExceptionHandler {
	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ApiErrorResponse constraintViolationException(ConstraintViolationException ex) {
		ex.printStackTrace();
		return new ApiErrorResponse(500, ex.getMessage());
	}

	@ExceptionHandler(value = { IllegalArgumentException.class })
	public ApiErrorResponse IllegalArgumentException(IllegalArgumentException ex) {
		return new ApiErrorResponse(501, ex.getMessage());
	}

	@ExceptionHandler(value = { NoHandlerFoundException.class })
	public ApiErrorResponse noHandlerFoundException(Exception ex) {
		return new ApiErrorResponse(404, ex.getMessage());
	}

	@ExceptionHandler(value = { Exception.class })
	public ApiErrorResponse unknownException(Exception ex) {
		ex.printStackTrace();
		return new ApiErrorResponse(500, ex.getMessage());
	}

	@ExceptionHandler(value = { SignErrorException.class })
	public ApiErrorResponse unknownException(SignErrorException ex) {
		return new ApiErrorResponse(-1, "sign invalidate!");
	}

	/*@ExceptionHandler(value = { IPErrorException.class })
	public ApiErrorResponse unknownException(IPErrorException ex) {
		return new ApiErrorResponse(WsConstants.IP_NOT_PERMIT, "ip not permit!");
	}*/

	/*@ExceptionHandler(value = { APPErrorException.class })
	public ApiErrorResponse unknownException(APPErrorException ex) {
		return new ApiErrorResponse(WsConstants.IP_NOT_PERMIT, "app not permit!");
	}*/

	@ExceptionHandler(value = { ParameterNullErrorException.class })
	public ApiErrorResponse unknownException(ParameterNullErrorException ex) {
		return new ApiErrorResponse(AppCodeConstant.REQUEST_PARAMETER_EMPTY,
				ex.getParameterName() + " request parameter empty!");
	}

	@ExceptionHandler(value = { ParameterIllegalErrorException.class })
	public ApiErrorResponse unknownException(ParameterIllegalErrorException ex) {
		return new ApiErrorResponse(AppCodeConstant.REQUEST_PARAMETER_ILLEGAL,
				ex.getParameterName() + " request parameter illegal!");
	}

}
