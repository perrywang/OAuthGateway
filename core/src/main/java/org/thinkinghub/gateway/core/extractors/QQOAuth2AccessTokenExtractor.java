package org.thinkinghub.gateway.core.extractors;

import com.github.scribejava.core.extractors.OAuth2AccessTokenExtractor;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;

import com.github.scribejava.core.model.OAuth2AccessToken;
import org.thinkinghub.gateway.util.RegexUtil;

public class QQOAuth2AccessTokenExtractor extends OAuth2AccessTokenExtractor {
    //TODO
    private final String ERRORCODE_REGEX = "\"code\"\\s*=\\s*\"(\\S*?)\"";
    private final String ERRORDESC_REGEX = "\"msg\"\\s*=\\s*\"(\\S*?)\"";

    public QQOAuth2AccessTokenExtractor() {
        super();
    }

    private static class InstanceHolder {
        private static final QQOAuth2AccessTokenExtractor INSTANCE = new QQOAuth2AccessTokenExtractor();
    }

    public static QQOAuth2AccessTokenExtractor instance() {
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
        return new GatewayAccessToken(accessToken.getAccessToken(), null, accessToken.getExpiresIn(),
                accessToken.getRefreshToken(), null, response);
    }
}
