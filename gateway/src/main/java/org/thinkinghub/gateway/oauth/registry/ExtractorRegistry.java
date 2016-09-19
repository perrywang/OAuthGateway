package org.thinkinghub.gateway.oauth.registry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.exception.ServiceNotSupportedException;
import org.thinkinghub.gateway.oauth.extractor.FacebookResponseExtractor;
import org.thinkinghub.gateway.oauth.extractor.GitHubResponseExtractor;
import org.thinkinghub.gateway.oauth.extractor.QQResponseExtractor;
import org.thinkinghub.gateway.oauth.extractor.ResponseExtractor;
import org.thinkinghub.gateway.oauth.extractor.WeiboResponseExtractor;
import org.thinkinghub.gateway.oauth.extractor.WeixinResponseExtractor;

@Service
public class ExtractorRegistry {
    private static ExtractorRegistry self;

    @Autowired
    QQResponseExtractor qqRX ;
    @Autowired
    WeiboResponseExtractor weiboRX ;
    @Autowired
    WeixinResponseExtractor weixinRX ;
    @Autowired
    GitHubResponseExtractor githubRX ;
    @Autowired
    FacebookResponseExtractor facebookRX ;

    @PostConstruct
    public void init() {
        self = this;
    }

    public static ExtractorRegistry instance() {
        return self;
    }

    public ResponseExtractor getExtractor(ServiceType service) {
        switch (service) {
            case QQ:
                return qqRX;
            case WEIBO:
            	return weiboRX;
            case WECHAT:
                return weixinRX;
            case GITHUB:
                return githubRX;
            case FACEBOOK:
                return facebookRX;
        }
        throw new ServiceNotSupportedException(service.name());
        
    }

}
