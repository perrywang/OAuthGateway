package org.thinkinghub.gateway.oauth.service;

import org.thinkinghub.gateway.oauth.response.GatewayResponse;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.entity.User;

public interface OAuthService {
    
    String authenticate(User user, String callback);
    
    GatewayResponse authenticated(String code, String state);
    
    ServiceType supportedOAuthType();

}