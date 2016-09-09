package org.thinkinghub.gateway.oauth.extractor;


import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

public class QQResponseExtrator extends ResponseExtractor {
    private final String RET = "ret"; //if ret is not 0, the error code will be stored in it.
    private final String USERID = "openid";
    private final String NICKNAME = "nickname";
    private final String HEADIMAGE_URL = "figureurl";
    private final String ERRORDESC = "msg";

    @Override
    public RetBean extract(String response) {
        String ret = extractParameter(response, getJsonRegex(RET));
        if(("0").equals(ret)){
            String errorDesc = extractParameter(response, getJsonRegex(ERRORDESC));
            return new RetBean(ret, errorDesc, ServiceType.QQ);
        }

        String userId = extractParameter(response, getJsonRegex(USERID));
        String nickname = extractParameter(response, getJsonRegex(NICKNAME));
        String headImage = extractParameter(response, getJsonRegex(HEADIMAGE_URL));

        return new RetBean(userId, nickname, headImage, ServiceType.QQ);
    }
}
