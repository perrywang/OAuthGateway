package org.thinkinghub.gateway.oauth.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.api.WeiboApi;
import org.thinkinghub.gateway.core.token.GatewayAccessToken;
import org.thinkinghub.gateway.oauth.bean.GatewayResponse;
import org.thinkinghub.gateway.oauth.config.WeiboConfig;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.registry.ExtractorRegistry;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.oauth.OAuth20Service;

import lombok.extern.slf4j.Slf4j;

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

    protected OAuth20Service getOAuthServiceProvider(String state) {
        OAuth20Service service = new ServiceBuilder().apiKey(weiboConfig.getApiKey())
                .apiSecret(weiboConfig.getApiSecret()).callback(weiboConfig.getCallback()).state(state)
                .build(WeiboApi.instance());
        return service;
    }

    @Override
    protected String getUserInfoUrl() {
        return "https://api.weibo.com/2/users/show.json";
    }

    @Override
    protected String getAppendedUrl(GatewayAccessToken token) {
        return "?uid=" + token.getUserId();
    }

    @Override
    protected GatewayResponse parseUserInfoResponse(Response response) {
        return ExtractorRegistry.getExtractor(ServiceType.WEIBO).extract(response);
    }

    @Override
    public ServiceType supportedOAuthType() {
        return ServiceType.WEIBO;
    }

}
