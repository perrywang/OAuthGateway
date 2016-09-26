package org.thinkinghub.gateway.oauth.service;

import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.response.GatewayResponse;

public interface OAuthService {
    
    String authenticate(String key, String callback);
    
    GatewayResponse authenticated(String code, String state);
    
    ServiceType supportedOAuthType();

}