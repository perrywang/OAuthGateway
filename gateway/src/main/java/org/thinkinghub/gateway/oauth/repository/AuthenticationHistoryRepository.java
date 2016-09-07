package org.thinkinghub.gateway.oauth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.entity.User;

public interface AuthenticationHistoryRepository extends JpaRepository<AuthenticationHistory, Long> {
    
    Page<AuthenticationHistory> findByUserAndServiceType(User user, ServiceType type, Pageable page);

}
