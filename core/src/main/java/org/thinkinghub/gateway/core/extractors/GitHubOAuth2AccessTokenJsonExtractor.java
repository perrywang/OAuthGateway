package org.thinkinghub.gateway.core.extractors;


import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;

public class GitHubOAuth2AccessTokenJsonExtractor extends OAuth2AccessTokenJsonExtractor {
    private final String ACCESS_TOKEN_REGEX = "access_token\\s*=(\\s*[a-z0-9A-Z]*)?";
    private final String SCOPE_REGEX = "scope\\s*=(\\s*\\w*)?";
    private final String TOKENTYPE_REGEX = "token_type\\s*=\\s*(\\w*)?";
    private final String ERRORCODE_REGEX = "error\\s*=\\s*(\\w*?)";
    private final String ERRORDESC_REGEX = "error_description\\s*=\\s*(\\w*?)";

    private static class InstanceHolder {
        private static final GitHubOAuth2AccessTokenJsonExtractor INSTANCE = new GitHubOAuth2AccessTokenJsonExtractor();
    }

    public static GitHubOAuth2AccessTokenJsonExtractor instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public OAuth2AccessToken extract(String response) {
        String errorCode = extractParameter(response, ERRORCODE_REGEX, false);
        if (errorCode != null) {
            String errorDesc = extractParameter(response, ERRORDESC_REGEX, false);
            if(errorDesc !=null){
                errorDesc = errorDesc.replace("+", " ");
            }
            return new GatewayAccessToken(errorCode, errorDesc, response);
        }

        final String accessToken = extractParameter(response, ACCESS_TOKEN_REGEX, true);
        final String scope = extractParameter(response, SCOPE_REGEX, false);
        final String tokenType = extractParameter(response, TOKENTYPE_REGEX, false);

        return new GatewayAccessToken(accessToken, tokenType, null, null, scope, response);
    }
}
