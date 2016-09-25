package org.thinkinghub.gateway.oauth.extractor;

import org.thinkinghub.gateway.oauth.entity.ServiceType;

public class LinkedInResponseExtractor extends BaseResponseExtractor  {
    @Override
    ServiceType getServiceType() {
        return ServiceType.LINKEDIN;
    }

    @Override
    String getUserIdFieldName() {
        return "id";
    }

    @Override
    String getNickNameFieldName() {
        return "firstName";
    }

    @Override
    String getHeadImageUrlFieldName() {
        return null;
    }

    @Override
    String getErrorCodeFieldName() {
        return "errorCode";
    }

    @Override
    String getErrorDescFieldName() {
        return "message";
    }
}
