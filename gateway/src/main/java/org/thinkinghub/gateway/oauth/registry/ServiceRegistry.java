package org.thinkinghub.gateway.oauth.registry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.exception.ServiceNotSupportedException;
import org.thinkinghub.gateway.oauth.service.AbstractOAuthService;
import org.thinkinghub.gateway.oauth.service.FacebookService;
import org.thinkinghub.gateway.oauth.service.GitHubService;
import org.thinkinghub.gateway.oauth.service.QQService;
import org.thinkinghub.gateway.oauth.service.WeiboService;
import org.thinkinghub.gateway.oauth.service.WeixinService;

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

	@Autowired
	FacebookService facebookService;

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
		case FACEBOOK:
			return facebookService;
		}
		throw new ServiceNotSupportedException(service.name());
	}
}
