package org.thinkinghub.gateway.oauth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.thinkinghub.gateway.oauth.exception.GatewayException;
import org.thinkinghub.gateway.oauth.registry.LocaleMessageSourceRegistry;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlingController {
	
    @Resource
    private LocaleMessageSourceRegistry localeMessageSourceService;
    
	@ExceptionHandler({Exception.class})
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public String defaultError(HttpServletRequest request, HttpServletResponse response, Exception exception){
		log.error("Request "+ request.getRequestURL() + " raised " + exception);
		return "An error happened";
	}
	
	@ExceptionHandler({GatewayException.class})
	public String gatewayError(HttpServletRequest request, Exception exception){
		log.error("Request "+ request.getRequestURL() + " raised " + exception);
		return "gateway error";
	}
}
