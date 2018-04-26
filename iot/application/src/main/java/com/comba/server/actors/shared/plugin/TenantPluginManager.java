package com.comba.server.actors.shared.plugin;

import java.util.List;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.service.DefaultActorService;
import com.comba.server.common.data.device.PluginJpa;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.page.PageDataIterable.FetchFunction;
import com.comba.server.common.data.plugin.PluginMetaData;

public class TenantPluginManager extends PluginManager {

    private final TenantId tenantId;

    public TenantPluginManager(ActorSystemContext systemContext, TenantId tenantId) {
        super(systemContext);
        this.tenantId = tenantId;
    }


    @Override
    TenantId getTenantId() {
        return tenantId;
    }

    @Override
    protected String getDispatcherName() {
        return DefaultActorService.TENANT_PLUGIN_DISPATCHER_NAME;
    }

	@Override
	List<PluginJpa> getPlugins() {
		return pluginJpaService.findTenantPlugins(tenantId);
	}

}
