package org.thinkinghub.gateway.oauth.event;

import com.github.scribejava.core.model.OAuth2AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;
import org.thinkinghub.gateway.oauth.entity.ServiceStatus;
import org.thinkinghub.gateway.oauth.entity.State;
import org.thinkinghub.gateway.oauth.queue.QueuableTask;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;
import org.thinkinghub.gateway.oauth.repository.StateRepository;
import org.thinkinghub.gateway.oauth.response.ErrorResponse;
import org.thinkinghub.gateway.oauth.response.GatewayResponse;
import org.thinkinghub.gateway.oauth.service.QueueService;

@Component
@Slf4j
public class GatewayEventsHandler {
    @Autowired
    private AuthenticationHistoryRepository authenticationHistoryRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private QueueService queueService;

    @EventListener
    public void onStartingOAuthProcess(StartingOAuthProcessEvent event) {
        AuthenticationHistory ah = new AuthenticationHistory();
        ah.setServiceType(event.getService());
        ah.setState(event.getState());
        ah.setUser(event.getUser());
        ah.setCallback(event.getCallback());
        ah.setServiceStatus(ServiceStatus.INPROGRESS);
        authenticationHistoryRepository.save(ah);

        State s = new State();
        s.setKey(event.getState());
        stateRepository.save(s);
    }

    @EventListener
    public void onAccessTokenRetrieved(AccessTokenRetrievedEvent event) {
        OAuth2AccessToken accessToken = event.getToken();
        log.info("Got the Access Token!");
        log.info("if you are curious it looks like this: " + accessToken + ", rawResponse="
                + accessToken.getRawResponse() + ", state=" + event.getState());
    }

    @EventListener
    public void onOAuthProviderCallbackReceived(OAuthProviderCallbackReceivedEvent event) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        sra.setAttribute("service", event.getService(), RequestAttributes.SCOPE_REQUEST);
    }

    @EventListener
    public void onOAuthProcessFinished(OAuthProcessFinishedEvent event) {
        AuthenticationHistory ah = authenticationHistoryRepository.findByState(event.getState());
        GatewayResponse response = event.getResponse();
        logAuthHistory(ah, response);
    }

    @EventListener
    public void onOAuthProcessError(OAuthProcessErrorEvent event) {
        log.info("Start processing OAuth error: " + event);
        AuthenticationHistory ah = authenticationHistoryRepository.findByState(event.getState());
        ErrorResponse errResponse = event.getErrResponse();
        logAuthHistory(ah, errResponse);
        log.info("Finish processingOAuth error");
    }

    private void logAuthHistory(AuthenticationHistory ah, GatewayResponse ret) {
        if (ah != null) {
            ah.setServiceStatus(ServiceStatus.SUCCESS);
            ah.setRawResponse(ret.getRawResponse());
            putTaskInQueue(ah);
        }
    }

    private void logAuthHistory(AuthenticationHistory ah, ErrorResponse err) {
        if (ah != null) {
        	ah.setErrorType(err.getErrorType());
            ah.setGwErrorCode(err.getGwErrorCode());
            ah.setErrorCode(err.getOauthErrorCode());
            ah.setErrorDesc(err.getOauthErrorMessage());
            ah.setServiceStatus(ServiceStatus.FAILURE);
            putTaskInQueue(ah);
        }
    }

    private void putTaskInQueue(AuthenticationHistory ah) {
        queueService.put(new QueuableTask() {
            @Override
            public void execute() {
                authenticationHistoryRepository.save(ah);
            }
        });
    }


}
