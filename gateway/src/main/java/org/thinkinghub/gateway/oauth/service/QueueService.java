package org.thinkinghub.gateway.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;
import queue.GatewayQueue;

@Service
public class QueueService {
    @Autowired
    GatewayQueue queue;

    public void add(AuthenticationHistory ah){
        queue.addElement(ah);
    }
}
