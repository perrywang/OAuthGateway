package org.thinkinghub.gateway.oauth.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.oauth.entity.User;
import org.thinkinghub.gateway.oauth.event.AccessTokenRetrievedEvent;
import org.thinkinghub.gateway.oauth.event.OAuthProcessFinishedEvent;
import org.thinkinghub.gateway.oauth.event.OAuthProviderCallbackReceivedEvent;
import org.thinkinghub.gateway.oauth.event.StartingOAuthProcessEvent;
import org.thinkinghub.gateway.oauth.exception.AccessTokenEmptyException;
import org.thinkinghub.gateway.oauth.exception.BadAccessTokenException;
import org.thinkinghub.gateway.oauth.exception.GWUserNotFoundException;
import org.thinkinghub.gateway.oauth.exception.OAuthProcessingException;
import org.thinkinghub.gateway.oauth.exception.UserIdEmptyException;
import org.thinkinghub.gateway.oauth.registry.EventPublisher;
import org.thinkinghub.gateway.oauth.repository.UserRepository;
import org.thinkinghub.gateway.oauth.response.ErrorResponse;
import org.thinkinghub.gateway.oauth.response.GatewayResponse;
import org.thinkinghub.gateway.util.IDGenerator;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public abstract class AbstractOAuthService implements OAuthService {
	
	@Autowired
	private UserRepository userRepository;
	
    @Override
    public String authenticate(String key, String callback) {
		User user = userRepository.findByKey(key);
		if (user == null) {
			throw new GWUserNotFoundException();
		}
        String state = Long.toString(IDGenerator.nextId());
        EventPublisher.instance().publishEvent(new StartingOAuthProcessEvent(user, this.supportedOAuthType(), state, callback));
        return getAuthorizationUrl(state);
    }

	@Override
    public GatewayResponse authenticated(String code, String state){
        EventPublisher .instance().publishEvent(new OAuthProviderCallbackReceivedEvent(this.supportedOAuthType(), state));
        OAuth20Service service = getOAuthServiceProvider(state);
        GatewayAccessToken accessToken = getAccessToken(state, code);
        checkToken(accessToken);
        EventPublisher.instance().publishEvent(new AccessTokenRetrievedEvent(state, accessToken));
        GatewayResponse gatewayResponse = retrieveUserInfo(accessToken, service);
        checkUserInfo(gatewayResponse);
        EventPublisher.instance().publishEvent(new OAuthProcessFinishedEvent(gatewayResponse, state));
        return gatewayResponse;
    }

    @Override
    public void errorWhenAuthentication(ErrorResponse errorResponse, String state){
    	throw new OAuthProcessingException(errorResponse.getOauthErrorCode(),errorResponse.getOauthErrorMessage());
    }

    protected abstract OAuth20Service getOAuthServiceProvider(String state);

    protected abstract String getUserInfoUrl();

    protected String getAppendedUrl(GatewayAccessToken token) {
        return "";
    }

    protected String getAuthorizationUrl(String state) {
        final String authorizationUrl = getOAuthServiceProvider(state).getAuthorizationUrl();
        return authorizationUrl;
    }

    protected GatewayAccessToken getAccessToken(String state, String code) {
        GatewayAccessToken accessToken = null;
        try {
            accessToken = (GatewayAccessToken) getOAuthServiceProvider(state).getAccessToken(code);
        } catch (IOException e) {
            log.error("IOException occurred while getting Access Token", e);
        }
        return accessToken;
    }

    protected abstract GatewayResponse parseUserInfoResponse(Response response);

    protected GatewayResponse retrieveUserInfo(GatewayAccessToken accessToken, OAuth20Service service) {
        String userInfoUrl = getUserInfoUrl() + getAppendedUrl(accessToken);
        final OAuthRequest request = new OAuthRequest(Verb.GET, userInfoUrl, service);
        service.signRequest(accessToken, request);
        Response response = request.send();
        return parseUserInfoResponse(response);
    }

    protected void checkToken(GatewayAccessToken accessToken) {
        if (accessToken == null) {
            throw new AccessTokenEmptyException();
        }
        if ("error".equals(accessToken.getAccessToken())) {
            throw new BadAccessTokenException(accessToken.getErrorCode(), accessToken.getErrorDesc());
        }
    }

    protected void checkUserInfo(GatewayResponse response) {
        if (response.getUserId() == null || "".equals(response.getUserId().trim())) {
            throw new UserIdEmptyException();
        }
    }

}
