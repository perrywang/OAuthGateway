package org.thinkinghub.gateway.util;

public class Constants {
    public static final String WEIXIN_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&state=esfadsgsad34fwdef&scope=snsapi_login#wechat_redirect";
    public static final String WEIXIN_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";
    public static final String WEBXIN_API_KEY = "appid";
    public static final String WEBXIN_API_SECRET = "secret";
}
