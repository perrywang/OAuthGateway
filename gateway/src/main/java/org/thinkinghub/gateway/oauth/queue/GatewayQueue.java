package org.thinkinghub.gateway.oauth.queue;

import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class GatewayQueue {
    BlockingQueue<QueueTask> queue = new LinkedBlockingDeque<>();
    private final String name = "gateway";

    public void addTask(QueueTask task){
        queue.add(task);
    }

    public QueueTask get() throws InterruptedException {
        return queue.take();
    }

    public void remove(){
        queue.remove();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public int size(){
        return queue.size();
    }
}
