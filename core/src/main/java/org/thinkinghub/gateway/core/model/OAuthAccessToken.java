package org.thinkinghub.gateway.core.model;

import java.io.Serializable;

import com.github.scribejava.core.utils.Preconditions;

public class OAuthAccessToken implements Serializable {

	private static final long serialVersionUID = -5594385847438364169L;
	
	private final String rawResponse;
	
	private String accessToken;
	
	private Integer expiresIn;
	
	private String refreshToken;
	
	private String tokenType;
	
	private String scope;
	
    public OAuthAccessToken(String accessToken) {
        this(accessToken, null);
    }

    public OAuthAccessToken(String accessToken, String rawResponse) {
        this(accessToken, null, null, null, null, rawResponse);
    }

    public OAuthAccessToken(String accessToken, String tokenType, Integer expiresIn, String refreshToken, String scope,
            String rawResponse) {
        Preconditions.checkNotNull(accessToken, "access_token can't be null");
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.scope = scope;
        this.rawResponse = rawResponse;
    }
    
    public String getRawResponse() {
        if (rawResponse == null) {
            throw new IllegalStateException(
                    "This token object was not constructed by ScribeJava and does not have a rawResponse");
        }
        return rawResponse;
    }
    
    public String getParameter(String parameter) {
        String value = null;
        for (String str : rawResponse.split("&")) {
            if (str.startsWith(parameter + '=')) {
                final String[] part = str.split("=");
                if (part.length > 1) {
                    value = part[1].trim();
                }
                break;
            }
        }
        return value;
    }
    
    @Override
    public String toString() {
        return "OAuth2AccessToken{"
                + "access_token=" + accessToken
                + ", token_type=" + tokenType
                + ", expires_in=" + expiresIn
                + ", refresh_token=" + refreshToken
                + ", scope=" + scope + '}';
    }
}
