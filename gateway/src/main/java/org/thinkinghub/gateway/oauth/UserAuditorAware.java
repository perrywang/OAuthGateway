package org.thinkinghub.gateway.oauth;

import org.springframework.data.domain.AuditorAware;
import org.thinkinghub.gateway.oauth.entity.User;

public class UserAuditorAware implements AuditorAware<User> {

    public User getCurrentAuditor() {
        return new User("test_user");
    }
}
