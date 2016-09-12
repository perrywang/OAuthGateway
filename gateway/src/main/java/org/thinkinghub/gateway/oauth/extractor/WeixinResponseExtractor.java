package org.thinkinghub.gateway.oauth.extractor;

import org.springframework.stereotype.Component;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

@Component("Weixin")
public class WeixinResponseExtractor extends BaseResponseExtractor {
    private static final String USERID = "openid";
    private final String NICKNAME = "nickname";
    private final String HEADIMAGE_URL = "headimgurl";
    private static final String ERRORCODE = "errcode";
    private static final String ERRORDESC = "errmsg";

    @Override
    public RetBean extract(String response) {
        String errorCode = extractParameter(response, getJsonRegex(ERRORCODE));
        if(errorCode != null){
            String errorDesc = extractParameter(response, getJsonRegex(ERRORDESC));
            return new RetBean(errorCode, errorDesc, ServiceType.WEIBO);
        }

        String userId = extractParameter(response, getJsonRegex(USERID));
        String nickname = extractParameter(response, getJsonRegex(NICKNAME));
        String headImage = extractParameter(response, getJsonRegex(HEADIMAGE_URL));

        return new RetBean(userId, nickname, headImage, ServiceType.WECHAT);
    }
}
