package com.comba.server.extensions.api.plugins.msg;

import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.aware.PluginAwareMsg;

public interface ToPluginActorMsg extends PluginAwareMsg {

    TenantId getPluginTenantId();

}
