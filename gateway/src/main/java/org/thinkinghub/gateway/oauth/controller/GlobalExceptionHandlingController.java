package org.thinkinghub.gateway.oauth.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;
import org.thinkinghub.gateway.oauth.entity.ErrorType;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.event.OAuthProcessErrorEvent;
import org.thinkinghub.gateway.oauth.exception.*;
import org.thinkinghub.gateway.oauth.registry.EventPublisher;
import org.thinkinghub.gateway.oauth.registry.LocaleMessageSourceRegistry;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;
import org.thinkinghub.gateway.oauth.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlingController {

    @Autowired
    private AuthenticationHistoryRepository authenticationHistoryRepository;

    @ExceptionHandler({OAuthProcessingException.class})
    public void oauthError(HttpServletRequest request, HttpServletResponse response,
                           OAuthProcessingException e) {
        log.error("Request " + request.getRequestURL() + " raised " + e);
        String state = request.getParameter("state");
        AuthenticationHistory ah = authenticationHistoryRepository.findByState(state);
        ErrorResponse err = new ErrorResponse().gwErrorCode(e.getGWErrorCode())
                .gwErrorMessage(LocaleMessageSourceRegistry.instance().getMessage(e.getGWErrorCode()))
                .oauthErrorCode(e.getErrorCode()).oauthErrorMessage(e.getErrorMessage())
                .errorType(ErrorType.THIRDPARTY).serviceType(ah.getServiceType());

        EventPublisher.instance().publishEvent(new OAuthProcessErrorEvent(err, state));
        String redirectUrl = ah.getCallback() + "?errorCode=" + err.getGwErrorCode() + "&errorMessage="
                + LocaleMessageSourceRegistry.instance().getMessage(err.getGwErrorCode()) + "&fullErrorMessage="
                + err.toString();
        redirect(response, redirectUrl);
    }

    @ExceptionHandler({GatewayException.class})
    public void gatewayError(HttpServletRequest request, HttpServletResponse response, GatewayException e) {
        log.error("Request " + request.getRequestURL() + " raised " + e);
        String state = request.getParameter("state");
        ErrorResponse err = new ErrorResponse().gwErrorCode(e.getGWErrorCode())
                .gwErrorMessage(LocaleMessageSourceRegistry.instance().getMessage(e.getGWErrorCode()))
                .errorType(ErrorType.THIRDPARTY).serviceType(ServiceType.valueOf(request.getParameter("service")));

        EventPublisher.instance().publishEvent(new OAuthProcessErrorEvent(err, state));
        AuthenticationHistory ah = authenticationHistoryRepository.findByState(state);
        if (ah != null) {
            String redirectUrl = ah.getCallback() + "?errorCode=" + e.getGWErrorCode() + "&errorMessage="
                    + LocaleMessageSourceRegistry.instance().getMessage(e.getGWErrorCode());
            redirect(response, redirectUrl);
        }
    }

    @ExceptionHandler({UserKeyNotFoundException.class, ServiceTypeNotFoundException.class,
            CallbackUrlNotFoundException.class, IncorrectUrlFormatException.class})
    public ResponseEntity<String> mandatoryParamError(HttpServletRequest request, GatewayException e) {
        log.error("Request " + request.getRequestURL() + " raised " + e);
        String errStr = "errorCode is: " + e.getGWErrorCode() + " and errorMessage is: "
                + LocaleMessageSourceRegistry.instance().getMessage(e.getGWErrorCode());
        return new ResponseEntity<>(errStr, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void defaultError(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error("Request " + request.getRequestURL() + " raised " + e);
        AuthenticationHistory ah = authenticationHistoryRepository.findByState(request.getParameter("state"));
        String redirectUrl = ah.getCallback() + "?errorCode=GW99999" + "&errorMessage="
                + LocaleMessageSourceRegistry.instance().getMessage("GW99999");
        redirect(response, redirectUrl);
    }

    private void redirect(HttpServletResponse response, String redirectUrl) {
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            log.error("fail to redirect to the url: " + redirectUrl, e);
            try {
                response.sendError(HttpStatus.BAD_REQUEST.value(), "fail to redirect to the url: " + redirectUrl);
            } catch (IOException e1) {
                log.error("failed to send error message back", e1);
            }
        }
    }

}
