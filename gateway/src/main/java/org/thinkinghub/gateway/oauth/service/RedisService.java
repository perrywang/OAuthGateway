package org.thinkinghub.gateway.oauth.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisService {
    
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    
    private final String DEFAULT_HASH_KEY = "DEFAULT_HASH_KEY_GATEWAY";
     
    public void put(Object key, Object value) {
        redisTemplate.boundHashOps(DEFAULT_HASH_KEY).put(key, value);
    }
    
    public Object get(Object key) {
        return redisTemplate.boundHashOps(DEFAULT_HASH_KEY).get(key);
    }
    
    @PostConstruct
    public void initialize() {
        log.info("redis service is ready");
    }

}
