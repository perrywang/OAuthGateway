package org.thinkinghub.gateway.oauth.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thinkinghub.gateway.oauth.entity.ErrorType;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.exception.CallbackUrlNotFoundException;
import org.thinkinghub.gateway.oauth.exception.IncorrectUrlFormatException;
import org.thinkinghub.gateway.oauth.exception.RedirectUrlException;
import org.thinkinghub.gateway.oauth.exception.ServiceTypeNotFoundException;
import org.thinkinghub.gateway.oauth.exception.UserKeyNotFoundException;
import org.thinkinghub.gateway.oauth.registry.ServiceRegistry;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;
import org.thinkinghub.gateway.oauth.response.ErrorResponse;
import org.thinkinghub.gateway.oauth.response.GatewayResponse;
import org.thinkinghub.gateway.oauth.service.OAuthService;
import org.thinkinghub.gateway.oauth.util.Base64Encoder;
import org.thinkinghub.gateway.oauth.util.JsonUtil;
import org.thinkinghub.gateway.oauth.util.MD5Encrypt;
import org.thinkinghub.gateway.util.RegexUtil;

@RestController
public class GatewayController {

    @Autowired
    private AuthenticationHistoryRepository authenticationHistoryRepository;

    @RequestMapping(value = "/oauthgateway", method = RequestMethod.GET)
    public void route(@RequestParam(value = "callbackUrl", required = false) String callbackUrl,
                      @RequestParam(value = "key", required = false) String key,
                      @RequestParam(value = "service", required = false) String service,
                      HttpServletResponse response) {
        preCheckParam(callbackUrl, key, service);
        OAuthService oauthService = ServiceRegistry.getService(ServiceType.valueOf(service.toUpperCase()));
        String url = oauthService.authenticate(key, callbackUrl);
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new RedirectUrlException(e);
        }
    }

    private void preCheckParam(String callbackUrl, String key, String service) {
        if (StringUtils.isEmpty(callbackUrl)) {
            throw new CallbackUrlNotFoundException();
        }
        if (StringUtils.isEmpty(key)) {
            throw new UserKeyNotFoundException();
        }
        if (StringUtils.isEmpty(service)) {
            throw new ServiceTypeNotFoundException();
        }
        if (!RegexUtil.isProperUrl(callbackUrl)) {
            throw new IncorrectUrlFormatException();
        }
    }

    @RequestMapping(value = "/oauth/{service}", method = RequestMethod.GET)
    public void oauthProviderCallback(@PathVariable String service,
                                      HttpServletResponse response, HttpServletRequest request) {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String errorId = request.getParameter("error");
        ServiceType serviceType = ServiceType.valueOf(service.toUpperCase());
        OAuthService oauthService = ServiceRegistry.getService(serviceType);
        if(errorId != null){
        	String errorCode = request.getParameter("error_code");
        	String errorDescription = request.getParameter("error_description");
        	ErrorResponse err = new ErrorResponse(null,null,errorCode,errorDescription,ErrorType.THIRDPARTY,serviceType);
        	oauthService.errorWhenAuthentication(err,state);;
        }else{
            GatewayResponse gatewayResponse = oauthService.authenticated(code, state);
            String matchedCallback = authenticationHistoryRepository.findByState(state).getCallback();
            try {
                response.sendRedirect(generateRedirectUrl(gatewayResponse, matchedCallback));
            } catch (IOException e) {
                throw new RedirectUrlException(e);
            }
        }
    }

    private String generateRedirectUrl(GatewayResponse response, String callback) {
        String resultStr = Base64Encoder.encode(JsonUtil.toJson(response));
        String redirectUrl = callback + "?userInfo=" + resultStr + "&md5signature=" + MD5Encrypt.hashing(resultStr);
        return redirectUrl;
    }

}
