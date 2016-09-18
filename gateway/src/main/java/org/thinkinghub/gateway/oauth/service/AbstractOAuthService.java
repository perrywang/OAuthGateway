package org.thinkinghub.gateway.oauth.service;

import java.io.IOException;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import lombok.Data;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;

import com.github.scribejava.core.oauth.OAuth20Service;
import org.thinkinghub.gateway.oauth.event.AccessTokenRetrievedEvent;
import org.thinkinghub.gateway.oauth.exception.BadAccessTokenException;
import org.thinkinghub.gateway.oauth.registry.EventPublisherRegistry;

@Data
public abstract class AbstractOAuthService implements OAuthService {
    private GatewayAccessToken accessToken;

    protected abstract OAuth20Service getOAuthService(String state);

    abstract String getUserInfoUrl();

    String getAppendedUrl() {
        return null;
    }

    public String getAuthorizationUrl(String state) {
        final String authorizationUrl = getOAuthService(state).getAuthorizationUrl();
        return authorizationUrl;
    }

    protected GatewayAccessToken getAccessToken(String state, String code) {
        GatewayAccessToken accessToken = null;
        try {
            accessToken = (GatewayAccessToken) getOAuthService(state).getAccessToken(code);
            return accessToken;
        } catch (IOException e) {

        }
        return accessToken;
    }

    public Response getResponse(String state, String code) {
        OAuth20Service service = getOAuthService(state);
        GatewayAccessToken accessToken = getAccessToken(state, code);
        checkToken(accessToken);
        setAccessToken(accessToken);
        EventPublisherRegistry.instance().getEventPublisher().publishEvent(new AccessTokenRetrievedEvent(state, accessToken));

        // send request to get user info
        String userInfoUrl = getUserInfoUrl() + (getAppendedUrl() != null ? getAppendedUrl() : "");
        final OAuthRequest request = new OAuthRequest(Verb.GET, userInfoUrl, service);
        service.signRequest(accessToken, request);
        Response response = request.send();

        return response;
    }

    protected void checkToken(OAuth2AccessToken accessToken){
        if(accessToken.equals("error")){
            //TODO exception handling required?
            throw new BadAccessTokenException("can not get correct access token");
        }
    }

}
