package org.thinkinghub.gateway.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.queue.GatewayQueue;
import org.thinkinghub.gateway.oauth.queue.QueueTask;

@Service
public class QueueService {
    @Autowired
    GatewayQueue queue;

    public void add(QueueTask task) {
        queue.addTask(task);
    }
}
