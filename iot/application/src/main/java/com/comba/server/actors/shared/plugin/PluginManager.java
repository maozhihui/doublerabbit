package com.comba.server.actors.shared.plugin;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.plugin.PluginActor;
import com.comba.server.common.data.device.PluginJpa;
import com.comba.server.common.data.id.PluginId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.page.PageDataIterable.FetchFunction;
import com.comba.server.common.data.plugin.PluginMetaData;
import com.comba.server.dao.plugin.PluginJpaService;
import com.comba.server.dao.plugin.PluginService;

@Slf4j
public abstract class PluginManager {

    protected final ActorSystemContext systemContext;
    protected final PluginJpaService pluginJpaService;
    protected final Map<PluginId, ActorRef> pluginActors;

    public PluginManager(ActorSystemContext systemContext) {
        this.systemContext = systemContext;
        this.pluginJpaService = systemContext.getPluginJpaService();
        this.pluginActors = new HashMap<>();
    }

    public void init(ActorContext context) {
        List<PluginJpa> pluginIterator = getPlugins();
        for (PluginJpa pluginJpa : pluginIterator) {
        	log.debug("[{}] Creating plugin actor", pluginJpa.getId());
        	PluginId pluginId = new PluginId(pluginJpa.getId().getId());
            getOrCreatePluginActor(context, pluginId);
            log.debug("Plugin actor created.");
		}
    }

    abstract List<PluginJpa> getPlugins();
    
    abstract TenantId getTenantId();

    abstract String getDispatcherName();

    public ActorRef getOrCreatePluginActor(ActorContext context, PluginId pluginId) {
        return pluginActors.computeIfAbsent(pluginId, pId ->
                context.actorOf(Props.create(new PluginActor.ActorCreator(systemContext, getTenantId(), pId))
                        .withDispatcher(getDispatcherName()), pId.toString()));
    }

    public void broadcast(Object msg) {
        pluginActors.values().forEach(actorRef -> actorRef.tell(msg, ActorRef.noSender()));
    }

    public void remove(PluginId id) {
        pluginActors.remove(id);
    }
}
