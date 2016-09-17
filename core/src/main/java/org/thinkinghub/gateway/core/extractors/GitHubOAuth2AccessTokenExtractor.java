package org.thinkinghub.gateway.core.extractors;


import com.github.scribejava.core.extractors.OAuth2AccessTokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.util.RegexUtil;

public class GitHubOAuth2AccessTokenExtractor extends OAuth2AccessTokenExtractor {
    private final String ERRORCODE_REGEX = "error=([^&]+)";
    private final String ERRORDESC_REGEX = "error_description=([^&]+)";

    private static class InstanceHolder {
        private static final GitHubOAuth2AccessTokenExtractor INSTANCE = new GitHubOAuth2AccessTokenExtractor();
    }

    public static GitHubOAuth2AccessTokenExtractor instance() {
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
                , null, null, accessToken.getScope(), response);
    }
}
