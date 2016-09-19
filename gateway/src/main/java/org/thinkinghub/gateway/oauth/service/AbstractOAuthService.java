package org.thinkinghub.gateway.oauth.service;

import java.io.IOException;

import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.oauth.event.AccessTokenRetrievedEvent;
import org.thinkinghub.gateway.oauth.exception.OAuthProcessingException;
import org.thinkinghub.gateway.oauth.registry.EventPublisherRegistry;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
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
        	log.error("IOException occurred while getting Access Token",e);
        	throw new OAuthProcessingException("","");
        }
    }

    public Response getResponse(String state, String code) {
        OAuth20Service service = getOAuthService(state);
        GatewayAccessToken accessToken = getAccessToken(state, code);
        setAccessToken(accessToken);
        EventPublisherRegistry.instance().getEventPublisher().publishEvent(new AccessTokenRetrievedEvent(state, accessToken));

        // send request to get user info
        String userInfoUrl = getUserInfoUrl() + (getAppendedUrl() != null ? getAppendedUrl() : "");
        final OAuthRequest request = new OAuthRequest(Verb.GET, userInfoUrl, service);
        service.signRequest(accessToken, request);
        Response response = request.send();

        return response;
    }


}
