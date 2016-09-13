package org.thinkinghub.gateway.oauth.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;
import org.thinkinghub.gateway.oauth.entity.ServiceStatus;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.entity.User;
import org.thinkinghub.gateway.oauth.event.StartingRetriveAccessTokenEvent;
import org.thinkinghub.gateway.oauth.exception.UserNotFoundException;
import org.thinkinghub.gateway.oauth.queue.AuthHistoryQueueTask;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;
import org.thinkinghub.gateway.oauth.repository.UserRepository;
import org.thinkinghub.gateway.oauth.service.AbstractOAuthService;
import org.thinkinghub.gateway.oauth.service.QueueService;
import org.thinkinghub.gateway.oauth.service.ServiceRegistry;
import org.thinkinghub.gateway.oauth.util.MD5Encrypt;
import org.thinkinghub.gateway.util.IDGenerator;

@RestController
public class GatewayController {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private UserRepository userRepository;
    @Autowired
    private QueueService queueService;

	@Autowired
	private AuthenticationHistoryRepository authenticationHistoryRepository;

	@RequestMapping(value = "/oauthgateway", method = RequestMethod.GET)
	public void route(@RequestParam(value = "callbackUrl", required = true) String callbackUrl,
			@RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "service", required = true) ServiceType service, HttpServletResponse response,
			HttpServletRequest request) {
		User user = userRepository.findByKey(key);
		String state = Long.toString(IDGenerator.nextId());
		if (user != null) {
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
			e.printStackTrace();
		}
	}

    @RequestMapping(value = "/oauth/sina", method = RequestMethod.GET)
    public void requestWeiboAccessToken(HttpServletRequest request, HttpServletResponse response, @RequestParam("code") String code,
            @RequestParam("state") String state) {
    	AbstractOAuthService weiboService = ServiceRegistry.instance().getService(ServiceType.WEIBO);
    	String resultStr = weiboService.getUserInfo(state, code);
    	AuthenticationHistory ah = authenticationHistoryRepository.findByState(state);

        //TODO here needs to add all required data in ah
        queueService.put(new AuthHistoryQueueTask(ah){
            @Override
            public void execute() {
                authenticationHistoryRepository.save(ah);
            }
        });
		String custCallbackUrl = ah.getCallback();
		String redirectUrl = request.getScheme() + "://" + custCallbackUrl + "?userInfo=" + resultStr + "&md5signature="
				+ getMD5Signature(resultStr);
		redirect(response, redirectUrl);
    }

	@RequestMapping(value = "/oauth/qq", method = RequestMethod.GET)
	public void requestQQAccessToken(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "state", required = true) String state) {
		AbstractOAuthService oauthService = ServiceRegistry.instance().getService(ServiceType.QQ);
		AuthenticationHistory ah = authenticationHistoryRepository.findByState(state);
		
		String custCallbackUrl = ah.getCallback();
		String resultStr = oauthService.getUserInfo(state, code);
		ah.setServiceStatus(ServiceStatus.SUCCESS);
		// Save the ServiceStatus to "SUCCESS"
		authenticationHistoryRepository.save(ah);
		String redirectUrl = request.getScheme() + "://" + custCallbackUrl + "?userInfo=" + resultStr + "&md5signature="
				+ getMD5Signature(resultStr);
		redirect(response, redirectUrl);
	}

	@RequestMapping(value = "/oauth/wechat", method = RequestMethod.GET)
	public void requestWeixinAccessToken(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("code") String code, @RequestParam("state") String state) {
		AbstractOAuthService oauthService = ServiceRegistry.instance().getService(ServiceType.WECHAT);
		AuthenticationHistory ah = authenticationHistoryRepository.findByState(state);
		String custCallbackUrl = ah.getCallback();
		String token = oauthService.getUserInfo(state, code);
		ah.setServiceStatus(ServiceStatus.SUCCESS);
		// Save the ServiceStatus to "SUCCESS"
		authenticationHistoryRepository.save(ah);
		String redirectUrl = request.getScheme() + "://" + custCallbackUrl + "?uid=" + token;
		redirect(response, redirectUrl);
	}
	
	public void redirect(HttpServletResponse response, String redirectUrl){
		try {
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getMD5Signature(String str) {
		return MD5Encrypt.hashing(str);
	}
}
