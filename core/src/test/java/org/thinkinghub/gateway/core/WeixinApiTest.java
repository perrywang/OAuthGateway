package org.thinkinghub.gateway.core;


import com.github.scribejava.core.model.OAuthConfig;
import org.junit.Test;
import org.thinkinghub.gateway.api.WeixinApi;

import static org.junit.Assert.assertEquals;

public class WeixinApiTest {
    private final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect";
    private final String TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    @Test
    public void getAuthorizationBaseUrl() {
        assertEquals( AUTHORIZE_URL, WeixinApi.instance().getAuthorizationBaseUrl());
    }

    @Test
    public void getAccessTokenEndpoint(){
        assertEquals(TOKEN_URL,  WeixinApi.instance().getAccessTokenEndpoint());
    }

    @Test
    public void getAuthorizationUrl(){
        OAuthConfig config = new OAuthConfig("myTestAppid", "myTestSecret",null, null, null, null, null, "code",null, null,null,null);
        assertEquals(AUTHORIZE_URL + "?response_type=code&appid=myTestAppid", WeixinApi.instance().getAuthorizationUrl(config, null));
    }

}
