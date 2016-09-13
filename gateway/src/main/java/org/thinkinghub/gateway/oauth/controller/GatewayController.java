package org.thinkinghub.gateway.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.*;
import org.thinkinghub.gateway.oauth.event.AccessTokenRetrievedEvent;
import org.thinkinghub.gateway.oauth.event.StartingRetriveAccessTokenEvent;
import org.thinkinghub.gateway.oauth.exception.GatewayException;
import org.thinkinghub.gateway.oauth.exception.UserNotFoundException;
import org.thinkinghub.gateway.oauth.helper.JsonHelper;
import org.thinkinghub.gateway.oauth.queue.QueuableTask;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;
import org.thinkinghub.gateway.oauth.repository.UserRepository;
import org.thinkinghub.gateway.oauth.service.*;
import org.thinkinghub.gateway.util.IDGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
public class GatewayController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private QQService qqService;

    @Autowired
    private WeiboService weiboService;

    @Autowired
    private WeixinService weixinService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationHistoryRepository authenticationHistoryRepository;

    @Autowired
    private QueueService queueService;

    @Autowired
    private ResultHandlingService resultService;

    @RequestMapping(value = "/oauthgateway", method = RequestMethod.GET)
    public void route(@RequestParam(value = "callbackUrl", required = true) String callbackUrl,
                      @RequestParam(value = "key", required = true) String key,
                      @RequestParam(value = "service", required = true) ServiceType service,
                      HttpServletResponse response, HttpServletRequest request) {
        User user = userRepository.findByKey(key);
        String state = Long.toString(IDGenerator.nextId());
        if (user != null) {
            eventPublisher.publishEvent(new StartingRetriveAccessTokenEvent(user, state, callbackUrl));
            switch (service) {
                case WEIBO:
                    weiboLogin(state, response);
                    break;
                case QQ:
                    qqLogin(state, response);
                    break;
                case WECHAT:
                    weixinLogin(state, response);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        throw new UserNotFoundException(String.format("can not find valid user relating with key: %s", key));
    }


    private void weiboLogin(String state, HttpServletResponse response) {

        String returnURL = weiboService.getAuthorizationUrl(state);
        try {
            response.sendRedirect(returnURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void qqLogin(String state, HttpServletResponse response) {
        String returnURL = qqService.getAuthorizationUrl(state);
        try {
            response.sendRedirect(returnURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void weixinLogin(String state, HttpServletResponse response) {
        String returnURL = weixinService.getAuthorizationUrl(state);
        try {
            response.sendRedirect(returnURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/oauth/sina", method = RequestMethod.GET)
    public ResponseEntity<String> requestWeiboAccessToken(HttpServletRequest request, @RequestParam("code") String code,
                                                          @RequestParam("state") String state) {

        GatewayAccessToken token = weiboService.getResult(state, code);
        eventPublisher.publishEvent(new AccessTokenRetrievedEvent(state, token));

        //here need to add the request for user info, return value should be here
        String response = "";

        RetBean retBean = resultService.getRetBean(response, ServiceType.WEIBO);
        //TODO is it possible to get a null ah?
        AuthenticationHistory ah = authenticationHistoryRepository.findByState(state);
        String custCallbackUrl = ah.getCallback();

        logAuthHistory(ah, retBean);

        String retJson = handleResult(retBean);
        String redirectUrl = request.getScheme() + "://" + custCallbackUrl + "?ret=" + retJson;
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set(HttpHeaders.LOCATION, redirectUrl);

        return new ResponseEntity<String>("Success", responseHeader, HttpStatus.TEMPORARY_REDIRECT);
    }

    @RequestMapping(value = "/oauth/qq", method = RequestMethod.GET)
    public ResponseEntity<String> requestQQAccessToken(HttpServletRequest request,
                                                       @RequestParam(value = "code", required = true) String code,
                                                       @RequestParam(value = "state", required = true) String state) {
        AuthenticationHistory ah = authenticationHistoryRepository.findByState(state);
        String custCallbackUrl = ah.getCallback();
        String tokenStr = qqService.getResult(state, code);
        ah.setServiceStatus(ServiceStatus.SUCCESS);
        //Save the ServiceStatus to "SUCCESS"
        authenticationHistoryRepository.save(ah);
        String redirectUrl = request.getScheme() + "://" + custCallbackUrl + "?" + tokenStr;
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set(HttpHeaders.LOCATION, redirectUrl);
        return new ResponseEntity<String>("Success", responseHeader, HttpStatus.TEMPORARY_REDIRECT);
    }

    @RequestMapping(value = "/oauth/wechat", method = RequestMethod.GET)
    public ResponseEntity<String> requestWeixinAccessToken(HttpServletRequest request, @RequestParam("code") String code,
                                                           @RequestParam("state") String state) {
        AuthenticationHistory ah = authenticationHistoryRepository.findByState(state);
        String custCallbackUrl = ah.getCallback();
        GatewayAccessToken token = weixinService.getResult(state, code);
        ah.setServiceStatus(ServiceStatus.SUCCESS);
        //Save the ServiceStatus to "SUCCESS"
        authenticationHistoryRepository.save(ah);
        String redirectUrl = request.getScheme() + "://" + custCallbackUrl + "?uid=" + token.getUserId();
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set(HttpHeaders.LOCATION, redirectUrl);
        return new ResponseEntity<String>("Success", responseHeader, HttpStatus.TEMPORARY_REDIRECT);
    }

    private void logAuthHistory(AuthenticationHistory ah, RetBean ret){
        ah.setServiceType(ServiceType.WEIBO);
        if(ret.getRetCode() == 0){
            ah.setErrorCode(ret.getErrorCode());
            ah.setErrorDesc(ret.getErrorDesc());
            ah.setServiceStatus(ServiceStatus.FAILURE);
            ah.setErrorType(ErrorType.THIRDPARTY);
        }else{
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

    private String handleResult(RetBean bean) {
        String retJson = JsonHelper.toJson(bean);
        byte[] retJsonBytes;
        try {
            retJsonBytes = retJson.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new GatewayException("exception happens when converting string to bytes");
        }
        return new sun.misc.BASE64Encoder().encode(retJsonBytes);
    }
}
