package org.thinkinghub.gateway.oauth.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.thinkinghub.gateway.oauth.bean.ErrorResponse;
import org.thinkinghub.gateway.oauth.entity.ErrorType;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.event.OAuthProcessErrorEvent;
import org.thinkinghub.gateway.oauth.exception.GatewayException;
import org.thinkinghub.gateway.oauth.exception.OAuthProcessingException;
import org.thinkinghub.gateway.oauth.registry.EventPublisher;
import org.thinkinghub.gateway.oauth.registry.LocaleMessageSourceRegistry;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlingController {

    @Resource
    private LocaleMessageSourceRegistry localeMessageSourceService;

    @ExceptionHandler({OAuthProcessingException.class})
    public void oauthError(HttpServletRequest request, HttpServletResponse response, OAuthProcessingException exception) {
        log.error("Request " + request.getRequestURL() + " raised " + exception);
        String state=request.getParameter("state");
        ErrorResponse err = new ErrorResponse(exception.getGWErrorCode(),
                LocaleMessageSourceRegistry.instance().getMessage(exception.getGWErrorCode()), exception.getErrCode(),
                exception.getErrMsg(), ErrorType.THIRDPARTY, ServiceType.valueOf((String)request.getParameter("service")));
        EventPublisher.instance().publishEvent(new OAuthProcessErrorEvent(err, state));
        String redirectUrl = request.getParameter("callbackUrl") + "?error=" + err.toString();
        redirect(response, redirectUrl);
    }

    @ExceptionHandler({GatewayException.class})
    public void gatewayError(HttpServletRequest request, HttpServletResponse response, GatewayException exception) {
        log.error("Request " + request.getRequestURL() + " raised " + exception);
        String state=request.getParameter("state");
        ErrorResponse err = new ErrorResponse(exception.getGWErrorCode(),
                LocaleMessageSourceRegistry.instance().getMessage(exception.getGWErrorCode()),
                ErrorType.GATEWAY, ServiceType.valueOf((String)request.getParameter("service")));
        EventPublisher.instance().publishEvent(new OAuthProcessErrorEvent(err, state));
        String redirectUrl = request.getParameter("callbackUrl") + "?error=" + exception.getGWErrorCode() + " - "
                + LocaleMessageSourceRegistry.instance().getMessage(exception.getGWErrorCode());
        redirect(response, redirectUrl);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void defaultError(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        log.error("Request " + request.getRequestURL() + " raised " + exception);
        String redirectUrl = request.getParameter("callbackUrl") + "?error=" + LocaleMessageSourceRegistry.instance().getMessage("GW99999");
        redirect(response, redirectUrl);
    }

    private void redirect(HttpServletResponse response, String redirectUrl) {
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            log.error("Exception occurs when redirecting to the url: " + redirectUrl, e);
        }
    }

}
