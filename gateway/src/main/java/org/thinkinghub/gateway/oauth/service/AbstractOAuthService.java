package org.thinkinghub.gateway.oauth.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.oauth.bean.GatewayResponse;
import org.thinkinghub.gateway.oauth.entity.User;
import org.thinkinghub.gateway.oauth.event.AccessTokenRetrievedEvent;
import org.thinkinghub.gateway.oauth.event.StartingOAuthProcessEvent;
import org.thinkinghub.gateway.oauth.exception.BadAccessTokenException;
import org.thinkinghub.gateway.oauth.exception.GatewayException;
import org.thinkinghub.gateway.oauth.exception.OAuthProcessingException;
import org.thinkinghub.gateway.oauth.registry.EventPublisher;
import org.thinkinghub.gateway.util.IDGenerator;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public abstract class AbstractOAuthService implements OAuthService {

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
            return accessToken;
        } catch (IOException e) {
        	log.error("IOException occurred while getting Access Token",e);
        	throw new OAuthProcessingException("","");
        }
    }
    
    protected abstract GatewayResponse parseUserInfoResponse(Response response);
    
    protected GatewayResponse retriveUserInfo(GatewayAccessToken accessToken, OAuth20Service service) {
        String userInfoUrl = getUserInfoUrl() + getAppendedUrl(accessToken);
        final OAuthRequest request = new OAuthRequest(Verb.GET, userInfoUrl, service);
        service.signRequest(accessToken, request);
        Response response = request.send();
        return parseUserInfoResponse(response);
    }
    
    protected void checkToken(OAuth2AccessToken accessToken){
        if(accessToken.equals("error")){
            //TODO exception handling required?
            throw new BadAccessTokenException("can not get correct access token");
        }
    }
    
    @Override
    public GatewayResponse authenticated(String state, String code) {
        OAuth20Service service = getOAuthServiceProvider(state);
        GatewayAccessToken accessToken = getAccessToken(state, code);
        checkToken(accessToken);
        EventPublisher.instance().publishEvent(new AccessTokenRetrievedEvent(state, accessToken));
        return retriveUserInfo(accessToken,service);
    }

    @Override
    public void authenticate(User user, String callback) {
        String state = Long.toString(IDGenerator.nextId());
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = sra.getResponse();
        try {
            response.sendRedirect(getAuthorizationUrl(state));
        } catch (IOException e) {
            throw new GatewayException("start oauth process failed");
        } 
        EventPublisher.instance().publishEvent(new StartingOAuthProcessEvent(user,this.supportedOAuthType(),state, callback));
    }

}
