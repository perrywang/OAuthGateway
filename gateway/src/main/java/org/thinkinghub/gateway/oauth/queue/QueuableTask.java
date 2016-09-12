package org.thinkinghub.gateway.oauth.queue;

public interface QueuableTask extends Runnable {
    
    default String getQueue() {
        return "default";
    }
    
    void execute();
    
    default void run(){
        execute();
    }
}
