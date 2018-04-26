package com.comba.server.extensions.api.rules;

/**
 * @author Andrew Shvayka
 */
public interface RuleLifecycleComponent {

    void resume();

    void suspend();

    void stop();

}
