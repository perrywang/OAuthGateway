package org.thinkinghub.gateway.oauth.registry;

import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.exception.ServiceNotSupportedException;
import org.thinkinghub.gateway.oauth.extractor.*;

public class ExtractorRegistry {
  
    private static class Extractors {
        static final ResponseExtractor qqRX = new QQResponseExtractor();
        static final ResponseExtractor weiboRX = new WeiboResponseExtractor();
        static final ResponseExtractor weixinRX = new WeixinResponseExtractor();
        static final ResponseExtractor githubRX = new GitHubResponseExtractor();
        static final ResponseExtractor facebookRX = new FacebookResponseExtractor();
        static final ResponseExtractor linkedinRX = new LinkedInResponseExtractor();
    }

    public static ResponseExtractor getExtractor(ServiceType service) {
        switch (service) {
            case QQ:
                return Extractors.qqRX;
            case WEIBO:
            	return Extractors.weiboRX;
            case WECHAT:
                return Extractors.weixinRX;
            case GITHUB:
                return Extractors.githubRX;
            case FACEBOOK:
                return Extractors.facebookRX;
            case LINKEDIN:
                return Extractors.linkedinRX;
        }
        
        throw new ServiceNotSupportedException(service.name());
        
    }

}
