package org.thinkinghub.gateway.core.extractors;

import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.util.RegexUtil;

public class FacebookOAuth2AccessTokenExtractor extends OAuth2AccessTokenJsonExtractor {
    private static final String ERRORCODE_REGEX = "\"code\"\\s*:\\s*(\\d*)";
    private static final String ERRORDESC_REGEX = "\"message\"\\s*:\\s*\"(.*?)\"";

    private static class InstanceHolder {
        private static final FacebookOAuth2AccessTokenExtractor INSTANCE = new FacebookOAuth2AccessTokenExtractor();
    }

    public static FacebookOAuth2AccessTokenExtractor instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public OAuth2AccessToken extract(String response) {
        String errorCode = RegexUtil.extractParameter(response, ERRORCODE_REGEX);
        if (errorCode != null) {
            String errorDesc = RegexUtil.extractParameter(response, ERRORDESC_REGEX);
            if (errorDesc != null) {
                errorDesc = errorDesc.replace("+", " ");
            }
            return new GatewayAccessToken(errorCode, errorDesc, response);
        }

        OAuth2AccessToken accessToken = super.extract(response);
        return new GatewayAccessToken(accessToken.getAccessToken(), accessToken.getTokenType()
                , accessToken.getExpiresIn(), null, accessToken.getScope(), response);
    }
}
