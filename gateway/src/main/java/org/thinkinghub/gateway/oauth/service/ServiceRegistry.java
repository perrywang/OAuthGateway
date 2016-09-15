package org.thinkinghub.gateway.oauth.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

@Service
public class ServiceRegistry {
    private static ServiceRegistry self;

    @Autowired
    WeiboService weiboService;

    @Autowired
    QQService qqService;

    @Autowired
    WeixinService weixinService;

    @Autowired
    GitHubService gitHubService;

    @PostConstruct
    public void init() {
        self = this;
    }

    public static ServiceRegistry instance() {
        return self;
    }

    public AbstractOAuthService getService(ServiceType service) {
        switch (service) {
            case WEIBO:
                return weiboService;
            case QQ:
                return qqService;
            case WECHAT:
                return weixinService;
            case GITHUB:
                return gitHubService;
            default:
                return null;
        }
    }
}
