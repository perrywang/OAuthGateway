package org.thinkinghub.gateway.oauth.extractor;

import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.exception.ServiceNotSupportedException;

public class ExtractorFactory {
    public static ResponseExtractor createExtractor(ServiceType service) {
        switch (service) {
            case WECHAT:
                return new WeixinResponseExtractor();
            case QQ:
                return new QQResponseExtrator();
            case WEIBO:
                return new WeiboResponseExtractor();
        }
        throw new ServiceNotSupportedException("Service " + service + " is not supported currently");
    }
}
