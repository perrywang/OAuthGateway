package org.thinkinghub.gateway.oauth.config;

import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "github")
public class GitHubConfig extends GatewayOAuthConfig {

}
