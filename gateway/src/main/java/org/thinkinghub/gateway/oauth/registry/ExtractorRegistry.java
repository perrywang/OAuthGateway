package org.thinkinghub.gateway.oauth.registry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.exception.ServiceNotSupportedException;
import org.thinkinghub.gateway.oauth.extractor.*;

import javax.annotation.PostConstruct;

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
        throw new ServiceNotSupportedException("Service " + service + " is not supported currently");
    }

}
