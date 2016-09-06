package org.thinkinghub.gateway.core.token;

import com.github.scribejava.core.model.OAuth2AccessToken;

public class GatewayAccessToken extends OAuth2AccessToken {
    private String userId;

    public GatewayAccessToken(String accessToken) {
        super(accessToken);
    }

    public GatewayAccessToken(String accessToken, String rawResponse) {
        super(accessToken, rawResponse);
    }

    public GatewayAccessToken(String accessToken, String tokenType, Integer expiresIn, String refreshToken, String scope, String rawResponse) {
        super(accessToken, tokenType, expiresIn, refreshToken, scope, rawResponse);
    }
    public GatewayAccessToken(String accessToken, String tokenType, Integer expiresIn, String refreshToken, String scope, String rawResponse, String userId) {
        super(accessToken, tokenType, expiresIn, refreshToken, scope, rawResponse);
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }
}
