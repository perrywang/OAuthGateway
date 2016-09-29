package org.thinkinghub.gateway.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OAuthGatewayApplication {

    public static void main(String[] args) {
        System.setProperty("http.proxyHost", "web-proxy.cce.hp.com");
        System.setProperty("http.proxyPort", "8080");
        System.setProperty("https.proxyHost", "web-proxy.cce.hp.com");
        System.setProperty("https.proxyPort", "8080");
        SpringApplication.run(OAuthGatewayApplication.class, args);
    }

}
