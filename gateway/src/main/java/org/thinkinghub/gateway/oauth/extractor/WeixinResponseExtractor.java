package org.thinkinghub.gateway.oauth.extractor;

import org.thinkinghub.gateway.oauth.bean.RetBean;

public class WeixinResponseExtractor extends ResponseExtractor {
    private static final String USERID = "openid";
    private static final String ERRORCODE = "errcode";
    private static final String ERRORDESC = "errmsg";

    @Override
    public RetBean extract(String response) {
        return null;
    }
}
