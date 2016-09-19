package org.thinkinghub.gateway.oauth.service;

import org.thinkinghub.gateway.oauth.bean.GatewayResponse;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.entity.User;

public interface OAuthService {
    
    void authenticate(User user, String callback);
    
    GatewayResponse authenticated(String code, String state);
    
    ServiceType supportedOAuthType();

}