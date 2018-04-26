package com.comba.web.controller.api;

import com.comba.server.actors.service.ActorService;
import com.comba.server.common.data.device.PluginJpa;
import com.comba.server.common.data.device.Rule;
import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.plugin.ComponentLifecycleEvent;
import com.comba.server.common.data.rest.RuleRest;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.msg.rest.ResponseEntity;
import com.comba.server.dao.plugin.PluginJpaService;
import com.comba.server.dao.rule.RuleJpaService;
import com.comba.server.exception.IoTErrorCode;
import com.comba.server.extensions.core.action.DeviceCtrlActionConfiguration;
import com.comba.server.extensions.core.filter.AttributeFilterConfiguration;
import com.comba.web.security.jwt.JwtTokenUtil;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author maozhihui
 * @Description:规则API接口
 * @create 2017/11/30 11:08
 **/
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class RuleRestController {

    private static final Gson GSON = new Gson();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private RuleJpaService ruleService;

    @Autowired
    private PluginJpaService pluginService;

    @Autowired
    private ActorService actorService;

    @PostMapping(value = "/linkRule",produces = "application/json")
    public ResponseEntity addRule(@RequestBody RuleRest reqBody,
                                  @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        String tenantId = tokenUtil.getIdFromToken(token);
        if (StringUtils.isBlank(tenantId) ||
                reqBody.getInput() == null ||
                reqBody.getOutput().isEmpty() ||
                StringUtils.isBlank(reqBody.getName()))
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"tenantId is invalid.","{}");

        if (ruleService.countByName(reqBody.getName()) > 0){
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"rule is exit.",reqBody.getName());
        }

        Rule rule = new Rule();
        rule.setName(reqBody.getName());
        rule.setTenantId(tenantId);
        Date date = new Date();
        rule.setCreateTime(date);
        rule.setUpdateTime(date);

        /*// 判断多输出目标判断
        if (reqBody.getOutput().size() > 1){
            log.warn("rule multi objective is not support.");
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"multi objective is not support.","{}");
        }else {
            // 判断输出目标的类型
            RuleRest.Action action = reqBody.getOutput().get(0);
            if (!action.getType().equals("devctrl")){
                log.warn("output type [{}] is not support,only support 'devctrl'.",action.getType());
                return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"output type is not support.","{}");
            }
        }*/
        RuleRest.Action validateAction = reqBody.getOutput().stream().filter(r -> (!r.getType().equals("devctrl"))).findFirst().orElse(null);
        if (validateAction != null){
            log.warn("output type [{}] is not support,only support 'devctrl'.",validateAction.getType());
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"output type is not support.","{}");
        }

        // 保存插件
        PluginJpa plugin = new PluginJpa();
        plugin.setName("cmdPlugin");
        plugin.setTenantId(tenantId);
        plugin.setClazz("com.comba.server.extensions.core.plugin.CmdPlugin");
        plugin.setConfiguration("{}");
        plugin.setCreateTime(date);
        plugin.setUpdateTime(date);
        plugin = pluginService.save(plugin);
        rule.setPluginId(plugin.getPluginId());

        RuleRest.Condition condition = reqBody.getInput();
        rule.setProcessor(condition.getGoalExpression());

        // 添加过滤条件
        List<ComponentConfiguration> filters = new ArrayList<>();
        for (RuleRest.SubCondition subCond : condition.getConditions()){
            AttributeFilterConfiguration config = new AttributeFilterConfiguration(subCond.getName(),subCond.getDid(),subCond.getExpression());
            filters.add(new ComponentConfiguration("com.comba.server.extensions.core.filter.AttributeFilter",
                                        "AttributeFilter",
                                        config));
        }
        rule.setFilters(GSON.toJson(filters));

        DeviceCtrlActionConfiguration config = new DeviceCtrlActionConfiguration();
        for (RuleRest.Action action : reqBody.getOutput()){
            DeviceCtrlActionConfiguration.CtrlEntity entity = new DeviceCtrlActionConfiguration.CtrlEntity(action.getType(),action.getDid(),action.getAttrs());
            config.add(entity);
        }
        String action = GSON.toJson(new ComponentConfiguration("com.comba.server.extensions.core.action.DeviceCtrlAction",
                                    "DeviceCtrlAction",
                                    config));
        rule.setAction(action);
        try {
            rule = ruleService.save(rule);
        }catch (Exception e){
            log.error("add rule failed,cause [{}]",e.getMessage());
            return new ResponseEntity(IoTErrorCode.bad_request_param.getErrorCode(),e.getMessage(),"");
        }
        // 通知进行动态加载规则
        actorService.onRuleStateChange(new TenantId(UUIDUtils.toUUID(tenantId)),
                                new RuleId(UUIDUtils.toUUID(rule.getRuleId())), ComponentLifecycleEvent.CREATED);
        StringBuilder data = new StringBuilder("{\"id\":\"");
        data.append(rule.getRuleId()).append("\"}");
        return new ResponseEntity(IoTErrorCode.SUCCESS.getErrorCode(),"success",data.toString());
    }


    @PutMapping(value = "/linkRule/{linkRuleId}",produces = "application/json")
    public ResponseEntity put(@PathVariable(value = "linkRuleId") String ruleId,
                              @RequestBody RuleRest reqBody,
                              @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        Rule ruleDB = ruleService.getOne(ruleId);
        if (ruleDB == null){
            log.warn("update rule,ruleId [{}] does not exists.",ruleId);
            return new ResponseEntity<>(IoTErrorCode.item_not_found.getErrorCode(),"rule is not exists.","{}");
        }

        if (reqBody.getInput() == null ||
                reqBody.getOutput().isEmpty() ||
                StringUtils.isBlank(reqBody.getName()))
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"tenantId is invalid.","{}");

        if (!ruleDB.getName().equals(reqBody.getName()) &&
                ruleService.countByName(reqBody.getName()) > 0){
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"rule is exit.",reqBody.getName());
        }

        Rule rule = new Rule();
        rule.setName(reqBody.getName());
        rule.setTenantId(ruleDB.getTenantId());
        Date date = new Date();
        rule.setCreateTime(date);
        rule.setUpdateTime(date);
        rule.setPluginId(ruleDB.getPluginId());
        rule.setRuleId(ruleDB.getRuleId());

        RuleRest.Action validateAction = reqBody.getOutput().stream().filter(r -> (!r.getType().equals("devctrl"))).findFirst().orElse(null);
        if (validateAction != null){
            log.warn("output type [{}] is not support,only support 'devctrl'.",validateAction.getType());
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"output type is not support.","{}");
        }

        RuleRest.Condition condition = reqBody.getInput();
        rule.setProcessor(condition.getGoalExpression());

        // 添加过滤条件
        List<ComponentConfiguration> filters = new ArrayList<>();
        for (RuleRest.SubCondition subCond : condition.getConditions()){
            AttributeFilterConfiguration config = new AttributeFilterConfiguration(subCond.getName(),subCond.getDid(),subCond.getExpression());
            filters.add(new ComponentConfiguration("com.comba.server.extensions.core.filter.AttributeFilter",
                    "AttributeFilter",
                    config));
        }
        rule.setFilters(GSON.toJson(filters));

        DeviceCtrlActionConfiguration config = new DeviceCtrlActionConfiguration();
        for (RuleRest.Action action : reqBody.getOutput()){
            DeviceCtrlActionConfiguration.CtrlEntity entity = new DeviceCtrlActionConfiguration.CtrlEntity(action.getType(),action.getDid(),action.getAttrs());
            config.add(entity);
        }
        String action = GSON.toJson(new ComponentConfiguration("com.comba.server.extensions.core.action.DeviceCtrlAction",
                "DeviceCtrlAction",
                config));
        rule.setAction(action);
        try {
            rule = ruleService.save(rule);
        }catch (Exception e){
            log.error("add rule failed,cause [{}]",e.getMessage());
            return new ResponseEntity(IoTErrorCode.bad_request_param.getErrorCode(),e.getMessage(),"");
        }
        // 通知进行动态加载规则
        actorService.onRuleStateChange(new TenantId(UUIDUtils.toUUID(ruleDB.getTenantId())),
                new RuleId(UUIDUtils.toUUID(rule.getRuleId())), ComponentLifecycleEvent.UPDATED);
        StringBuilder data = new StringBuilder("{\"id\":\"");
        data.append(rule.getRuleId()).append("\"}");
        return new ResponseEntity(IoTErrorCode.SUCCESS.getErrorCode(),"success",data.toString());
    }


    @DeleteMapping(value = "/rule/{linkRuleId}",produces = "application/json")
    public ResponseEntity deleteRule(@PathVariable(value = "linkRuleId") String ruleId,
                                     @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        Rule rule = ruleService.getOne(ruleId);
        if (rule == null){
            log.warn("delete rule,ruleId [{}] does not exists.",ruleId);
            return new ResponseEntity<>(IoTErrorCode.item_not_found.getErrorCode(),"rule is not exists.","{}");
        }
        pluginService.delete(rule.getPluginId());
        ruleService.delete(ruleId);
        // 删除己经加载的规则
        String tenantId = tokenUtil.getIdFromToken(token);
        actorService.onRuleStateChange(new TenantId(UUIDUtils.toUUID(tenantId)),
                                new RuleId(UUIDUtils.toUUID(ruleId)), ComponentLifecycleEvent.DELETED);
        return new ResponseEntity(IoTErrorCode.SUCCESS.getErrorCode(),"success","{}");
    }

    @GetMapping(value = "linkRule/{linkRuleId}",produces = "application/json")
    public ResponseEntity get(@PathVariable(value = "linkRuleId") String ruleId,
                              @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }

        Rule rule = ruleService.getOne(ruleId);
        if (rule == null){
            log.warn("delete rule,ruleId [{}] does not exists.",ruleId);
            return new ResponseEntity<>(IoTErrorCode.item_not_found.getErrorCode(),"rule is not exists.","{}");
        }
        return new ResponseEntity(IoTErrorCode.SUCCESS.getErrorCode(),"success",rule);
    }


    @Data
    @AllArgsConstructor
    private static class ComponentConfiguration<T> {
        private final String clazz;
        private final String name;
        private final T configuration;
    }
}
