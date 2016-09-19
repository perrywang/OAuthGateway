package org.thinkinghub.gateway.oauth.event;

import org.thinkinghub.gateway.oauth.entity.ServiceType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OAuthProviderCallbackEvent extends GatewayEvent {
    
    private ServiceType service;
    
    private String state;
    
    private String code;
    
    public OAuthProviderCallbackEvent(ServiceType service, String state, String code) {
        
        this.service = service;
        this.state = state;
        this.code = code;
        
    }

}
