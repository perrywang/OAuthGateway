package org.thinkinghub.gateway.core.extractors;

import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;

public class WeixinOAuth2AccessTokenJsonExtractor extends OAuth2AccessTokenJsonExtractor {
    private static final String USERID_REGEX = "\"openid\"\\s*:\\s*\"(\\S*?)\"";

    private static class InstanceHolder {
        private static final WeixinOAuth2AccessTokenJsonExtractor INSTANCE = new WeixinOAuth2AccessTokenJsonExtractor();
    }

    public static WeixinOAuth2AccessTokenJsonExtractor instance() {
        return InstanceHolder.INSTANCE;
    }


    @Override
    public OAuth2AccessToken extract(String response) {
        OAuth2AccessToken baseToken = super.extract(response);
        final String userId = extractParameter(response, USERID_REGEX, true);

        return new GatewayAccessToken(baseToken.getAccessToken(), baseToken.getTokenType(),
                baseToken.getExpiresIn(), baseToken.getRefreshToken(), baseToken.getScope(), null, userId);
    }
}
