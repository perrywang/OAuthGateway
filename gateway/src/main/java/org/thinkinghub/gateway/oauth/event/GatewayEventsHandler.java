package org.thinkinghub.gateway.oauth.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thinkinghub.gateway.oauth.bean.GatewayResponse;
import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;
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
    public void onOAuthProviderCallbackReceived(OAuthProviderCallbackReceivedEvent event) {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        sra.setAttribute("service", event.getService(), RequestAttributes.SCOPE_REQUEST);
    }
    
    @EventListener
    public void onOAuthProcessFinished(OAuthProcessFinishedEvent event) {
        AuthenticationHistory ah = authenticationHistoryRepository.findByState(event.getState());
        GatewayResponse response = event.getResponse();
        logAuthHistory(ah, response);
    }

    private void logAuthHistory(AuthenticationHistory ah, GatewayResponse response) {
        ah.setServiceStatus(ServiceStatus.SUCCESS);
        ah.setRawResponse(response.getRawResponse());
        queueService.put(new QueuableTask() {
            @Override
            public void execute() {
                authenticationHistoryRepository.save(ah);
            }
        });
    }
}
