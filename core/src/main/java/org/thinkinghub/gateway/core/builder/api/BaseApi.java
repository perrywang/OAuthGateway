package org.thinkinghub.gateway.core.builder.api;

import org.thinkinghub.gateway.core.model.OAuthConfig;
import org.thinkinghub.gateway.core.service.OAuthService;

public interface BaseApi<T extends OAuthService> {
	T createService(OAuthConfig config);
}
