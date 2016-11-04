package org.thinkinghub.gateway.oauth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.thinkinghub.gateway.oauth.entity.State;

public interface StateRepository extends JpaRepository<State, Long> {
    State findByKey(String key);
}
