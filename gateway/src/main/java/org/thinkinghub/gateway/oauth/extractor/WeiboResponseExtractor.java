package org.thinkinghub.gateway.oauth.extractor;

import org.springframework.stereotype.Component;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

@Component("Weibo")
public class WeiboResponseExtractor extends BaseResponseExtractor {
    @Override
    String getUserId() {
        return "id";
    }

    @Override
    String getNickname() {
        return "screen_name";
    }

    @Override
    String getHeadImageUrl() {
        return "profile_image_url";
    }

    @Override
    String getErrorCode() {
        return "error_code";
    }

    @Override
    String getErrorDesc() {
        return "error_description";
    }

    @Override
    ServiceType getServiceType() {
        return ServiceType.WEIBO;
    }
}
