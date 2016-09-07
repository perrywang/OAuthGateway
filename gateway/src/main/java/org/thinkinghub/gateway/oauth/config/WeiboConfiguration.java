package org.thinkinghub.gateway.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Configuration
@ConfigurationProperties(prefix = "weibo")
public class WeiboConfiguration {
    private String apiKey;
    private String apiSecret;
    private String callback;

}
