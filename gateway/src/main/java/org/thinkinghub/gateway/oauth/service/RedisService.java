package org.thinkinghub.gateway.oauth.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisService {
    
    @SuppressWarnings({ "rawtypes", "unused" })
    @Autowired
    private RedisTemplate redisTemplate;
    
    
    @PostConstruct
    public void initialize() {
        log.info("redis service is ready");
    }

}
