package org.thinkinghub.gateway.core;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.thinkinghub.gateway.api.WeixinApi;

public class WeixinApiTest {
    @SuppressWarnings("unused")
    private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&state=esfadsgsad34fwdef&scope=snsapi_login#wechat_redirect";
    
    private static final String AUTHORIZE_BASE_URL = "https://open.weixin.qq.com/connect/qrconnect";
    
    @Test
    public void getAuthorizationBaseUrl() {
        assertEquals(WeixinApi.instance().getAuthorizationBaseUrl(), AUTHORIZE_BASE_URL);
    }
}
