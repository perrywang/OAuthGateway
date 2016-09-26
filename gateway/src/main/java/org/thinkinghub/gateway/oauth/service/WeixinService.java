package org.thinkinghub.gateway.oauth.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.WeixinApi;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.oauth.response.GatewayResponse;
import org.thinkinghub.gateway.oauth.config.WeixinConfig;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.registry.ExtractorRegistry;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.utils.OAuthEncoder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WeixinService extends AbstractOAuthService {
    @Autowired
    private WeixinConfig weixinConfig;

    @PostConstruct
    public void initialize() {
        log.info(weixinConfig.toString());
    }

    protected OAuth20Service getOAuthServiceProvider(String state) {
        OAuth20Service service = new ServiceBuilder().apiKey(weixinConfig.getApiKey())
                .apiSecret(weixinConfig.getApiSecret()).callback(OAuthEncoder.encode(weixinConfig.getCallback()))
                .scope(weixinConfig.getScope()).state(state).build(WeixinApi.instance());
        return service;
    }

    @Override
    public String getAuthorizationUrl(String state) {
        Map<String, String> additionalParams = new HashMap<>();
        additionalParams.put("response_type", weixinConfig.getResponseType());
        final String authorizationUrl = getOAuthServiceProvider(state).getAuthorizationUrl(additionalParams);
        return authorizationUrl;
    }

    @Override
    protected String getUserInfoUrl() {
        return "https://api.weixin.qq.com/sns/userinfo";
    }

    @Override
    protected String getAppendedUrl(GatewayAccessToken accessToken) {
        return "?openid=" + accessToken.getUserId();
    }

    @Override
    protected GatewayResponse parseUserInfoResponse(Response response) {
        return ExtractorRegistry.getExtractor(ServiceType.WECHAT).extract(response);
    }

    @Override
    public ServiceType supportedOAuthType() {
        return ServiceType.WECHAT;
    }
}
