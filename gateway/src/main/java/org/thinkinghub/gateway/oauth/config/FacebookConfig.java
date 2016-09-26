package org.thinkinghub.gateway.oauth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Configuration
@EqualsAndHashCode(callSuper=false)
@ConfigurationProperties(prefix = "facebook")
@Data
public class FacebookConfig extends GatewayOAuthConfig{
    private String apiVersion;
}
