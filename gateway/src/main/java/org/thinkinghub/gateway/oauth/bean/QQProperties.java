package org.thinkinghub.gateway.oauth.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Configuration
@ConfigurationProperties(prefix = "qq")
public class QQProperties {
	private String apiKey;
	private String apiSecret;
	private String callback;
}
