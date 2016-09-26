package org.thinkinghub.gateway.core.extractors;

import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.util.RegexUtil;

public class LinkedInOAuth2AccessTokenExtractor extends OAuth2AccessTokenJsonExtractor {
    private static final String ERRORCODE_REGEX = "\"error\"\\s*:\\s*\"(\\S*?)\"";
    private static final String ERRORDESC_REGEX = "\"error_description\"\\s*:\\s*\"(.*?)\"";

    private static class InstanceHolder {
        private static final LinkedInOAuth2AccessTokenExtractor INSTANCE = new LinkedInOAuth2AccessTokenExtractor();
    }

    public static LinkedInOAuth2AccessTokenExtractor instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public OAuth2AccessToken extract(String response) {
        String errorCode = RegexUtil.extractParameter(response, ERRORCODE_REGEX);
        if (errorCode != null) {
            String errorDesc = RegexUtil.extractParameter(response, ERRORDESC_REGEX);
            return new GatewayAccessToken(errorCode, errorDesc, response);
        }

        OAuth2AccessToken accessToken = super.extract(response);
        return new GatewayAccessToken(accessToken.getAccessToken(), null
                , accessToken.getExpiresIn(), null, null, response);
    }
}
