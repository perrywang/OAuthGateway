package org.thinkinghub.gateway.core;


import org.junit.Test;
import org.thinkinghub.gateway.api.WeixinApi;

import static org.junit.Assert.assertEquals;

public class WeixinApiTest {
    private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&state=esfadsgsad34fwdef&scope=snsapi_login#wechat_redirect";

    @Test
    public void getAuthorizationBaseUrl() {
        assertEquals(new WeixinApi().getAuthorizationBaseUrl(), AUTHORIZE_URL);
    }
}
