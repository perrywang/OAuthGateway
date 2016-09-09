package org.thinkinghub.gateway.oauth.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;
import org.thinkinghub.gateway.oauth.entity.ServiceStatus;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;

import com.github.scribejava.core.model.OAuth2AccessToken;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GatewayEventsHandler {
    
    @Autowired
    private AuthenticationHistoryRepository authenticationHistoryRepository;
    
    @EventListener
    public void onAccessTokenRetrived(AccessTokenRetrievedEvent event) {
        
        AuthenticationHistory ah = authenticationHistoryRepository.findByState(event.getState());
        ah.setServiceStatus(ServiceStatus.SUCCESS);
        
        OAuth2AccessToken accessToken = event.getToken();
        log.info("Got the Access Token!");
        log.info("(if your curious it looks like this: " + accessToken + ", 'rawResponse'='"
                + accessToken.getRawResponse() + "')");

    }
    
    @EventListener
    public void onRetriveAccessTokenStarting(StartingRetriveAccessTokenEvent event) {
        
        AuthenticationHistory ah = new AuthenticationHistory();
        ah.setState(event.getState());
        ah.setUser(event.getUser());
        ah.setCallback(event.getCallback());
        ah.setServiceStatus(ServiceStatus.INPROGRESS);
        authenticationHistoryRepository.save(ah);
    }

}
