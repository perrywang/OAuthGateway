package org.thinkinghub.gateway.oauth.service;

import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;
import org.thinkinghub.gateway.oauth.entity.User;


public interface CacheService {
	public User getUser(String key);
	
	public AuthenticationHistory getAuthHistory(String state);
	
	public AuthenticationHistory saveAuthHistory(AuthenticationHistory ah);
}
