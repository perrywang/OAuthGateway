package org.thinkinghub.gateway.oauth.extractor;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

import javax.annotation.Resource;

@Component("Weixin")
public class WeixinResponseExtractor extends BaseResponseExtractor {
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
        return "headimgurl";
    }

    @Override
    String getErrorCode() {
        return "errcode";
    }

    @Override
    String getErrorDesc() {
        return "errmsg";
    }

    @Override
    Enum getServiceType() {
        return ServiceType.WECHAT;
    }
}
