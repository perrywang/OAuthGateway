package org.thinkinghub.gateway.oauth.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.GitHubApi;
import org.thinkinghub.gateway.oauth.bean.GatewayResponse;
import org.thinkinghub.gateway.oauth.config.GitHubConfig;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.registry.ExtractorRegistry;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.oauth.OAuth20Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GitHubService extends AbstractOAuthService {
    @Autowired
    private GitHubConfig gitHubConfig;

    @PostConstruct
    public void initilize() {
        log.info(gitHubConfig.toString());
    }

    @Override
    protected OAuth20Service getOAuthServiceProvider(String state) {
        OAuth20Service service = new ServiceBuilder().apiKey(gitHubConfig.getApiKey())
                .apiSecret(gitHubConfig.getApiSecret()).callback(gitHubConfig.getCallback())
                .state(state).scope(gitHubConfig.getScope())
                .build(GitHubApi.instance());
        return service;
    }

    @Override
    protected String getUserInfoUrl() {
        return "https://api.github.com/user";
    }

    @Override
    protected GatewayResponse parseUserInfoResponse(Response response) {
        return ExtractorRegistry.getExtractor(ServiceType.GITHUB).extract(response);
    }

    @Override
    public ServiceType supportedOAuthType() {
        return ServiceType.GITHUB;
    }
}
