package org.thinkinghub.gateway.oauth.controller;

import com.github.scribejava.core.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thinkinghub.gateway.oauth.bean.GatewayResponse;
import org.thinkinghub.gateway.oauth.entity.*;
import org.thinkinghub.gateway.oauth.event.StartingRetriveAccessTokenEvent;
import org.thinkinghub.gateway.oauth.exception.UserNotFoundException;
import org.thinkinghub.gateway.oauth.queue.QueuableTask;
import org.thinkinghub.gateway.oauth.registry.ServiceRegistry;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;
import org.thinkinghub.gateway.oauth.repository.UserRepository;
import org.thinkinghub.gateway.oauth.service.AbstractOAuthService;
import org.thinkinghub.gateway.oauth.service.QueueService;
import org.thinkinghub.gateway.oauth.service.ResultHandlingService;
import org.thinkinghub.gateway.oauth.util.Base64Encoder;
import org.thinkinghub.gateway.oauth.util.JsonUtil;
import org.thinkinghub.gateway.oauth.util.MD5Encrypt;
import org.thinkinghub.gateway.util.IDGenerator;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class GatewayController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationHistoryRepository authenticationHistoryRepository;

    @Autowired
    private QueueService queueService;

    @Autowired
    private ResultHandlingService resultHandlingService;

    @RequestMapping(value = "/oauthgateway", method = RequestMethod.GET)
    public void route(@RequestParam(value = "callbackUrl", required = false) String callbackUrl,
                      @RequestParam(value = "key", required = false) String key,
                      @RequestParam(value = "service", required = false) ServiceType service) {
        User user = userRepository.findByKey(key);
        if (user != null) {
            String state = Long.toString(IDGenerator.nextId());
            eventPublisher.publishEvent(new StartingRetriveAccessTokenEvent(user, state, callbackUrl));
            AbstractOAuthService oauthService = ServiceRegistry.instance().getService(service);

            String returnURL = oauthService.getAuthorizationUrl(state);
            try {
                getHttpResponse().sendRedirect(returnURL);
            } catch (IOException e) {
                log.error("Exception occurred while redirect the custom callback url", e);
            }
        } else {
            throw new UserNotFoundException(String.format("can not find valid user relating with key: %s", key));
        }
    }

    @RequestMapping(value = "/oauth/{service}", method = RequestMethod.GET)
    public void requestFacebookAccessToken(@RequestParam("code") String code,
                                           @RequestParam("state") String state,
                                           @PathVariable String service) {
        requestAccessToken(code, state, Enum.valueOf(ServiceType.class, service.toUpperCase()));
    }

    private void requestAccessToken(String code, String state, ServiceType type) {
        AbstractOAuthService facebookService = ServiceRegistry.instance().getService(type);
        Response userInfoResponse = facebookService.getResponse(state, code);
        String redirectUrl = handleResponse(userInfoResponse, type, state);
        try {
            getHttpResponse().sendRedirect(redirectUrl);
        } catch (IOException e) {
            log.error("Exception occurred while redirect the custom callback url", e);
        }
    }

    private String handleResponse(Response response, ServiceType type, String state) {
        AuthenticationHistory ah = authenticationHistoryRepository.findByState(state);
        GatewayResponse gatewayResponse = resultHandlingService.getRetBean(response, type);
        try {
            logAuthHistory(ah, gatewayResponse);
        } catch (Exception e) {
            log.error("Exception happens when log an authentication history record", e);
            //here failed to write the record in database, but may try n times as the failure may only happen at that time,
            //but succeed this time.
        }

        String resultStr = Base64Encoder.encode(JsonUtil.toJson(gatewayResponse));
        String redirectUrl = ah.getCallback() + "?userInfo=" + resultStr + "&md5signature="
                + MD5Encrypt.hashing(resultStr);

        return redirectUrl;
    }

    private void logAuthHistory(AuthenticationHistory ah, GatewayResponse ret) {
        ah.setServiceType(ServiceType.WEIBO);
        if (ret.getRetCode() != 0) {
            ah.setErrorCode(ret.getErrorCode());
            ah.setErrorDesc(ret.getErrorDesc());
            ah.setServiceStatus(ServiceStatus.FAILURE);
            ah.setErrorType(ErrorType.THIRDPARTY);
        } else {
            ah.setServiceStatus(ServiceStatus.SUCCESS);
        }
        ah.setRawResponse(ret.getRawResponse());

        queueService.put(new QueuableTask() {
            @Override
            public void execute() {
                authenticationHistoryRepository.save(ah);
            }
        });
    }

    private HttpServletResponse getHttpResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
}
