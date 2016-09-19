package org.thinkinghub.gateway.oauth.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.thinkinghub.gateway.oauth.bean.GatewayResponse;
import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;
import org.thinkinghub.gateway.oauth.entity.ErrorType;
import org.thinkinghub.gateway.oauth.entity.ServiceStatus;
import org.thinkinghub.gateway.oauth.queue.QueuableTask;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;
import org.thinkinghub.gateway.oauth.service.QueueService;

import com.github.scribejava.core.model.OAuth2AccessToken;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GatewayEventsHandler {
    
    @Autowired
    private AuthenticationHistoryRepository authenticationHistoryRepository;
    
    @Autowired
    private QueueService queueService;
    
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
    public void onStartingOAuthProcess(StartingOAuthProcessEvent event) {
        AuthenticationHistory ah = new AuthenticationHistory();
        ah.setServiceType(event.getService());
        ah.setState(event.getState());
        ah.setUser(event.getUser());
        ah.setCallback(event.getCallback());
        ah.setServiceStatus(ServiceStatus.INPROGRESS);
        authenticationHistoryRepository.save(ah);
    }
    
    @EventListener
    public void onOAuthProcessFinished(OAuthProcessFinishedEvent event) {
        AuthenticationHistory ah = authenticationHistoryRepository.findByState(event.getState());
        GatewayResponse response = event.getResponse();
        logAuthHistory(ah, response);
    }
    
    private void logAuthHistory(AuthenticationHistory ah, GatewayResponse ret) {
        if (ret.getRetCode() != 0) {
            ah.setErrorCode(ret.getErrorCode());
            ah.setErrorDesc(ret.getErrorDesc());
            ah.setServiceStatus(ServiceStatus.FAILURE);
            ah.setErrorType(ErrorType.THIRDPARTY);
        } else {
            ah.setServiceStatus(ServiceStatus.SUCCESS);
        }
        ah.setRawResponse(ret.getRawResponse());
        queueService.put(new QueuableTask() {
            @Override
            public void execute() {
                authenticationHistoryRepository.save(ah);
            }
        });
    }
}
