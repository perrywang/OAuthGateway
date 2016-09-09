package org.thinkinghub.gateway.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;
import org.thinkinghub.gateway.oauth.entity.User;
import org.thinkinghub.gateway.oauth.repository.AuthenticationHistoryRepository;
import org.thinkinghub.gateway.oauth.repository.UserRepository;

@Service
public class CacheServiceDBImpl implements CacheService{
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	AuthenticationHistoryRepository authHistoryhRepo;
	
	public User getUser(String key){
		User user = userRepo.findByKey(key);
		return user;
	}

	
	public AuthenticationHistory getAuthHistory(String state){
		AuthenticationHistory ah = authHistoryhRepo.findByState(state);
		return ah;
	}
	
	public AuthenticationHistory saveAuthHistory(AuthenticationHistory ah){
		return authHistoryhRepo.save(ah);
	}
}
