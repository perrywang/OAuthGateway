package org.thinkinghub.gateway.oauth.service;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.WeiboApi;
import org.thinkinghub.gateway.oauth.config.WeiboConfig;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class WeiboService extends AbstractOAuthService {
    @Autowired
    private WeiboConfig weiboConfig;

    public WeiboService() {
    }

    @PostConstruct
    public void initialize() {
        log.info(weiboConfig.toString());
    }

    protected OAuth20Service getOAuthService(String state) {
        OAuth20Service service = new ServiceBuilder().apiKey(weiboConfig.getApiKey())
                .apiSecret(weiboConfig.getApiSecret()).callback(weiboConfig.getCallback()).state(state)
                .build(WeiboApi.instance());
        return service;
    }

    @Override
    String getUserInfoUrl() {
        return "https://api.weibo.com/2/users/show.json";
    }

    @Override
    String getAppendedUrl() {
        return "?uid=" + getAccessToken().getUserId();
    }
}
