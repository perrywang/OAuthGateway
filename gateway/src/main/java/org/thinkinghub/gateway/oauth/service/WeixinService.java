package org.thinkinghub.gateway.oauth.service;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.utils.OAuthEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.WeixinApi;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.oauth.config.WeixinConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeixinService {
    @Autowired
    private WeixinConfiguration weixinConfig;

    private OAuth20Service getOAuthService(String state) {
        OAuth20Service service = new ServiceBuilder()
                .apiKey(weixinConfig.getApiKey())
                .apiSecret(weixinConfig.getApiSecret())
                .callback(OAuthEncoder.encode(weixinConfig.getCallback()))
                .scope(weixinConfig.getScope())
                .state(state)
                .build(WeixinApi.instance());
        return service;
    }

    public String getAuthorizationUrl(String state) {
        Map<String, String> additionalParams = new HashMap<>();
        additionalParams.put("response_type", weixinConfig.getResponseType());
        final String authorizationUrl = getOAuthService(state).getAuthorizationUrl(additionalParams);

        return authorizationUrl;
    }

    public GatewayAccessToken getResult(String state, String code) {
        GatewayAccessToken accessToken = null;
        try {
            accessToken = (GatewayAccessToken)getOAuthService(state).getAccessToken(code);
        } catch (IOException e) {

        } finally {

        }

        return accessToken;
    }
}
