package com.comba.server.actors.plugin;

import com.comba.server.extensions.api.exception.UnauthorizedException;
import com.comba.server.extensions.api.plugins.PluginCallback;
import com.comba.server.extensions.api.plugins.PluginContext;
import com.hazelcast.util.function.Consumer;

/**
 * Created by ashvayka on 21.02.17.
 */
public class ValidationCallback implements PluginCallback<Boolean> {

    private final PluginCallback<?> callback;
    private final Consumer<PluginContext> action;

    public ValidationCallback(PluginCallback<?> callback, Consumer<PluginContext> action) {
        this.callback = callback;
        this.action = action;
    }

    @Override
    public void onSuccess(PluginContext ctx, Boolean value) {
        if (value) {
            action.accept(ctx);
        } else {
            onFailure(ctx, new UnauthorizedException());
        }
    }

    @Override
    public void onFailure(PluginContext ctx, Exception e) {
        callback.onFailure(ctx, e);
    }
}
