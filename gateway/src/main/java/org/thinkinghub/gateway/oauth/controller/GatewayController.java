package org.thinkinghub.gateway.oauth.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thinkinghub.gateway.oauth.entity.ErrorType;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.entity.State;
import org.thinkinghub.gateway.oauth.exception.CallbackUrlNotFoundException;
import org.thinkinghub.gateway.oauth.exception.IncorrectUrlFormatException;
import org.thinkinghub.gateway.oauth.exception.RedirectUrlException;
import org.thinkinghub.gateway.oauth.exception.ServiceTypeNotFoundException;
import org.thinkinghub.gateway.oauth.exception.UserKeyNotFoundException;
import org.thinkinghub.gateway.oauth.registry.ServiceRegistry;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;
import org.thinkinghub.gateway.oauth.repository.StateRepository;
import org.thinkinghub.gateway.oauth.response.ErrorResponse;
import org.thinkinghub.gateway.oauth.response.GatewayResponse;
import org.thinkinghub.gateway.oauth.service.OAuthService;
import org.thinkinghub.gateway.oauth.util.Base64Encoder;
import org.thinkinghub.gateway.oauth.util.JsonUtil;
import org.thinkinghub.gateway.oauth.util.MD5Encrypt;
import org.thinkinghub.gateway.util.RegexUtil;

@Slf4j
@RestController
public class GatewayController {

    @Autowired
    private AuthenticationHistoryRepository authenticationHistoryRepository;

    @Autowired
    private StateRepository stateRepository;

    @RequestMapping(value = "/oauthgateway", method = RequestMethod.GET)
    public void route(@RequestParam(value = "callbackUrl", required = false) String callbackUrl,
                      @RequestParam(value = "key", required = false) String key,
                      @RequestParam(value = "service", required = false) String service,
                      HttpServletResponse response) {
        log.debug("callBackUrl is " + callbackUrl
                + ", user key is " + key
                + ", service is " + service);
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
        String state = request.getParameter("state");
        if (!checkState(state)) {
            log.error("No state or wrong state returned from " + service + ", this might be caused by external attack");
            return;
        }
        ServiceType serviceType = ServiceType.valueOf(service.toUpperCase());
        OAuthService oauthService = ServiceRegistry.getService(serviceType);
        String oauthError = request.getParameter("error");
        if (!StringUtils.isEmpty(oauthError)) {
            String errorCode = request.getParameter("error_code");
            String errorDesc = request.getParameter("error_description");
            ErrorResponse er = new ErrorResponse().oauthError(oauthError)
                    .oauthErrorCode(errorCode).oauthErrorMessage(errorDesc)
                    .errorType(ErrorType.THIRDPARTY).serviceType(serviceType);
            oauthService.handleOAuthError(er, state);
        } else {
            String code = request.getParameter("code");
            //code is missing not because any error is happening only.
            //For example, if user refuses to login Webxin, the code will not be returned.
            if (code == null || code.equals("")) {
                log.error("No code returned from " + service + ", state is " + state);
                return;
            }
            GatewayResponse gatewayResponse = oauthService.authenticated(code, state);
            String matchedCallback = authenticationHistoryRepository.findByState(state).getCallback();
            try {
                response.sendRedirect(generateRedirectUrl(gatewayResponse, matchedCallback));
            } catch (IOException e) {
                throw new RedirectUrlException(e);
            }
        }
    }

    private boolean checkState(String state) {
        State s = stateRepository.findByKey(state);
        return (s != null && s.getId() > 0) ? true : false;
    }

    private String generateRedirectUrl(GatewayResponse response, String callback) {
        String resultStr = Base64Encoder.encode(JsonUtil.toJson(response));
        String redirectUrl = callback + "?userInfo=" + resultStr + "&md5signature=" + MD5Encrypt.hashing(resultStr);
        return redirectUrl;
    }

}
