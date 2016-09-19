package org.thinkinghub.gateway.oauth.extractor;

import org.thinkinghub.gateway.oauth.entity.ServiceType;

public class WeiboResponseExtractor extends BaseResponseExtractor {

	@Override
	public String getUserIdFieldName() {
		return "id";
	}

	@Override
	public String getNickNameFieldName() {
		return "screen_name";
	}

	@Override
	public String getHeadImageUrlFieldName() {
		return "profile_image_url";
	}

	@Override
	public String getErrorCodeFieldName() {
		return "error_code";
	}

	@Override
	public String getErrorDescFieldName() {
		return "error_description";
	}

	@Override
	ServiceType getServiceType() {
		return ServiceType.WEIBO;
	}
}
