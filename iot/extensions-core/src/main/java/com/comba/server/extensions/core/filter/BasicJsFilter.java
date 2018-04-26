package com.comba.server.extensions.core.filter;

import lombok.extern.slf4j.Slf4j;
import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.msg.device.ToDeviceActorMsg;

import com.comba.server.extensions.api.rules.RuleContext;
import com.comba.server.extensions.api.rules.RuleFilter;

import javax.script.ScriptException;
import java.text.SimpleDateFormat;

/**
 * @author Andrew Shvayka
 */
@Slf4j
public abstract class BasicJsFilter implements RuleFilter<JsFilterConfiguration> {

    protected JsFilterConfiguration configuration;
    protected NashornJsEvaluator evaluator;

    @Override
    public void init(JsFilterConfiguration configuration) {
        this.configuration = configuration;
        initEvaluator(configuration);
    }

    @Override
    public boolean filter(RuleContext ctx, ToDeviceActorMsg msg) {
        try {
            return doFilter(ctx, msg);
        } catch (ScriptException e) {
            log.warn("RuleFilter evaluation exception: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    protected abstract boolean doFilter(RuleContext ctx, ToDeviceActorMsg msg) throws ScriptException;

    @Override
    public void resume() {
        initEvaluator(configuration);
    }

    @Override
    public void suspend() {
        destroyEvaluator();
    }

    @Override
    public void stop() {
        destroyEvaluator();
    }

    private void initEvaluator(JsFilterConfiguration configuration) {
        evaluator = new NashornJsEvaluator(configuration.getFilter());
    }

    private void destroyEvaluator() {
        if (evaluator != null) {
            evaluator.destroy();
        }
    }

    protected static Object getValue(KvEntry attr) {
        switch (attr.getDataType()) {
            case STRING:
                return attr.getStrValue().get();
            case LONG:
                return attr.getLongValue().get();
            case DOUBLE:
                return attr.getDoubleValue().get();
            case BOOLEAN:
                return attr.getBooleanValue().get();
        }
        return null;
    }

    @Override
    public String getName(){
        return "JS";
    }

    @Override
    public boolean isMatched(){
        return false;
    }
}
