package org.thinkinghub.gateway.oauth.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.QQApi;
import org.thinkinghub.gateway.oauth.config.QQConfiguration;
import org.thinkinghub.gateway.oauth.event.AccessTokenRetrievedEvent;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QQService {
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @Autowired
    private QQConfiguration qqConfig;

    @PostConstruct
    public void initilize() {
        log.info(qqConfig.toString());
    }

    public String getAuthorizationUrl(String state) {

        final String authorizationUrl = getOAuthService(state).getAuthorizationUrl();
        return authorizationUrl;
    }

    private OAuth20Service getOAuthService(String state) {
        OAuth20Service service = new ServiceBuilder().apiKey(qqConfig.getApiKey()).apiSecret(qqConfig.getApiSecret())
                .callback(qqConfig.getCallback()).state(state).build(QQApi.instance());
        return service;
    }

    public void getAccessToken(String custCallback, String state, String code) {
        try {
            final OAuth2AccessToken accessToken = getOAuthService(state).getAccessToken(code);
            eventPublisher.publishEvent(new AccessTokenRetrievedEvent(state, accessToken));
        } catch (IOException e) {

        }
    }

    public String getResult(String state, String code) {
        OAuth2AccessToken accessToken = null;
        try {
            accessToken = getOAuthService(state).getAccessToken(code);
        } catch (IOException e) {

        } finally {

        }
        String accessTokenStr = accessToken.getRawResponse();
        return accessTokenStr;
    }
}
