package org.thinkinghub.gateway.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.extractor.ResponseExtractor;

@Service
public class ResultHandlingService {
    @Autowired
    @Qualifier("WeixinResponseExtractor")
    private ResponseExtractor WeixinExtractor;

    @Autowired
    @Qualifier("WeiboResponseExtractor")
    private ResponseExtractor WeiboExtractor;

    @Autowired
    @Qualifier("QQResponseExtractor")
    private ResponseExtractor QQExtractor;

    public RetBean getRetBean(String rawResponse, ServiceType service){
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
