package org.thinkinghub.gateway.api;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import org.thinkinghub.gateway.core.extractors.GitHubOAuth2AccessTokenJsonExtractor;

public class GitHubApi extends DefaultApi20{
    @Override
    public String getAccessTokenEndpoint() {
        return "https://github.com/login/oauth/access_token";
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://github.com/login/oauth/authorize";
    }

    private static class InstanceHolder {
        static final GitHubApi INSTANCE = new GitHubApi();
    }

    public static GitHubApi instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
        return GitHubOAuth2AccessTokenJsonExtractor.instance();
    }
}
