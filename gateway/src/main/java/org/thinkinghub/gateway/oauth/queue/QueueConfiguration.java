package org.thinkinghub.gateway.oauth.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class QueueConfiguration {
    
    private static final String QUEUE_CONFIG_PREFIX = "gateway.queues";
    
    @Autowired
    private Environment properties;
    
    public int getWorker(String queueName) {
        return properties.getProperty(String.format(QUEUE_CONFIG_PREFIX+".%s.worker", queueName), Integer.class,1);
    }
}
