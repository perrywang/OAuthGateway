package org.thinkinghub.gateway.oauth.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.thinkinghub.gateway.oauth.Entity.AuthenticationHistory;

public interface AuthenticationHistoryRepository extends CrudRepository<AuthenticationHistory, Long>{
	List<AuthenticationHistory> findById(Long id);
}
