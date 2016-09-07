package org.thinkinghub.gateway.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OauthGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthGatewayApplication.class, args);
	}
}
