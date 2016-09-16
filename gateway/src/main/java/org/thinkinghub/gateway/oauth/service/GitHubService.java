package org.thinkinghub.gateway.oauth.service;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.GitHubApi;
import org.thinkinghub.gateway.api.WeiboApi;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.config.GitHubConfiguration;
import org.thinkinghub.gateway.oauth.config.WeixinConfiguration;
import org.thinkinghub.gateway.oauth.event.AccessTokenRetrievedEvent;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class GitHubService extends AbstractOAuthService {
    private final String GET_USER_INFO_URL = "https://api.github.com/user";

    @Autowired
    private GitHubConfiguration gitHubConfig;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostConstruct
    public void initilize() {
        log.info(gitHubConfig.toString());
    }

    @Override
    protected OAuth20Service getOAuthService(String state) {
        OAuth20Service service = new ServiceBuilder().apiKey(gitHubConfig.getApiKey())
                .apiSecret(gitHubConfig.getApiSecret()).callback(gitHubConfig.getCallback())
                .state(state).scope(gitHubConfig.getScope())
                .build(GitHubApi.instance());
        return service;
    }

    @Override
    public Response getResponse(String state, String code) {
        OAuth20Service service = getOAuthService(state);
        GatewayAccessToken accessToken = getAccessToken(state, code);
        eventPublisher.publishEvent(new AccessTokenRetrievedEvent(state, accessToken));
        // send request to get user info
        final OAuthRequest request = new OAuthRequest(Verb.GET, GET_USER_INFO_URL + "?scope=user", service);

        service.signRequest(accessToken, request);
        Response response = request.send();
        return response;
    }

    @Override
    public RetBean getRetBean() {
        return null;
    }
}
