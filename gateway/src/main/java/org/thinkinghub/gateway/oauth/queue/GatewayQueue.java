package org.thinkinghub.gateway.oauth.queue;

import org.springframework.stereotype.Service;
import org.thinkinghub.gateway.oauth.entity.AuthenticationHistory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class GatewayQueue {
    BlockingQueue<AuthenticationHistory> queue = new LinkedBlockingDeque<>();

    public void addElement(AuthenticationHistory ah){
        queue.add(ah);
    }

    public AuthenticationHistory get() throws InterruptedException {
        //return org.thinkinghub.gateway.oauth.queue.poll(10000, TimeUnit.MICROSECONDS);
        return queue.take();
    }

    public void remove(){
        queue.remove();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public int size(){
        return queue.size();
    }
}
