package org.thinkinghub.gateway.oauth.extractor;

import org.thinkinghub.gateway.oauth.entity.ServiceType;

public class GitHubResponseExtractor extends BaseResponseExtractor {
    @Override
    ServiceType getServiceType() {
        return ServiceType.GITHUB;
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
