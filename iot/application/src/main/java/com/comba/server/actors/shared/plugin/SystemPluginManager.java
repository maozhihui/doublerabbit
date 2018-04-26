package com.comba.server.actors.shared.plugin;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.service.DefaultActorService;
import com.comba.server.common.data.device.PluginJpa;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.dao.model.ModelConstants;

import java.util.List;

public class SystemPluginManager extends PluginManager {

    public SystemPluginManager(ActorSystemContext systemContext) {
        super(systemContext);
    }

    @Override
    TenantId getTenantId() {    	
        return new TenantId(ModelConstants.NULL_UUID);
    }

    @Override
    protected String getDispatcherName() {
        return DefaultActorService.SYSTEM_PLUGIN_DISPATCHER_NAME;
    }

	@Override
	List<PluginJpa> getPlugins() {
		return pluginJpaService.findSystemPlugins();
	}
}
