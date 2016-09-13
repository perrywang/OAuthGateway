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
    ServiceType getServiceType() {
        return ServiceType.QQ;
    }

    @Override
    public RetBean extract(String response) {
        String ret = extractParameter(response, getUrlParamRegex(getErrorCode()));
        //if ret is not 0, the error code will be stored in it.
        if(!("0").equals(ret)){
            String errorDesc = extractParameter(response, getUrlParamRegex(getErrorDesc()));
            return new RetBean(ret, errorDesc, getServiceType(), response);
        }

        String userId = extractParameter(response, getUrlParamRegex(getUserId()));
        String nickname = extractParameter(response, getUrlParamRegex(getNickname()));
        String headImage = extractParameter(response, getUrlParamRegex(getHeadImageUrl()));

        return new RetBean(userId, nickname, headImage, getServiceType(), response);
    }
}
