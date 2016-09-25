package org.thinkinghub.gateway.oauth.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.FacebookApi;
import org.thinkinghub.gateway.oauth.response.GatewayResponse;
import org.thinkinghub.gateway.oauth.config.FacebookConfig;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.registry.ExtractorRegistry;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.oauth.OAuth20Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FacebookService extends AbstractOAuthService {
    @Autowired
    private FacebookConfig facebookConfig;

    @PostConstruct
    public void initialize() {
        log.info(facebookConfig.toString());
    }

    @Override
    protected OAuth20Service getOAuthServiceProvider(String state) {
        OAuth20Service service = new ServiceBuilder().apiKey(facebookConfig.getApiKey())
                .apiSecret(facebookConfig.getApiSecret()).callback(facebookConfig.getCallback())
                .state(state).scope(facebookConfig.getScope()).responseType(facebookConfig.getResponseType())
                .build(FacebookApi.instance());
        return service;
    }
    
    @Override
    protected String getUserInfoUrl() {
        return "https://graph.facebook.com/" + facebookConfig.getApiVersion() + "/me";
    }

    @Override
    protected GatewayResponse parseUserInfoResponse(Response response) {
        return ExtractorRegistry.getExtractor(ServiceType.FACEBOOK).extract(response);
    }

    @Override
    public ServiceType supportedOAuthType() {
        return ServiceType.FACEBOOK;
    }
}
