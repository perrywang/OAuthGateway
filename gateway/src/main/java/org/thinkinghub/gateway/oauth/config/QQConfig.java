package org.thinkinghub.gateway.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "qq")
public class QQConfig extends GatewayOAuthConfig {
}
