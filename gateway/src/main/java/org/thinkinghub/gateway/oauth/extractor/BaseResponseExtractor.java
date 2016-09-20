package org.thinkinghub.gateway.oauth.extractor;

import com.github.scribejava.core.model.Response;
import org.thinkinghub.gateway.oauth.bean.GatewayResponse;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.exception.BadOAuthUserInfoException;
import org.thinkinghub.gateway.oauth.exception.UserInfoJsonRetrievingException;
import org.thinkinghub.gateway.oauth.util.JsonUtil;

import java.io.IOException;

public abstract class BaseResponseExtractor implements ResponseExtractor {

    public GatewayResponse extract(Response response) {
        String jsonBody;
        try {
            jsonBody = response.getBody();
        } catch (IOException e) {
            throw new UserInfoJsonRetrievingException(e);
        }
        if (isSuccessful(response)) {
            //here not need to check if userId is valid as the check will be postponed to do in service later
            String userId = getUserId(jsonBody) != null ? getUserId(jsonBody) : JsonUtil.getValue(jsonBody, getUserIdFieldName());
            String nickName = JsonUtil.getValue(jsonBody, getNickNameFieldName());
            String headImageUrl = getHeadImageUrlFieldName() != null ? JsonUtil.getValue(jsonBody, getHeadImageUrlFieldName()) : "";

            return new GatewayResponse(userId, nickName, headImageUrl, getServiceType(), jsonBody);
        } else {
            String errorCode = JsonUtil.getValue(jsonBody, getErrorCodeFieldName());
            String errorDesc = JsonUtil.getValue(jsonBody, getErrorDescFieldName());
            throw new BadOAuthUserInfoException(errorCode, errorDesc);
        }
    }

    protected boolean isSuccessful(Response response) {
        return response.isSuccessful();
    }

    //In some scenarios the user id is not returned when getting access token like QQ
    //it is ok to send a request with a valid access token, but it would be better if the user id can be extracted from
    //another variable like figureUrl in QQ response of access token.
    //this method should be overridden only for special cases
    String getUserId(String response) {
        return null;
    }

    abstract ServiceType getServiceType();

    abstract String getUserIdFieldName();

    abstract String getNickNameFieldName();

    abstract String getHeadImageUrlFieldName();

    abstract String getErrorCodeFieldName();

    abstract String getErrorDescFieldName();

}
