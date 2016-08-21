package org.thinkinghub.gateway.core;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.oauth.OAuth20Service;

public class WeixinApi extends DefaultApi20{

    private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&state=esfadsgsad34fwdef&scope=snsapi_login#wechat_redirect";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";

    /**
     * see getAuthorizationUrl to understand what parameters will be appened to the base Url.
     * @return
     */
    @Override
    public String getAuthorizationBaseUrl() {
        return AUTHORIZE_URL;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return ACCESS_TOKEN_URL;
    }

    @Override
    public OAuth20Service createService(OAuthConfig config){
        return null;
    }
}
