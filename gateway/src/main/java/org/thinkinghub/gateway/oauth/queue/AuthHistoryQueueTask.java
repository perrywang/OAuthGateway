package org.thinkinghub.gateway.oauth.queue;

import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;
import org.thinkinghub.gateway.oauth.repository.RepositoryRegistry;

public class AuthHistoryQueueTask implements QueuableTask {
    AuthenticationHistory authHistory;

    @Override
    public void execute() {
        AuthenticationHistoryRepository authHistoryRepo = RepositoryRegistry.instance().getAuthHistoryRepo();
        authHistoryRepo.save(authHistory);
    }

    public AuthHistoryQueueTask(AuthenticationHistory authHistory) {
        this.authHistory = authHistory;
    }
}
