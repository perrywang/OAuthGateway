package org.thinkinghub.gateway.oauth.event;

import com.github.scribejava.core.model.OAuth2AccessToken;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AccessTokenRetrievedEvent extends GatewayEvent {
    
    private String state;
    
    private OAuth2AccessToken token;
    
    public AccessTokenRetrievedEvent(String state, OAuth2AccessToken token) {
        this.state = state;
        this.token = token;
    }

}
