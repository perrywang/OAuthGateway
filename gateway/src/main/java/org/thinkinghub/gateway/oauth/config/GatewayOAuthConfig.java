package org.thinkinghub.gateway.oauth.config;

import lombok.Data;

@Data
public class GatewayOAuthConfig {
    private String apiKey;
    private String apiSecret;
    private String callback;
    private String scope;
    private String responseType;
    private String state;
}
