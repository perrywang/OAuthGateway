package org.thinkinghub.gateway.oauth.service;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.GitHubApi;
import org.thinkinghub.gateway.oauth.config.GitHubConfig;

import javax.annotation.PostConstruct;

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
    protected OAuth20Service getOAuthService(String state) {
        OAuth20Service service = new ServiceBuilder().apiKey(gitHubConfig.getApiKey())
                .apiSecret(gitHubConfig.getApiSecret()).callback(gitHubConfig.getCallback())
                .state(state).scope(gitHubConfig.getScope())
                .build(GitHubApi.instance());
        return service;
    }

    @Override
    String getUserInfoUrl() {
        return "https://api.github.com/user";
    }
}
