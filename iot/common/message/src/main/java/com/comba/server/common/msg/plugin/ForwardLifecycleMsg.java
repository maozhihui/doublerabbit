package com.comba.server.common.msg.plugin;

import com.comba.server.common.data.plugin.ComponentLifecycleEvent;
import lombok.Data;

/**
 * @author maozhihui
 * @Description:
 * @create 2018/1/2 14:09
 **/
@Data
public class ForwardLifecycleMsg {

    private final String tenantId;
    private final String forwardRuleId;
    private final ComponentLifecycleEvent event;

    public ForwardLifecycleMsg(String tenantId,String forwardRuleId,ComponentLifecycleEvent event){
        this.tenantId = tenantId;
        this.forwardRuleId = forwardRuleId;
        this.event = event;
    }
}
