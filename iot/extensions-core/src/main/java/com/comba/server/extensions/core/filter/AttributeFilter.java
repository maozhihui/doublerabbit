package com.comba.server.extensions.core.filter;

import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.msg.core.TelemetryUploadRequest;
import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.common.msg.session.FromDeviceMsg;
import com.comba.server.extensions.api.component.Filter;
import com.comba.server.extensions.api.rules.RuleContext;
import com.comba.server.extensions.api.rules.RuleFilter;
import com.comba.server.extensions.api.rules.SimpleRuleLifecycleComponent;
import com.ql.util.express.DefaultContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 设备属性过滤器
 * @author maozhihui
 * @Description:
 * @create 2018/3/6 20:13
 **/
@Slf4j
@Filter(name = "AttributeFilter",descriptor = "AttributeFilterDescriptor.json",configuration = AttributeFilterConfiguration.class)
public class AttributeFilter extends SimpleRuleLifecycleComponent implements RuleFilter<AttributeFilterConfiguration> {

    private AttributeFilterConfiguration configuration;
    private DefaultContext<String,Object> paramContext;
    private boolean isMatched = false;

    @Override
    public boolean filter(RuleContext ctx, ToDeviceActorMsg msg) {
        isMatched = false;
        // 校验did
        String actualDid = UUIDUtils.toUUID(msg.getDeviceId().getId());
        if (!actualDid.equals(configuration.getDid()))
            return false;
        paramContext.clear();
        FromDeviceMsg deviceMsg = msg.getPayload();
        if (deviceMsg instanceof TelemetryUploadRequest){
            TelemetryUploadRequest telemetryMsg = (TelemetryUploadRequest) deviceMsg;
            for (Map.Entry<Long,List<KvEntry>> entry : telemetryMsg.getData().entrySet()){
                for (KvEntry kvEntry : entry.getValue()){
                    // 判断表达式中是否包含了对应属性名称
                    if (configuration.getExpression().contains(kvEntry.getKey())){
                        isMatched = true;
                        paramContext.put(kvEntry.getKey(),kvEntry.getValue());
                    }
                }
            }
            // 校验表达式
            try {
                if (isMatched)
                    return  (boolean)ctx.getExpressRunner().execute(configuration.getExpression(),paramContext,null,true,true);
            } catch (Exception e) {
                log.error("did [{}],filter name [{}],expression [{}] execute failed.",actualDid,configuration.getName(),configuration.getExpression());
            }
        }
        return false;
    }

    @Override
    public void init(AttributeFilterConfiguration configuration) {
        this.configuration = configuration;
        this.paramContext = new DefaultContext<>();
    }

    @Override
    public String getName(){
        if (configuration != null)
            return configuration.getName();
        else
            return "";
    }

    @Override
    public boolean isMatched() {
        return isMatched;
    }
}
