package org.thinkinghub.gateway.oauth.event;

import org.thinkinghub.gateway.oauth.response.GatewayResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OAuthProcessFinishedEvent extends GatewayEvent {
    
    private GatewayResponse response;
    
    private String state;
    
    public OAuthProcessFinishedEvent(GatewayResponse response,String state) {
        this.response = response;
        this.state = state;
    }
}
