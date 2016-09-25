package org.thinkinghub.gateway.oauth.registry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.exception.ServiceNotSupportedException;
import org.thinkinghub.gateway.oauth.service.*;

@Service
public class ServiceRegistry {

    private static ServiceRegistry self;

    @Autowired
    private WeiboService weiboService;

    @Autowired
    private QQService qqService;

    @Autowired
    private WeixinService weixinService;

    @Autowired
    private GitHubService gitHubService;

    @Autowired
    private FacebookService facebookService;

    @Autowired
    private LinkedInService linkedInService;

    @PostConstruct
    public void init() {
        self = this;
    }

    public static OAuthService getService(ServiceType service) {
        switch (service) {
            case WEIBO:
                return self.weiboService;
            case QQ:
                return self.qqService;
            case WECHAT:
                return self.weixinService;
            case GITHUB:
                return self.gitHubService;
            case FACEBOOK:
                return self.facebookService;
            case LINKEDIN:
                return self.linkedInService;
        }
        throw new ServiceNotSupportedException(service.name());
    }
}
