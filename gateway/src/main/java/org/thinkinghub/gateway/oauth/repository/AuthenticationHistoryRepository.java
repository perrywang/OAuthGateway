package org.thinkinghub.gateway.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;

public interface AuthenticationHistoryRepository extends JpaRepository<AuthenticationHistory, Long> {

}
