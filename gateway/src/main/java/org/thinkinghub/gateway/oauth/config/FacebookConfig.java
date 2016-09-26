package org.thinkinghub.gateway.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Configuration
@EqualsAndHashCode(callSuper=false)
@ConfigurationProperties(prefix = "facebook")
@Data
public class FacebookConfig extends GatewayOAuthConfig{
    private String apiVersion;
}
