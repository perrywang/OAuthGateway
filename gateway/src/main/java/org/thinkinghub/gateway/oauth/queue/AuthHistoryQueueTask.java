package org.thinkinghub.gateway.oauth.queue;

import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;
import org.thinkinghub.gateway.oauth.repository.RepositoryRegistry;

public class AuthHistoryQueueTask implements QueueTask {
    AuthenticationHistory authHistory;
    private String queueName;

    @Override
    public void execute() {
        AuthenticationHistoryRepository authHistoryRepo = RepositoryRegistry.instance().getAuthHistoryRepo();
        authHistoryRepo.save(authHistory);
    }

    public AuthHistoryQueueTask(AuthenticationHistory authHistory) {
        this.authHistory = authHistory;
    }

    @Override
    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
