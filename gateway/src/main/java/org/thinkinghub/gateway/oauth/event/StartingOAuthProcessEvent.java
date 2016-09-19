package org.thinkinghub.gateway.oauth.event;

import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.entity.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class StartingOAuthProcessEvent extends GatewayEvent {
    
    private User user;
    
    private ServiceType service;
    
    private String state;
    
    private String callback;
    
    public StartingOAuthProcessEvent(User user, ServiceType service, String state, String callback) {
        this.user = user;
        this.service = service;
        this.state = state;
        this.callback = callback;
    }

}

