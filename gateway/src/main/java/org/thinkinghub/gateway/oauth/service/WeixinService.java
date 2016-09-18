package org.thinkinghub.gateway.oauth.service;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.utils.OAuthEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.WeixinApi;
import org.thinkinghub.gateway.oauth.config.WeixinConfig;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WeixinService extends AbstractOAuthService {
    @Autowired
    private WeixinConfig weixinConfig;

    @PostConstruct
    public void initialize() {
        log.info(weixinConfig.toString());
    }

    protected OAuth20Service getOAuthService(String state) {
        OAuth20Service service = new ServiceBuilder().apiKey(weixinConfig.getApiKey())
                .apiSecret(weixinConfig.getApiSecret()).callback(OAuthEncoder.encode(weixinConfig.getCallback()))
                .scope(weixinConfig.getScope()).state(state).build(WeixinApi.instance());
        return service;
    }

    @Override
    public String getAuthorizationUrl(String state) {
        Map<String, String> additionalParams = new HashMap<>();
        additionalParams.put("response_type", weixinConfig.getResponseType());
        final String authorizationUrl = getOAuthService(state).getAuthorizationUrl(additionalParams);
        return authorizationUrl;
    }

    @Override
    String getUserInfoUrl() {
        return "https://api.weixin.qq.com/sns/userinfo";
    }

    @Override
    String getAppendedUrl() {
        return "?openid=" + getAccessToken().getUserId();
    }
}
