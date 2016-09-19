package org.thinkinghub.gateway.oauth.event;

import org.thinkinghub.gateway.oauth.entity.ServiceType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OAuthProviderCallbackReceivedEvent extends GatewayEvent {
    
    private String state;
    
    private ServiceType service;
    
    public OAuthProviderCallbackReceivedEvent(ServiceType service, String state) {
        this.service = service;
        this.state = state;
    }
}
