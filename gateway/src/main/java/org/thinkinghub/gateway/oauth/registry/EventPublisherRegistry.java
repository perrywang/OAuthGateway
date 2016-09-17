package org.thinkinghub.gateway.oauth.registry;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;

import javax.annotation.PostConstruct;
import javax.xml.ws.ServiceMode;

@Service
public class EventPublisherRegistry {
    private static EventPublisherRegistry self;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostConstruct
    public void initialize(){
        EventPublisherRegistry.self = this;
    }

    public ApplicationEventPublisher getEventPublisher(){
        return eventPublisher;
    }

    public static EventPublisherRegistry instance(){
        return self;
    }
}
