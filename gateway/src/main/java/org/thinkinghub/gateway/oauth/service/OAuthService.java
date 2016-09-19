package org.thinkinghub.gateway.oauth.service;

import org.thinkinghub.gateway.oauth.entity.User;

public interface OAuthService {
    void authenticate(User user, String callback);
}