package com.comba.server.actors.plugin;

import lombok.Getter;
import lombok.ToString;

import com.comba.server.extensions.api.plugins.PluginCallback;

/**
 * @author Andrew Shvayka
 */
@ToString
public final class PluginCallbackMessage<V> {
    @Getter
    private final PluginCallback<V> callback;
    @Getter
    private final boolean success;
    @Getter
    private final V v;
    @Getter
    private final Exception e;

    public static <V> PluginCallbackMessage<V> onSuccess(PluginCallback<V> callback, V data) {
        return new PluginCallbackMessage<V>(true, callback, data, null);
    }

    public static <V> PluginCallbackMessage<V> onError(PluginCallback<V> callback, Exception e) {
        return new PluginCallbackMessage<V>(false, callback, null, e);
    }

    private PluginCallbackMessage(boolean success, PluginCallback<V> callback, V v, Exception e) {
        this.success = success;
        this.callback = callback;
        this.v = v;
        this.e = e;
    }
}
