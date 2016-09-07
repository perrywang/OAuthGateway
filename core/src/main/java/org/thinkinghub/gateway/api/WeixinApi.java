package org.thinkinghub.gateway.api;

import java.util.Map;

import org.thinkinghub.gateway.core.extractors.WeixinOAuth2AccessTokenJsonExtractor;
import org.thinkinghub.gateway.core.service.WeixinOAuthService;
import org.thinkinghub.gateway.util.Constants;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.ParameterList;

public class WeixinApi extends DefaultApi20 {

    protected WeixinApi() {
    }

    private static class InstanceHolder {
        private static final WeixinApi INSTANCE = new WeixinApi();
    }

    public static WeixinApi instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
        return WeixinOAuth2AccessTokenJsonExtractor.instance();
    }

    /**
     * see getAuthorizationUrl to understand what parameters will be appended to the base Url.
     *
     * @return
     */
    @Override
    public String getAuthorizationBaseUrl() {
        return Constants.WEIXIN_AUTHORIZE_URL;
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config, Map<String, String> additionalParams) {
        final ParameterList parameters = new ParameterList(additionalParams);
        parameters.add(OAuthConstants.RESPONSE_TYPE, config.getResponseType());
        parameters.add(Constants.WEBXIN_API_KEY, config.getApiKey());

        final String callback = config.getCallback();
        if (callback != null) {
            parameters.add(OAuthConstants.REDIRECT_URI, callback);
        }

        final String scope = config.getScope();
        if (scope != null) {
            parameters.add(OAuthConstants.SCOPE, scope);
        }

        final String state = config.getState();
        if (state != null) {
            parameters.add(OAuthConstants.STATE, state);
        }

        return parameters.appendTo(getAuthorizationBaseUrl());
    }

    @Override
    public String getAccessTokenEndpoint() {
        return Constants.WEIXIN_ACCESS_TOKEN_URL;
    }

    @Override
    public WeixinOAuthService createService(OAuthConfig config) {
        return new WeixinOAuthService(this, config);
    }

}
