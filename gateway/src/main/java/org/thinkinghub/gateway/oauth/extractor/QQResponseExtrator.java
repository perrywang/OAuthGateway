package org.thinkinghub.gateway.oauth.extractor;


import org.springframework.stereotype.Component;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

@Component("QQ")
public class QQResponseExtrator extends BaseResponseExtractor {
    @Override
    String getUserId() {
        return "openid";
    }

    @Override
    String getNickname() {
        return "nickname";
    }

    @Override
    String getHeadImageUrl() {
        return "figureurl";
    }

    @Override
    String getErrorCode() {
        return "ret";
    }

    @Override
    String getErrorDesc() {
        return "msg";
    }

    @Override
    Enum getServiceType() {
        return ServiceType.QQ;
    }

    @Override
    public RetBean extract(String response) {
        String ret = extractParameter(response, getJsonRegex(getErrorCode()));
        //if ret is not 0, the error code will be stored in it.
        if(!("0").equals(ret)){
            String errorDesc = extractParameter(response, getJsonRegex(getErrorDesc()));
            return new RetBean(ret, errorDesc, ServiceType.QQ);
        }

        String userId = extractParameter(response, getJsonRegex(getUserId()));
        String nickname = extractParameter(response, getJsonRegex(getNickname()));
        String headImage = extractParameter(response, getJsonRegex(getHeadImageUrl()));

        return new RetBean(userId, nickname, headImage, ServiceType.QQ);
    }
}
