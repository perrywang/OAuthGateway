package org.thinkinghub.gateway.oauth.extractor;

import com.github.scribejava.core.model.Response;
import org.thinkinghub.gateway.oauth.bean.GatewayResponse;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.exception.GatewayException;
import org.thinkinghub.gateway.oauth.util.JsonUtil;

import java.io.IOException;

public abstract class BaseResponseExtractor implements ResponseExtractor {
	public GatewayResponse extract(Response response) {
		try {
			String jsonBody = response.getBody();
			if (isSuccessful(response)) {
				String userId = JsonUtil.getValue(jsonBody, getUserIdFieldName());
				String nickName = JsonUtil.getValue(jsonBody, getNickNameFieldName());
				String headImageUrl = getHeadImageUrlFieldName() != null ? JsonUtil.getValue(jsonBody, getHeadImageUrlFieldName()) : "";

				return new GatewayResponse(userId, nickName, headImageUrl, getServiceType(), response.getBody());
			} else {
				String errorCode = JsonUtil.getValue(jsonBody, getErrorCodeFieldName());
				String errorDesc = JsonUtil.getValue(jsonBody, getErrorDescFieldName());
				return new GatewayResponse(errorCode, errorDesc, getServiceType(), response.getBody());
			}
		} catch (IOException e) {
			throw new GatewayException("can't extract data from response " + response, e);
		}
	}

	protected boolean isSuccessful(Response response) {
		return response.isSuccessful();
	}

	abstract ServiceType getServiceType();

	abstract String getUserIdFieldName();

	abstract String getNickNameFieldName();

	abstract String getHeadImageUrlFieldName();

	abstract String getErrorCodeFieldName();

	abstract String getErrorDescFieldName();

}
