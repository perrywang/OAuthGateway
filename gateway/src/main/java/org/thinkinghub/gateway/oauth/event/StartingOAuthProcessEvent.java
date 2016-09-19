package org.thinkinghub.gateway.oauth.event;

import org.thinkinghub.gateway.oauth.entity.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class StartingOAuthProcessEvent extends GatewayEvent {
    
    private User user;
    
    private String state;
    
    private String callback;
    
    public StartingOAuthProcessEvent(User user, String state, String callback) {
        this.user = user;
        this.state = state;
        this.callback = callback;
    }

}

