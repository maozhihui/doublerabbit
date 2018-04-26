package com.comba.server.actors.plugin;

/**
 * @author Andrew Shvayka
 */
public interface TimeoutScheduler {

    void scheduleMsgWithDelay(Object msg, long delayInMs);

}
