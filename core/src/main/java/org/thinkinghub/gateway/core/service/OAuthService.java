package org.thinkinghub.gateway.core.service;

import java.io.IOException;
import java.util.Map;

import org.thinkinghub.gateway.core.builder.api.DefaultApi;
import org.thinkinghub.gateway.core.model.OAuthAccessToken;
import org.thinkinghub.gateway.core.model.OAuthConfig;
import org.thinkinghub.gateway.core.model.OAuthConstants;
import org.thinkinghub.gateway.core.model.OAuthRequest;
import org.thinkinghub.gateway.core.model.Verb;


public class OAuthService {
	private final OAuthConfig config;
	private final DefaultApi api;
	
	public OAuthService(DefaultApi api, OAuthConfig config){
		this.config = config;
		this.api = api;
	}
	
	public OAuthConfig getConfig(){
		return this.config;
	}

    public final String getAuthorizationUrl() {
        return getAuthorizationUrl(null);
    }
    
    public final String getWeixinAuthorizationUrl(){
    	return getWeixinAuthorizationUrl(null);
    }
    /**
     * Returns the URL where you should redirect your users to authenticate your application.
     *
     * @param additionalParams any additional GET params to add to the URL
     * @return the URL where you should redirect your users
     */
    public String getAuthorizationUrl(Map<String, String> additionalParams) {
        return api.getAuthorizationUrl(getConfig(), additionalParams);
    }
    
    public String getWeixinAuthorizationUrl(Map<String, String> additionalParams) {
        return api.getWeixinAuthorizationUrl(getConfig(), additionalParams);
    }
    
    public OAuthAccessToken getAccessToken(String code) throws IOException{
    	Verb verb = api.getAccessTokenVerb();
    	String url = api.getAccessTokenEndpoint();
    	OAuthRequest request = new OAuthRequest(verb, url, this);
    	final OAuthRequest finalRequest = createAccessTokenRequest(code, request);
    	return sendAccessTokenRequestSync(finalRequest);
    }

    public OAuthAccessToken getWeixinAccessToken(String code) throws IOException{
    	Verb verb = api.getAccessTokenVerb();
    	String url = api.getAccessTokenEndpoint();
    	OAuthRequest request = new OAuthRequest(verb, url, this);
    	final OAuthRequest finalRequest = createWeixinAccessTokenRequest(code, request);
    	return sendAccessTokenRequestSync(finalRequest);
    }
    
    protected OAuthAccessToken sendAccessTokenRequestSync(OAuthRequest request) throws IOException {
        return api.getAccessTokenExtractor().extract(request.send().getBody());
    }
    
    protected OAuthRequest createAccessTokenRequest(String code, OAuthRequest request) {
        final OAuthConfig config = getConfig();
        request.addParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
        request.addParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
        request.addParameter(OAuthConstants.CODE, code);
        request.addParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
        final String scope = config.getScope();
        if (scope != null) {
            request.addParameter(OAuthConstants.SCOPE, scope);
        }
        request.addParameter(OAuthConstants.GRANT_TYPE, OAuthConstants.AUTHORIZATION_CODE);
        return request;
    }
    
    protected OAuthRequest createWeixinAccessTokenRequest(String code, OAuthRequest request) {
        final OAuthConfig config = getConfig();
        request.addParameter(OAuthConstants.APPID, config.getApiKey());
        request.addParameter(OAuthConstants.SECRET, config.getApiSecret());
        request.addParameter(OAuthConstants.CODE, code);
        request.addParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
        final String scope = config.getScope();
        if (scope != null) {
            request.addParameter(OAuthConstants.SCOPE, scope);
        }
        request.addParameter(OAuthConstants.GRANT_TYPE, OAuthConstants.AUTHORIZATION_CODE);
        return request;
    }
}
