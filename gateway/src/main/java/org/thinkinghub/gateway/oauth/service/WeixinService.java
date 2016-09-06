package org.thinkinghub.gateway.oauth.service;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.WeixinApi;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.oauth.bean.WeixinProperties;

import java.io.IOException;

@Service
public class WeixinService {
    @Autowired
    WeixinProperties weixinConfig;

    public WeixinService() {
    }

    private OAuth20Service getOAuthService(String state) {
        OAuth20Service service = new ServiceBuilder()
                .apiKey(weixinConfig.getApiKey())
                .apiSecret(weixinConfig.getApiSecret())
                .callback(weixinConfig.getCallback())
                .state(state)
                .build(WeixinApi.instance());
        return service;
    }

    public String getAuthorizationUrl(String state) {
        final String authorizationUrl = getOAuthService(state).getAuthorizationUrl();
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
