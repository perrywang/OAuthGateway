package org.thinkinghub.gateway.oauth.controller;

import com.github.scribejava.core.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.*;
import org.thinkinghub.gateway.oauth.event.StartingRetriveAccessTokenEvent;
import org.thinkinghub.gateway.oauth.exception.UserNotFoundException;
import org.thinkinghub.gateway.oauth.helper.JsonHelper;
import org.thinkinghub.gateway.oauth.queue.QueuableTask;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;
import org.thinkinghub.gateway.oauth.repository.UserRepository;
import org.thinkinghub.gateway.oauth.service.AbstractOAuthService;
import org.thinkinghub.gateway.oauth.service.QueueService;
import org.thinkinghub.gateway.oauth.service.ResultHandlingService;
import org.thinkinghub.gateway.oauth.service.ServiceRegistry;
import org.thinkinghub.gateway.oauth.util.Base64Encoder;
import org.thinkinghub.gateway.oauth.util.MD5Encrypt;
import org.thinkinghub.gateway.util.IDGenerator;

import javax.servlet.http.HttpServletRequest;
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
    public void route(@RequestParam(value = "callbackUrl", required = true) String callbackUrl,
                      @RequestParam(value = "key", required = true) String key,
                      @RequestParam(value = "service", required = true) ServiceType service, HttpServletResponse response,
                      HttpServletRequest request) {
        User user = userRepository.findByKey(key);
        if (user != null) {
            String state = Long.toString(IDGenerator.nextId());
            eventPublisher.publishEvent(new StartingRetriveAccessTokenEvent(user, state, callbackUrl));
            AbstractOAuthService oauthService = ServiceRegistry.instance().getService(service);

            login(state, response, oauthService);
        } else {
            throw new UserNotFoundException(String.format("can not find valid user relating with key: %s", key));
        }
    }

    private void login(String state, HttpServletResponse response, AbstractOAuthService oauthService) {
        String returnURL = oauthService.getAuthorizationUrl(state);
        try {
            response.sendRedirect(returnURL);
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/oauth/sina", method = RequestMethod.GET)
    public void requestWeiboAccessToken(HttpServletRequest request, HttpServletResponse response, @RequestParam("code") String code,
                                        @RequestParam("state") String state) {
        AbstractOAuthService weiboService = ServiceRegistry.instance().getService(ServiceType.WEIBO);
        Response userInfoResponse = weiboService.getResponse(state, code);
        String redirectUrl = handleResponse(request, userInfoResponse, ServiceType.WEIBO, state);

        redirect(response, redirectUrl);
    }

    @RequestMapping(value = "/oauth/qq", method = RequestMethod.GET)
    public void requestQQAccessToken(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam(value = "code", required = true) String code,
                                     @RequestParam(value = "state", required = true) String state) {
        AbstractOAuthService QQService = ServiceRegistry.instance().getService(ServiceType.QQ);
        Response userInfoResponse = QQService.getResponse(state, code);
        String redirectUrl = handleResponse(request, userInfoResponse, ServiceType.QQ, state);

        redirect(response, redirectUrl);
    }

    @RequestMapping(value = "/oauth/wechat", method = RequestMethod.GET)
    public void requestWeixinAccessToken(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam("code") String code, @RequestParam("state") String state) {
        AbstractOAuthService weixinService = ServiceRegistry.instance().getService(ServiceType.WECHAT);
        Response userInfoResponse = weixinService.getResponse(state, code);
        String redirectUrl = handleResponse(request, userInfoResponse, ServiceType.WECHAT, state);

        redirect(response, redirectUrl);
    }

    public void redirect(HttpServletResponse response, String redirectUrl) {
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }
    }

    private String handleResponse(HttpServletRequest request, Response response, ServiceType type, String state) {
        AuthenticationHistory ah = authenticationHistoryRepository.findByState(state);
        RetBean retBean = resultHandlingService.getRetBean(response, type);
        try {
            logAuthHistory(ah, retBean);
        } catch (Exception e) {
            log.error("Exception happens when log an authentication history record", e);
            //here failed to write the record in database, but may try n times as the failure may only happen at that time,
            //but succeed this time.
        }

        String resultStr = Base64Encoder.encode(JsonHelper.toJson(retBean));
        String redirectUrl = request.getScheme() + "://" + ah.getCallback() + "?userInfo=" + resultStr + "&md5signature="
                + getMD5Signature(resultStr);

        return redirectUrl;
    }

    private void logAuthHistory(AuthenticationHistory ah, RetBean ret) {
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

    private String getMD5Signature(String str) {
        return MD5Encrypt.hashing(str);
    }

}
