package org.thinkinghub.gateway.oauth.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thinkinghub.gateway.oauth.exception.GatewayException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlingController {

	@ExceptionHandler({GatewayException.class})
	public String gatewayBusinessError(HttpServletRequest request, Exception exception){
		log.error("Request "+ request.getRequestURL() + " raised " + exception);
		return "gatewayerror";
	}
	
	@ExceptionHandler({SQLException.class})
	public String databaseError(HttpServletRequest request, Exception exception){
		log.error("Request "+ request.getRequestURL() + " raised " + exception);
		return "databaseerror";
	}
	
	@ExceptionHandler({IOException.class})
	public String IOError(HttpServletRequest request, Exception exception){
		log.error("Request "+ request.getRequestURL() + " raised " + exception);
		return "ioerror";
	}
}
