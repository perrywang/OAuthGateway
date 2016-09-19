package org.thinkinghub.gateway.oauth.service;

import org.thinkinghub.gateway.oauth.bean.GatewayResponse;
import org.thinkinghub.gateway.oauth.entity.User;
import org.thinkinghub.gateway.oauth.exception.GatewayException;

public interface OAuthService {
    
    void authenticate(User user, String callback);
    
    default GatewayResponse authenticated(String code, String state) {
        throw new GatewayException("Not implemented");
    }

}