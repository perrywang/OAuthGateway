package org.thinkinghub.gateway.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.extractor.ResponseExtractor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ResultHandlingService {
    @Autowired
    @Qualifier("Weixin")
    private ResponseExtractor WeixinExtractor;

    @Autowired
    @Qualifier("Weibo")
    private ResponseExtractor WeiboExtractor;

    @Autowired
    @Qualifier("QQ")
    private ResponseExtractor QQExtractor;

    public RetBean getRetBean(String rawResponse, ServiceType service){
    	
//    	return ResponseExtractors.of(service).extract(rawResponse);
        switch(service){
            case WECHAT:
                return WeixinExtractor.extract(rawResponse);
            case QQ:
                return QQExtractor.extract(rawResponse);
            case WEIBO:
                return WeiboExtractor.extract(rawResponse);
        }
       return null;
    }

    public String getRetJson(String rawResponse, ServiceType service){
        RetBean bean = getRetBean(rawResponse, service);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(bean);
            return json;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
