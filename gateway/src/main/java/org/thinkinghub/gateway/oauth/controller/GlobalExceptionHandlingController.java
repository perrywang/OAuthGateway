package org.thinkinghub.gateway.oauth.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.thinkinghub.gateway.oauth.bean.ErrorResponse;
import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;
import org.thinkinghub.gateway.oauth.exception.GatewayException;
import org.thinkinghub.gateway.oauth.exception.OAuthProcessingException;
import org.thinkinghub.gateway.oauth.registry.LocaleMessageSourceRegistry;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlingController {
	
    @Resource
    private LocaleMessageSourceRegistry localeMessageSourceService;
    
    @Autowired
    private AuthenticationHistoryRepository authenticationHistoryRepository;
    
	private ErrorResponse err;
    
	@ExceptionHandler({Exception.class})
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public void defaultError(HttpServletRequest request, @RequestParam("state")String state, HttpServletResponse response, Exception exception){
		log.error("Request "+ request.getRequestURL() + " raised " + exception);
		AuthenticationHistory ah = authenticationHistoryRepository.findByState(state);
		String redirectUrl = ah.getCallback() + "?error=" + LocaleMessageSourceRegistry.instance().getMessage("GW999") ;
		try{
		response.sendRedirect(redirectUrl);
		}catch(IOException e){
			throw new GatewayException("IOException occurred");
		}
	}
	
	@ExceptionHandler({GatewayException.class})
	public void gatewayError(HttpServletRequest request,@RequestParam("state")String state, HttpServletResponse response, GatewayException exception){
		log.error("Request "+ request.getRequestURL() + " raised " + exception);
		AuthenticationHistory ah = authenticationHistoryRepository.findByState(state);
		String redirectUrl = ah.getCallback() + "?error=" + LocaleMessageSourceRegistry.instance().getMessage(exception.getErrorCode()) ;
		try{
		response.sendRedirect(redirectUrl);
		}catch(IOException e){
			throw new GatewayException("IOException occurred");
		}
	}
	
	@ExceptionHandler({OAuthProcessingException.class})
	public void oauthError(HttpServletRequest request,@RequestParam("state")String state, HttpServletResponse response, OAuthProcessingException exception){
		log.error("Request "+ request.getRequestURL() + " raised " + exception);
		AuthenticationHistory ah = authenticationHistoryRepository.findByState(state);
		err = new ErrorResponse(LocaleMessageSourceRegistry.instance().getMessage(exception.getErrorCode()),exception.getErrCode(),exception.getErrMsg());
		String redirectUrl = ah.getCallback() + "?error=" + err.toString();
		try{
		response.sendRedirect(redirectUrl);
		}catch(IOException e){
			throw new GatewayException("IOException occurred");
		}
	}

}
