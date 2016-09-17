package org.thinkinghub.gateway.oauth.service;

import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.registry.ExtractorRegistry;
import org.thinkinghub.gateway.oauth.util.JsonUtil;

import com.github.scribejava.core.model.Response;

@Service
public class ResultHandlingService {

	public RetBean getRetBean(Response response, ServiceType service) {
		return ExtractorRegistry.instance().getExtractor(service).extract(response);
	}

	public String getRetJson(Response response, ServiceType service) {
		RetBean bean = getRetBean(response, service);
		return JsonUtil.toJson(bean);
	}
}
