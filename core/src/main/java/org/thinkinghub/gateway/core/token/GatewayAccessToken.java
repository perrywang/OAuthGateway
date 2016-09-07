package org.thinkinghub.gateway.core.token;

import com.github.scribejava.core.model.OAuth2AccessToken;

public class GatewayAccessToken extends OAuth2AccessToken {

    private static final long serialVersionUID = -3549128720817595770L;

    private String userId;

    public GatewayAccessToken(String accessToken, String tokenType, Integer expiresIn, String refreshToken,
            String scope, String rawResponse, String userId) {
        super(accessToken, tokenType, expiresIn, refreshToken, scope, rawResponse);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
