package org.thinkinghub.gateway.oauth.registry;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EventPublisherRegistry {
    private static EventPublisherRegistry self;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostConstruct
    public void initialize(){
        EventPublisherRegistry.self = this;
    }

    public static ApplicationEventPublisher getEventPublisher(){
        return self.eventPublisher;
    }
}
