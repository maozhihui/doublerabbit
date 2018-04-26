package com.comba.server.service.component;

import java.util.List;
import java.util.Optional;

import com.comba.server.common.data.plugin.ComponentDescriptor;
import com.comba.server.common.data.plugin.ComponentType;

/**
 * @author Andrew Shvayka
 */
public interface ComponentDiscoveryService {

    List<ComponentDescriptor> getComponents(ComponentType type);

    Optional<ComponentDescriptor> getComponent(String clazz);

    List<ComponentDescriptor> getPluginActions(String pluginClazz);

}
