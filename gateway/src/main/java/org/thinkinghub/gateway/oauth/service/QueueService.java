package org.thinkinghub.gateway.oauth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.queue.QueuableTask;
import org.thinkinghub.gateway.oauth.queue.QueueConfiguration;
import org.thinkinghub.gateway.oauth.queue.QueueProcessor;

@Service
public class QueueService {
    
    @Autowired
    private QueueConfiguration configurations;
    
    private Map<String, QueueProcessor> processors = new HashMap<>();
    
    public void put(QueuableTask queuingTask) {
        String targetQueue = queuingTask.getQueue();
        synchronized(this) {
            if(!processors.containsKey(targetQueue)) {
                int workerSize = configurations.getWorker(targetQueue);
                QueueProcessor processor = new QueueProcessor(targetQueue, workerSize);
                processor.put(queuingTask);
                processors.put(targetQueue, processor);
            }else {
                processors.get(targetQueue).put(queuingTask);
            }
        }
    }
}
