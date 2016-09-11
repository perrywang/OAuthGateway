package org.thinkinghub.gateway.oauth.extractor;

import org.springframework.stereotype.Component;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

@Component("Weibo")
public class WeiboResponseExtractor extends BaseResponseExtractor {
    private final String USERID = "id";
    private final String NICKNAME = "screen_name";
    private final String HEADIMAGE_URL = "profile_image_url";
    private final String ERRORCODE = "error_code";
    private final String ERRORDESC = "error_description";

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

        return new RetBean(userId, nickname, headImage, ServiceType.WEIBO);
    }
}
