package org.thinkinghub.gateway.oauth.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@NoArgsConstructor
@Data
@Configuration
@ConfigurationProperties(prefix = "github")
public class GitHubConfiguration {
    private String apiKey;
    private String apiSecret;
    private String callback;
    private String scope;
}
