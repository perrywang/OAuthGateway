package org.thinkinghub.gateway.api;

import org.thinkinghub.gateway.core.extractors.LinkedInOAuth2AccessTokenExtractor;
import org.thinkinghub.gateway.core.service.LinkedInOAuthService;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.oauth.OAuth20Service;

public class LinkedInApi extends DefaultApi20{
    protected LinkedInApi() {
    }

    private static class InstanceHolder {
        private static final LinkedInApi INSTANCE = new LinkedInApi();
    }

    public static LinkedInApi instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://www.linkedin.com/oauth/v2/accessToken";
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://www.linkedin.com/oauth/v2/authorization";
    }

    @Override
    public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
        return LinkedInOAuth2AccessTokenExtractor.instance();
    }

    @Override
    public OAuth20Service createService(OAuthConfig config) {
        return new LinkedInOAuthService(this, config);
    }
}
