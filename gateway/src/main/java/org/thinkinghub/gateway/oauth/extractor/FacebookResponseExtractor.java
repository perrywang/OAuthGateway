package org.thinkinghub.gateway.oauth.extractor;

import org.springframework.stereotype.Component;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

@Component("Facebook")
public class FacebookResponseExtractor extends BaseResponseExtractor {
    @Override
    ServiceType getServiceType() {
        return ServiceType.FACEBOOK;
    }

    @Override
    String getUserIdFieldName() {
        return "id";
    }

    @Override
    String getNickNameFieldName() {
        return "name";
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
