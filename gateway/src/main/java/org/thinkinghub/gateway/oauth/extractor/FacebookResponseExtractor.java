package org.thinkinghub.gateway.oauth.extractor;

import org.springframework.stereotype.Component;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

@Component("Facebook")
public class FacebookResponseExtractor extends BaseResponseExtractor {
    @Override
    ServiceType getServiceType() {
        return ServiceType.FACEBOOK.FACEBOOK;
    }

    @Override
    String getUserIdFieldName() {
        return null;
    }

    @Override
    String getNickNameFieldName() {
        return null;
    }

    @Override
    String getHeadImageUrlFieldName() {
        return null;
    }

    @Override
    String getErrorCodeFieldName() {
        return null;
    }

    @Override
    String getErrorDescFieldName() {
        return null;
    }
}
