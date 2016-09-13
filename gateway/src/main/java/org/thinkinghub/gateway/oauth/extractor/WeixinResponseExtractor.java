package org.thinkinghub.gateway.oauth.extractor;

import org.springframework.stereotype.Component;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

@Component("Weixin")
public class WeixinResponseExtractor extends BaseResponseExtractor {
    
    public String getUserIdFieldName(){
    	return "openid";
    }

    public String getNickNameFieldName(){
    	return "nickname";
    }

    public String getHeadImageUrlFieldName(){
    	return "headimgurl";
    }

    public String getErrorCodeFieldName() {
        return "errcode";
    }

    public String getErrorDescFieldName() {
        return "errmsg";
    }
    
    public ServiceType getServiceType() {
        return ServiceType.WECHAT;
    }
}
