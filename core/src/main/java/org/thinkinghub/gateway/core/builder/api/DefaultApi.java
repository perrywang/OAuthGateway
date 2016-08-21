package org.thinkinghub.gateway.core.builder.api;

import java.util.Map;

import org.thinkinghub.gateway.core.extractors.OAuthAccessTokenJsonExtractor;
import org.thinkinghub.gateway.core.extractors.TokenExtractor;
import org.thinkinghub.gateway.core.model.OAuthAccessToken;
import org.thinkinghub.gateway.core.model.OAuthConfig;
import org.thinkinghub.gateway.core.model.OAuthConstants;
import org.thinkinghub.gateway.core.model.ParameterList;
import org.thinkinghub.gateway.core.model.Verb;
import org.thinkinghub.gateway.core.service.OAuthService;


public abstract class DefaultApi implements BaseApi<OAuthService>{
	
	protected abstract String getAuthorizationBaseUrl();
	
    public String getAuthorizationUrl(OAuthConfig config, Map<String, String> additionalParams) {
        final ParameterList parameters = new ParameterList(additionalParams);
        parameters.add(OAuthConstants.RESPONSE_TYPE, config.getResponseType());
        parameters.add(OAuthConstants.CLIENT_ID, config.getApiKey());

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
    
    public String getWeixinAuthorizationUrl(OAuthConfig config, Map<String, String> additionalParams){
        final ParameterList parameters = new ParameterList(additionalParams);
        parameters.add(OAuthConstants.RESPONSE_TYPE, config.getResponseType());
        parameters.add(OAuthConstants.APPID, config.getApiKey());


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
    public OAuthService createService(OAuthConfig config) {
        return new OAuthService(this, config);
    }
    
    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }
    
    public abstract String getAccessTokenEndpoint();
    
    public TokenExtractor<OAuthAccessToken> getAccessTokenExtractor() {
        return OAuthAccessTokenJsonExtractor.instance();
    }
}
