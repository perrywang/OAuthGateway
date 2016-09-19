package org.thinkinghub.gateway.oauth.registry;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {
    
    private static EventPublisher self;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostConstruct
    public void initialize(){
        EventPublisher.self = this;
    }

    public static ApplicationEventPublisher instance(){
        return self.eventPublisher;
    }

}
