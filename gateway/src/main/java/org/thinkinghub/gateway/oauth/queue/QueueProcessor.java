package org.thinkinghub.gateway.oauth.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class QueueProcessor {
    
    private String queueName;
    
    private ExecutorService workerPool;
        
    public QueueProcessor(String queueName, int workerNum) {
        assert queueName != null && queueName.length() > 0;
        assert workerNum > 0;
        this.queueName = queueName;
        this.workerPool = Executors.newFixedThreadPool(workerNum, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName(String.format("queue-worker-thread-for-%s-%d", queueName, t.getId()));
                t.setDaemon(true);
                return t;
            }
        });
    }
    
    public void put(QueuableTask task) {
        workerPool.submit(task);
    }

    public String getQueueName() {
        return queueName;
    }
}
