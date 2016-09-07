package org.thinkinghub.gateway.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thinkinghub.gateway.oauth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByKey(String key);
}
