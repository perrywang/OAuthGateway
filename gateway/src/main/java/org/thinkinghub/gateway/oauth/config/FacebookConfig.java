package org.thinkinghub.gateway.oauth.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@NoArgsConstructor
@Configuration
@Data
@ConfigurationProperties(prefix = "facebook")
public class FacebookConfig extends GatewayOAuthConfig{
    private String apiVersion;
}
