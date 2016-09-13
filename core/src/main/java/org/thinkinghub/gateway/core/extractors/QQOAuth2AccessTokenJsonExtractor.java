package org.thinkinghub.gateway.core.extractors;

import org.thinkinghub.gateway.core.token.GatewayAccessToken;

import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;

public class QQOAuth2AccessTokenJsonExtractor extends OAuth2AccessTokenJsonExtractor {
    private final String ACCESS_TOKEN_REGEX = "access_token\\s*=(\\s*[a-z0-9A-Z]*)?";
    private final String EXPIRES_IN_REGEX = "\"expires_in\"\\s*=\\s*\"?(\\d*?)\"?\\D";
    private final String REFRESH_TOKEN_REGEX = "\"refresh_token\"\\s*=\\s*\"(\\S*?)\"";
    private final String ERRORCODE_REGEX = "\"code\"\\s*=\\s*\"(\\S*?)\"";
    private final String ERRORDESC_REGEX = "\"msg\"\\s*=\\s*\"(\\S*?)\"";

    public QQOAuth2AccessTokenJsonExtractor() {
        super();
    }

    private static class InstanceHolder {
        private static final QQOAuth2AccessTokenJsonExtractor INSTANCE = new QQOAuth2AccessTokenJsonExtractor();
    }

    public static QQOAuth2AccessTokenJsonExtractor instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public OAuth2AccessToken extract(String response) {
        String errorCode = extractParameter(response, ERRORCODE_REGEX, false);
        if(errorCode != null){
            String errorDesc = extractParameter(response, ERRORDESC_REGEX, false);
            return new GatewayAccessToken(errorCode, errorDesc, response);
        }

        final String accessToken = extractParameter(response, ACCESS_TOKEN_REGEX, true);
        final String expiresInString = extractParameter(response, EXPIRES_IN_REGEX, false);
        Integer expiresIn;
        try {
            expiresIn = expiresInString == null ? null : Integer.valueOf(expiresInString);
        } catch (NumberFormatException nfe) {
            expiresIn = null;
        }
        final String refreshToken = extractParameter(response, REFRESH_TOKEN_REGEX, false);

        return new GatewayAccessToken(accessToken, null, expiresIn, refreshToken, null, response);
    }
}
