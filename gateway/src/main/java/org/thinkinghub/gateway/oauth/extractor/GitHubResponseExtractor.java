package org.thinkinghub.gateway.oauth.extractor;

import org.thinkinghub.gateway.oauth.entity.ServiceType;

public class GitHubResponseExtractor extends BaseResponseExtractor {
    @Override
    ServiceType getServiceType() {
        return ServiceType.GITHUB;
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
        return "avatar_url";
    }

    @Override
    String getErrorCodeFieldName() {
        //there is no error code return
        return null;
    }

    @Override
    String getErrorDescFieldName() {
        return "message";
    }
}
