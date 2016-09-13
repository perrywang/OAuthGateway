package org.thinkinghub.gateway.oauth.extractor;

import java.io.IOException;

import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.exception.GatewayException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.Response;

public abstract class BaseResponseExtractor implements ResponseExtractor {
	public RetBean extract(Response response) {
		JsonNode root = null;
		try {
			ObjectMapper om = new ObjectMapper();
			root = om.readTree(response.getBody());
			if (hasError(response)) {
				String userId = getUserId(response);
				String nickName = root.get(getNickNameFieldName()).asText();
				String headImageUrl = root.get(getHeadImageUrlFieldName()).asText();
				return new RetBean(userId, nickName, headImageUrl, getServiceType(), response.getBody());
			} else {
				int errorCode = root.get(getErrorCodeFieldName()).asInt();
				String errorDesc = root.get(getErrorDescFieldName()).asText();
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
			ObjectMapper om = new ObjectMapper();
			JsonNode root = om.readTree(response.getBody());
			return root.get(getUserIdFieldName()).asText();
		} catch (IOException e) {
			throw new GatewayException("can't found openid", e);
		}
	}

	abstract String getNickNameFieldName();

	abstract String getHeadImageUrlFieldName();

	abstract String getErrorCodeFieldName();

	abstract String getErrorDescFieldName();

}
