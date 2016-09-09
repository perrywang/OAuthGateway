package org.thinkinghub.gateway.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.extractor.QQResponseExtrator;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.extractor.WeiboResponseExtractor;
import org.thinkinghub.gateway.oauth.extractor.WeixinResponseExtractor;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

@Service
public class ResultHandlingService {
    public RetBean getRetBean(String rawResponse, ServiceType service){
        switch(service){
            case WECHAT:
                return new WeixinResponseExtractor().extract(rawResponse);
            case QQ:
                return new QQResponseExtrator().extract(rawResponse);
            case WEIBO:
                return new WeiboResponseExtractor().extract(rawResponse);
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

    public static void main(String[] args){
     /* RetBean bean = new RetBean();
        bean.setRetCode("0");
        bean.setUserId("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        bean.setNickname("Johnson");
        bean.setService(ServiceType.QQ);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(bean);
           System.out.println(json);
        } catch (JsonProcessingException e) {

        }*/
    }
}
