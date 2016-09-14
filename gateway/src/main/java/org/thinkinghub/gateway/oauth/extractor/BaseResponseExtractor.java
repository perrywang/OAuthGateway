package org.thinkinghub.gateway.oauth.extractor;

import com.github.scribejava.core.model.Response;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.exception.GatewayException;
import org.thinkinghub.gateway.oauth.helper.JsonHelper;

import java.io.IOException;

public abstract class BaseResponseExtractor implements ResponseExtractor {
	public RetBean extract(Response response) {
		try {
			String jsonBody = response.getBody();
			if (hasError(response)) {
				String userId = getUserId(response);
				String nickName = JsonHelper.getValue(jsonBody, getNickNameFieldName());
				String headImageUrl = JsonHelper.getValue(jsonBody, getHeadImageUrlFieldName());
				return new RetBean(userId, nickName, headImageUrl, getServiceType(), response.getBody());
			} else {
				String errorCode = JsonHelper.getValue(jsonBody, getErrorCodeFieldName());
				String errorDesc = JsonHelper.getValue(jsonBody, getErrorDescFieldName());
				return new RetBean(errorCode, errorDesc, getServiceType(), response.getBody());
			}
		} catch (IOException e) {
			throw new GatewayException("can't extract data from response ", e);
		}
	}

	protected boolean hasError(Response response) {
		return response.isSuccessful();
	}

	abstract ServiceType getServiceType();

	abstract String getUserIdFieldName();

	String getUserId(Response response) {
		try {
			return JsonHelper.getValue(response.getBody(), getUserIdFieldName());
		} catch (IOException e) {
			throw new GatewayException("can't found openid", e);
		}
	}

	abstract String getNickNameFieldName();

	abstract String getHeadImageUrlFieldName();

	abstract String getErrorCodeFieldName();

	abstract String getErrorDescFieldName();

}
