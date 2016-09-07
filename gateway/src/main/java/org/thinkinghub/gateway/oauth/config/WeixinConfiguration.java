package org.thinkinghub.gateway.oauth.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@NoArgsConstructor
@Data
@Configuration
@ConfigurationProperties(prefix = "weixin")
public class WeixinConfiguration {
    private String apiKey;
    private String apiSecret;
    private String callback;
    private String scope;
    private String responseType;
}
