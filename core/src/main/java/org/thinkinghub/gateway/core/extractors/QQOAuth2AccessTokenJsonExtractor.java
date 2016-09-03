package org.thinkinghub.gateway.core.extractors;

import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.utils.Preconditions;


public class QQOAuth2AccessTokenJsonExtractor extends OAuth2AccessTokenJsonExtractor{
	private static final String ACCESS_TOKEN_REGEX_QQ = "access_token\\s*=(\\s*[a-z0-9A-Z]*)?";
    private static final String TOKEN_TYPE_REGEX_QQ = "\"token_type\"\\s*=\\s*\"(\\S*?)\"";
    private static final String EXPIRES_IN_REGEX_QQ = "\"expires_in\"\\s*=\\s*\"?(\\d*?)\"?\\D";
    private static final String REFRESH_TOKEN_REGEX_QQ = "\"refresh_token\"\\s*=\\s*\"(\\S*?)\"";
    private static final String SCOPE_REGEX_QQ = "\"scope\"\\s*\\s*=\"(\\S*?)\"";
	
	public QQOAuth2AccessTokenJsonExtractor(){
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
        Preconditions.checkEmptyString(response,
                "Response body is incorrect. Can't extract a token from an empty string");

        final String accessToken = extractParameter(response, ACCESS_TOKEN_REGEX_QQ, true);
        final String tokenType = extractParameter(response, TOKEN_TYPE_REGEX_QQ, false);
        final String expiresInString = extractParameter(response, EXPIRES_IN_REGEX_QQ, false);
        Integer expiresIn;
        try {
            expiresIn = expiresInString == null ? null : Integer.valueOf(expiresInString);
        } catch (NumberFormatException nfe) {
            expiresIn = null;
        }
        final String refreshToken = extractParameter(response, REFRESH_TOKEN_REGEX_QQ, false);
        final String scope = extractParameter(response, SCOPE_REGEX_QQ, false);

        return createToken(accessToken, tokenType, expiresIn, refreshToken, scope, response);
    }
}
