package org.thinkinghub.gateway.oauth.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class QueueConsumer implements InitializingBean{
    @Autowired
    private GatewayQueue queue;

    public boolean shutdown;

    @PostConstruct
    public void handle() {
        log.debug("Start handling all pending tasks in org.thinkinghub.gateway.oauth.queue");
        new Executor().start();
    }

    class Executor extends Thread {
        public void run() {
            while (!shutdown) {
                QueueTask qt = null;
                try {
                    qt = queue.get();
                } catch (InterruptedException e) {
                    log.error("Errors happen while getting object from org.thinkinghub.gateway.oauth.queue", e);
                }
                qt.execute();
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    //shutdown can be configured in one property file
    public void shutDown() {
        shutdown = true;
    }
}
