package org.thinkinghub.gateway.oauth.repository;

import org.springframework.data.repository.CrudRepository;
import org.thinkinghub.gateway.oauth.Entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findById(long id);
	
	User findByKey(String key);
}
