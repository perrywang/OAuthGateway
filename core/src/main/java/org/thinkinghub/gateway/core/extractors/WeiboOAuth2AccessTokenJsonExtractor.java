package org.thinkinghub.gateway.core.extractors;

import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;

public class WeiboOAuth2AccessTokenJsonExtractor extends OAuth2AccessTokenJsonExtractor {
    private static final String USERID_REGEX = "\"uid\"\\s*:\\s*\"(\\S*?)\"";
    private static final String ERRORCODE_REGEX = "\"error_code\"\\s*:\\s*\"(\\S*?)\"";
    private static final String ERRORDESC_REGEX = "\"error_description\"\\s*:\\s*\"(\\S*?)\"";

    private static class InstanceHolder {
        private static final WeiboOAuth2AccessTokenJsonExtractor INSTANCE = new WeiboOAuth2AccessTokenJsonExtractor();
    }

    public static WeiboOAuth2AccessTokenJsonExtractor instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public OAuth2AccessToken extract(String response) {
        String errorCode = extractParameter(response, ERRORCODE_REGEX, false);
        if(errorCode != null){
            String errorDesc = extractParameter(response, ERRORDESC_REGEX, false);
            return new GatewayAccessToken(errorCode, errorDesc, response);
        }

        OAuth2AccessToken baseToken = super.extract(response);
        final String userId = extractParameter(response, USERID_REGEX, true);

        return new GatewayAccessToken(baseToken.getAccessToken(), baseToken.getTokenType(), baseToken.getExpiresIn(),
                baseToken.getRefreshToken(), baseToken.getScope(), response, userId);
    }
}
