package queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class QueueHandler implements InitializingBean{
    @Autowired
    private GatewayQueue queue;

    @Autowired
    private AuthenticationHistoryRepository ahRepository;

    public boolean shutdown;

    @PostConstruct
    public void handle() {
        log.debug("Start handling all pending tasks in queue");
        new Executor().start();
    }

    class Executor extends Thread {
        public void run() {
            while (!shutdown) {
                AuthenticationHistory ah = null;
                try {
                    ah = queue.get();
                } catch (InterruptedException e) {
                    log.error("Errors happen while getting object from queue", e);
                }

                if (ah != null) {
                    ahRepository.save(ah);
                }
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
