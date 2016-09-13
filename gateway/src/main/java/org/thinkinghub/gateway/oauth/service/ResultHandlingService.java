package org.thinkinghub.gateway.oauth.service;

import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.extractor.ExtractorFactory;
import org.thinkinghub.gateway.oauth.helper.JsonHelper;

@Service
public class ResultHandlingService {

    public RetBean getRetBean(String rawResponse, ServiceType service){
        return ExtractorFactory.createExtractor(service).extract(rawResponse);
    }

    public String getRetJson(String rawResponse, ServiceType service){
        RetBean bean = getRetBean(rawResponse, service);
        return JsonHelper.toJson(bean);
    }
}
