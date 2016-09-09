package org.thinkinghub.gateway.core.token;

import com.github.scribejava.core.model.OAuth2AccessToken;
import lombok.Getter;

@Getter
public class GatewayAccessToken extends OAuth2AccessToken {

    private static final long serialVersionUID = -3549128720817595770L;

    private String userId;
    private String errorCode;
    private String errorDesc;

    public GatewayAccessToken(String accessToken, String tokenType, Integer expiresIn, String refreshToken,
                              String scope, String rawResponse, String userId) {
        super(accessToken, tokenType, expiresIn, refreshToken, scope, rawResponse);
        this.userId = userId;
    }

    public GatewayAccessToken(String accessToken, String tokenType, Integer expiresIn, String refreshToken,
                              String scope, String rawResponse) {
        super(accessToken, tokenType, expiresIn, refreshToken, scope, rawResponse);
    }

    public GatewayAccessToken(String errorCode, String errorDesc, String rawResponse) {
        super(null, null, null, null, null, rawResponse);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }
}
