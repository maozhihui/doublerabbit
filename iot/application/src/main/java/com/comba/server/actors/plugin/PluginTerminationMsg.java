package com.comba.server.actors.plugin;

import com.comba.server.actors.shared.ActorTerminationMsg;
import com.comba.server.common.data.id.PluginId;

/**
 * @author Andrew Shvayka
 */
public class PluginTerminationMsg extends ActorTerminationMsg<PluginId> {

    public PluginTerminationMsg(PluginId id) {
        super(id);
    }
}
