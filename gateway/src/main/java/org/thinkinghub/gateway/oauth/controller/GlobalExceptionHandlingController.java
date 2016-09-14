package org.thinkinghub.gateway.oauth.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.thinkinghub.gateway.oauth.exception.GatewayException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlingController {

	@ExceptionHandler({Exception.class, RuntimeException.class})
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public String defaultError(HttpServletRequest request, Exception exception){
		log.error("Request "+ request.getRequestURL() + " raised " + exception);
		return "An error happened";
	}
	
	@ExceptionHandler({GatewayException.class})
	public String gatewayError(HttpServletRequest request, Exception exception){
		log.error("Request "+ request.getRequestURL() + " raised " + exception);
		return "gatewayerror";
	}
	
	@ExceptionHandler({MissingServletRequestParameterException.class})
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public String requestParamError(HttpServletRequest request, Exception exception){
		log.error("Request "+ request.getRequestURL() + " raised " + exception);
		return "Your request is missing parameter(s)";
	}
	
	@ExceptionHandler({SQLException.class})
	public String databaseError(HttpServletRequest request, Exception exception){
		log.error("Request "+ request.getRequestURL() + " raised " + exception);
		return "databaseerror";
	}
	
	@ExceptionHandler({IOException.class})
	public String ioError(HttpServletRequest request, Exception exception){
		log.error("Request "+ request.getRequestURL() + " raised " + exception);
		return "ioerror";
	}
}
