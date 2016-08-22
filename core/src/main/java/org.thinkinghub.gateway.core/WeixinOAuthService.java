package org.thinkinghub.gateway.core;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.oauth.OAuth20Service;

public class WeixinOAuthService extends OAuth20Service{
    public WeixinOAuthService(DefaultApi20 api, OAuthConfig config) {
        super(api, config);
    }



}
