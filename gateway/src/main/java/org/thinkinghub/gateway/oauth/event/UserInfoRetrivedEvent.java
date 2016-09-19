package org.thinkinghub.gateway.oauth.event;

import com.github.scribejava.core.model.Response;

import lombok.Data;

@Data
public class UserInfoRetrivedEvent {
    
    private Response response;
    
    public UserInfoRetrivedEvent(Response response) {
        this.response = response;
    }
}
