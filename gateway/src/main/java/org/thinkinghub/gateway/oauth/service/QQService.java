package org.thinkinghub.gateway.oauth.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.QQApi;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.oauth.response.GatewayResponse;
import org.thinkinghub.gateway.oauth.config.QQConfig;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.event.AccessTokenRetrievedEvent;
import org.thinkinghub.gateway.oauth.registry.EventPublisher;
import org.thinkinghub.gateway.oauth.registry.ExtractorRegistry;
import org.thinkinghub.gateway.oauth.util.JsonUtil;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QQService extends AbstractOAuthService {
    private final String GET_OPEN_ID_URL = "https://graph.qq.com/oauth2.0/me";

    @Autowired
    private QQConfig qqConfig;

    @PostConstruct
    public void initialize() {
        log.info(qqConfig.toString());
    }

    protected OAuth20Service getOAuthServiceProvider(String state) {
        OAuth20Service service = new ServiceBuilder().apiKey(qqConfig.getApiKey()).apiSecret(qqConfig.getApiSecret())
                .callback(qqConfig.getCallback()).state(state).build(QQApi.instance());
        return service;
    }

    @Override
    protected String getUserInfoUrl() {
        return "https://graph.qq.com/user/get_user_info";
    }

    public String getOpenId(OAuth2AccessToken accessToken, OAuth20Service service) {
        final OAuthRequest request = new OAuthRequest(Verb.GET, GET_OPEN_ID_URL, service);
        service.signRequest(accessToken, request);
        Response response = request.send();
        String rawResponse = null;
        try {
            rawResponse = response.getBody();
        } catch (IOException e) {

        }
        String formattedStr = rawResponse.substring(rawResponse.indexOf("{"), rawResponse.indexOf("}") + 1);
        return JsonUtil.getValue("openid", formattedStr);
    }

    @Override
    public GatewayResponse authenticated(String state, String code) {
        OAuth20Service service = getOAuthServiceProvider(state);
        GatewayAccessToken accessToken = getAccessToken(state, code);
        checkToken(accessToken);
        String openId = getOpenId(accessToken, service);
        EventPublisher.instance().publishEvent(new AccessTokenRetrievedEvent(state, accessToken));
        final OAuthRequest request = new OAuthRequest(Verb.GET,
                getUserInfoUrl() + "?oauth_consumer_key=" + qqConfig.getApiKey() + "&openid=" + openId, service);
        service.signRequest(accessToken, request);
        Response response = request.send();
        return parseUserInfoResponse(response);
    }

    @Override
    protected GatewayResponse parseUserInfoResponse(Response response) {
        return ExtractorRegistry.getExtractor(ServiceType.QQ).extract(response);
    }

    @Override
    public ServiceType supportedOAuthType() {
        return ServiceType.QQ;
    }
}
