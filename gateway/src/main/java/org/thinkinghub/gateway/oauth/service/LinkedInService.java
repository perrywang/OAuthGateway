package org.thinkinghub.gateway.oauth.service;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.LinkedInApi;
import org.thinkinghub.gateway.oauth.config.LinkedInConfig;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.exception.LinkedInOAuthCancelException;
import org.thinkinghub.gateway.oauth.exception.OAuthProcessingException;
import org.thinkinghub.gateway.oauth.registry.ExtractorRegistry;
import org.thinkinghub.gateway.oauth.response.ErrorResponse;
import org.thinkinghub.gateway.oauth.response.GatewayResponse;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class LinkedInService extends AbstractOAuthService {
    @Autowired
    private LinkedInConfig linkedInConfig;

    @PostConstruct
    public void initialize() {
        log.info(linkedInConfig.toString());
    }

    @Override
    protected OAuth20Service getOAuthServiceProvider(String state) {
        OAuth20Service service = new ServiceBuilder().apiKey(linkedInConfig.getApiKey())
                .apiSecret(linkedInConfig.getApiSecret()).callback(linkedInConfig.getCallback())
                .state(state).scope(linkedInConfig.getScope()).build(LinkedInApi.instance());
        return service;
    }

    @Override
    protected String getUserInfoUrl() {
        return "https://api.linkedin.com/v1/people/~?format=json";
    }

    @Override
    protected GatewayResponse parseUserInfoResponse(Response response) {
        return ExtractorRegistry.getExtractor(ServiceType.LINKEDIN).extract(response);
    }

    @Override
    public ServiceType supportedOAuthType() {
        return ServiceType.LINKEDIN;
    }

    public void handleOAuthError(ErrorResponse er, String state){
        if(er.getOauthErrorCode().equals("user_cancelled_login")
                || er.getOauthErrorCode().equals("user_cancelled_authorize ")){
            throw new LinkedInOAuthCancelException(er.getOauthErrorCode(), er.getOauthErrorMessage());
        }
        super.handleOAuthError(er, state);
    }
}
