package org.thinkinghub.gateway.oauth.queue;

public interface QueueTask {
    void execute();
    String getQueueName();
}
