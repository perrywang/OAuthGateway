package org.thinkinghub.gateway.core.service;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.AbstractRequest;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.oauth.OAuth20Service;

public class WeixinOAuthService extends OAuth20Service {
    public WeixinOAuthService(DefaultApi20 api, OAuthConfig config) {
        super(api, config);
    }

    protected <T extends AbstractRequest> T createAccessTokenRequest(String code, T request) {
        final OAuthConfig config = getConfig();
        request.addParameter("appid", config.getApiKey());
        request.addParameter("secret", config.getApiSecret());
        request.addParameter(OAuthConstants.CODE, code);
        request.addParameter(OAuthConstants.GRANT_TYPE, OAuthConstants.AUTHORIZATION_CODE);
        return request;
    }

    protected <T extends AbstractRequest> T createRefreshTokenRequest(String refreshToken, T request) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("The refreshToken cannot be null or empty");
        }
        final OAuthConfig config = getConfig();
        request.addParameter("appid", config.getApiKey());
        request.addParameter("secret", config.getApiSecret());
        request.addParameter(OAuthConstants.REFRESH_TOKEN, refreshToken);
        request.addParameter(OAuthConstants.GRANT_TYPE, OAuthConstants.REFRESH_TOKEN);
        return request;
    }

}
