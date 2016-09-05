package org.thinkinghub.gateway.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.thinkinghub.gateway.oauth.bean.QQProperties;
import org.thinkinghub.gateway.oauth.bean.WeiboProperties;

@SpringBootApplication
public class OauthGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthGatewayApplication.class, args);
	}
}
