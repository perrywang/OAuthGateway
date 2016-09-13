package org.thinkinghub.gateway.oauth.extractor;

import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.exception.ServiceNotSupportedException;

public class ExtractorFactory {
	private static ResponseExtractor qqRX = new QQResponseExtrator();

	private static ResponseExtractor weiboRX = new WeiboResponseExtractor();

	private static ResponseExtractor weixinRX = new WeixinResponseExtractor();

	public static ResponseExtractor createExtractor(ServiceType service) {
		switch (service) {
		case QQ:
			return qqRX;
		case WEIBO:
			return weiboRX;
		case WECHAT:
			return weixinRX;
		}
		throw new ServiceNotSupportedException("Service " + service + " is not supported currently");
	}

}
