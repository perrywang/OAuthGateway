package org.thinkinghub.gateway.oauth.service;

import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.bean.GatewayResponse;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.registry.ExtractorRegistry;
import org.thinkinghub.gateway.oauth.util.JsonUtil;

import com.github.scribejava.core.model.Response;

@Service
public class ResultHandlingService {

	public GatewayResponse getRetBean(Response response, ServiceType service) {
		return ExtractorRegistry.instance().getExtractor(service).extract(response);
	}

	public String getRetJson(Response response, ServiceType service) {
		GatewayResponse bean = getRetBean(response, service);
		return JsonUtil.toJson(bean);
	}
}
