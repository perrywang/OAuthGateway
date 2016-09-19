package org.thinkinghub.gateway.oauth.extractor;

import org.thinkinghub.gateway.oauth.entity.ServiceType;

public class WeixinResponseExtractor extends BaseResponseExtractor {

	@Override
	public String getUserIdFieldName() {
		return "openid";
	}

	@Override
	public String getNickNameFieldName() {
		return "nickname";
	}

	@Override
	public String getHeadImageUrlFieldName() {
		return "headimgurl";
	}

	@Override
	public String getErrorCodeFieldName() {
		return "errcode";
	}

	@Override
	public String getErrorDescFieldName() {
		return "errmsg";
	}

	@Override
	ServiceType getServiceType() {
		return ServiceType.WECHAT;
	}
}
