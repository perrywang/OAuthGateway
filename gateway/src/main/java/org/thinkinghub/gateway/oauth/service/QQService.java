package org.thinkinghub.gateway.oauth.service;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.QQApi;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.config.QQConfig;
import org.thinkinghub.gateway.oauth.event.AccessTokenRetrievedEvent;
import org.thinkinghub.gateway.oauth.util.JsonUtil;
import org.thinkinghub.gateway.oauth.registry.EventPublisherRegistry;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@Slf4j
public class QQService extends AbstractOAuthService {
    private final String GET_OPEN_ID_URL = "https://graph.qq.com/oauth2.0/me";

    @Autowired
    private QQConfig qqConfig;

    @Autowired
    ResultHandlingService resultHandlingService;

    @PostConstruct
    public void initialize() {
        log.info(qqConfig.toString());
    }

    protected OAuth20Service getOAuthService(String state) {
        OAuth20Service service = new ServiceBuilder().apiKey(qqConfig.getApiKey()).apiSecret(qqConfig.getApiSecret())
                .callback(qqConfig.getCallback()).state(state).build(QQApi.instance());
        return service;
    }

    @Override
    String getUserInfoUrl() {
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
    public Response getResponse(String state, String code) {
        OAuth20Service service = getOAuthService(state);
        OAuth2AccessToken accessToken = getAccessToken(state, code);
        checkToken(accessToken);
        String openId = getOpenId(accessToken, service);
        EventPublisherRegistry.instance().getEventPublisher().publishEvent(new AccessTokenRetrievedEvent(state, accessToken));

        // send request to get user info
        final OAuthRequest request = new OAuthRequest(Verb.GET,
                getUserInfoUrl() + "?oauth_consumer_key=" + qqConfig.getApiKey() + "&openid=" + openId, service);
        service.signRequest(accessToken, request);
        Response response = request.send();

        return response;
    }
}
