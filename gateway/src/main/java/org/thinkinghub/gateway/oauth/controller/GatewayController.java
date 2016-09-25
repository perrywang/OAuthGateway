package org.thinkinghub.gateway.oauth.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thinkinghub.gateway.oauth.response.GatewayResponse;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.entity.User;
import org.thinkinghub.gateway.oauth.exception.RedirectUrlException;
import org.thinkinghub.gateway.oauth.exception.GWUserNotFoundException;
import org.thinkinghub.gateway.oauth.registry.ServiceRegistry;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;
import org.thinkinghub.gateway.oauth.repository.UserRepository;
import org.thinkinghub.gateway.oauth.service.OAuthService;
import org.thinkinghub.gateway.oauth.util.Base64Encoder;
import org.thinkinghub.gateway.oauth.util.JsonUtil;
import org.thinkinghub.gateway.oauth.util.MD5Encrypt;

@RestController
public class GatewayController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationHistoryRepository authenticationHistoryRepository;

    @RequestMapping(value = "/oauthgateway", method = RequestMethod.GET)
    public void route(@RequestParam(value = "callbackUrl") String callbackUrl,
                      @RequestParam(value = "key") String key,
                      @RequestParam(value = "service") String service) {
        User user = userRepository.findByKey(key);
        if (user != null) {
            OAuthService oauthService = ServiceRegistry.getService(ServiceType.valueOf(service.toUpperCase()));
            oauthService.authenticate(user, callbackUrl);
        } else {
            throw new GWUserNotFoundException();
        }
    }

    @RequestMapping(value = "/oauth/{service}", method = RequestMethod.GET)
    public void oauthProviderCallback(HttpServletResponse response, @RequestParam("code") String code,
                                      @RequestParam("state") String state,
                                      @PathVariable String service) {
        ServiceType serviceType = ServiceType.valueOf(service.toUpperCase());
        OAuthService oauthService = ServiceRegistry.getService(serviceType);
        GatewayResponse gatewayResponse = oauthService.authenticated(code, state);
        String matchedCallback = authenticationHistoryRepository.findByState(state).getCallback();
        try {
            response.sendRedirect(generateRedirectUrl(gatewayResponse, matchedCallback));
        } catch (IOException e) {
            throw new RedirectUrlException(e);
        }
    }

    private String generateRedirectUrl(GatewayResponse response, String callback) {
        String resultStr = Base64Encoder.encode(JsonUtil.toJson(response));
        String redirectUrl = callback + "?userInfo=" + resultStr + "&md5signature="
                + MD5Encrypt.hashing(resultStr);
        return redirectUrl;
    }

}
