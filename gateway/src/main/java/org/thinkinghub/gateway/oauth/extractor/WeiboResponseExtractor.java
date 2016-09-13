package org.thinkinghub.gateway.oauth.extractor;

import org.springframework.stereotype.Component;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

@Component("Weibo")
public class WeiboResponseExtractor extends BaseResponseExtractor {
    
    public String getUserIdFieldName(){
    	return "id";
    }

    public String getNickNameFieldName(){
    	return "screen_name";
    }

    public String getHeadImageUrlFieldName(){
    	return "profile_image_url";
    }

    public String getErrorCodeFieldName() {
        return "error_code";
    }

    public String getErrorDescFieldName() {
        return "error_description";
    }

    public ServiceType getServiceType() {
        return ServiceType.WEIBO;
    }
}
