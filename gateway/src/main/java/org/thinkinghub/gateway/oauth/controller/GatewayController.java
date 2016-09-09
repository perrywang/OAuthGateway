package org.thinkinghub.gateway.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;
import org.thinkinghub.gateway.oauth.entity.ServiceStatus;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.entity.User;
import org.thinkinghub.gateway.oauth.service.CacheServiceDBImpl;
import org.thinkinghub.gateway.oauth.service.QQService;
import org.thinkinghub.gateway.oauth.service.WeiboService;
import org.thinkinghub.gateway.oauth.service.WeixinService;
import org.thinkinghub.gateway.util.IDGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class GatewayController {
    @Autowired
    QQService qqService;

    @Autowired
    WeiboService weiboService;
    
    @Autowired
    WeixinService weixinService;
    
    @Autowired
    CacheServiceDBImpl dataService;

    @RequestMapping(value = "/oauthgateway", method = RequestMethod.GET)
    public void route(@RequestParam(value="callbackUrl",required=true) String callbackUrl,
    							 @RequestParam(value="key",required=true) String key,
    							 @RequestParam(value="service",required=true) ServiceType service,
    							 HttpServletResponse response, HttpServletRequest request) {
		User user = dataService.getUser(key);
		if (user == null) {
//			throw new Exception("Your key"+ key + "is not correct");
		} else {
			switch (service) {
			case WEIBO:
				weiboLogin(callbackUrl, response, user, service, request);
				break;
			case QQ:
				qqLogin(callbackUrl, response, user, service);
				break;
			case WECHAT:
				weixinLogin(callbackUrl, response, user, service);
				break;
			}
		}
	}

    private Map<String, String> map = new ConcurrentHashMap<String, String>();


    private void weiboLogin(String custCallbackUrl, HttpServletResponse response, User user, ServiceType service, HttpServletRequest request) {
    	String state = Long.toString(IDGenerator.nextId());
    	AuthenticationHistory ah = new AuthenticationHistory(user, service, custCallbackUrl, state, ServiceStatus.INPROGRESS, null, null, null);
        dataService.saveAuthHistory(ah);
        String returnURL = weiboService.getAuthorizationUrl(state);
        try {
            response.sendRedirect(returnURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void qqLogin(String custCallbackUrl, HttpServletResponse response, User user, ServiceType service) {
        String state = Long.toString(IDGenerator.nextId());
        AuthenticationHistory ah = new AuthenticationHistory(user, service, custCallbackUrl, state, ServiceStatus.INPROGRESS, null, null, null);
        dataService.saveAuthHistory(ah);
        String returnURL = qqService.getAuthorizationUrl(state);
        try {
            response.sendRedirect(returnURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void weixinLogin(String custCallbackUrl, HttpServletResponse response, User user, ServiceType service){
    	String state = Long.toString(IDGenerator.nextId());
    	AuthenticationHistory ah = new AuthenticationHistory(user, service, custCallbackUrl, state, ServiceStatus.INPROGRESS, null, null, null);
        dataService.saveAuthHistory(ah);
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
    	AuthenticationHistory ah = dataService.getAuthHistory(state);
    	String custCallbackUrl = ah.getCallback();
    	GatewayAccessToken token = weiboService.getResult(state, code);
        ah.setServiceStatus(ServiceStatus.SUCCESS);
        //Save the ServiceStatus to "SUCCESS"
        dataService.saveAuthHistory(ah);
        String redirectUrl = request.getScheme() + "://" + custCallbackUrl + "?uid=" + token.getUserId();
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set(HttpHeaders.LOCATION, redirectUrl);
        return new ResponseEntity<String>("Success", responseHeader, HttpStatus.TEMPORARY_REDIRECT);
    }
    
    @RequestMapping(value = "/oauth/qq", method = RequestMethod.GET)
    public ResponseEntity<String> requestQQAccessToken(HttpServletRequest request,
    												   @RequestParam(value="code",required=true) String code,
    												   @RequestParam(value="state",required=true) String state) {
        AuthenticationHistory ah = dataService.getAuthHistory(state);
    	String custCallbackUrl = ah.getCallback();
        String tokenStr = qqService.getResult(state, code);
        ah.setServiceStatus(ServiceStatus.SUCCESS);
        //Save the ServiceStatus to "SUCCESS"
        dataService.saveAuthHistory(ah);
        String redirectUrl = request.getScheme() + "://" + custCallbackUrl + "?" + tokenStr;
        HttpHeaders responseHeader = new HttpHeaders();
        //TODO when call service to get the final Json, need to put openid in raw response as openid is not included in it.
        responseHeader.set(HttpHeaders.LOCATION, redirectUrl);
        return new ResponseEntity<String>("Success", responseHeader, HttpStatus.TEMPORARY_REDIRECT);
    }

    @RequestMapping(value = "/oauth/wechat", method = RequestMethod.GET)
    public ResponseEntity<String> requestWeixinAccessToken(HttpServletRequest request, @RequestParam("code") String code,
            @RequestParam("state") String state) {
    	AuthenticationHistory ah = dataService.getAuthHistory(state);
    	String custCallbackUrl = ah.getCallback();
    	GatewayAccessToken token = weixinService.getResult(state, code);
        ah.setServiceStatus(ServiceStatus.SUCCESS);
        //Save the ServiceStatus to "SUCCESS"
        dataService.saveAuthHistory(ah);
        String redirectUrl = request.getScheme() + "://" + custCallbackUrl + "?uid=" + token.getUserId();
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set(HttpHeaders.LOCATION, redirectUrl);
        return new ResponseEntity<String>("Success", responseHeader, HttpStatus.TEMPORARY_REDIRECT);
    }
}
