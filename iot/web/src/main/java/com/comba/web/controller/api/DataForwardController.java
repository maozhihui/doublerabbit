package com.comba.web.controller.api;

import com.comba.server.actors.service.ActorService;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.plugin.ComponentLifecycleEvent;
import com.comba.server.common.data.rest.DataForwardRest;
import com.comba.server.common.data.rule.ForwardRule;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.msg.rest.ResponseEntity;
import com.comba.server.dao.rule.ForwardRuleService;
import com.comba.server.exception.IoTErrorCode;
import com.comba.web.security.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author maozhihui
 * @Description: 添加数据转发规则
 * @create 2018/1/2 11:12
 **/
@Slf4j
@RestController
@RequestMapping("/api/v1/dataForward")
public class DataForwardController {

    private static final String STATUS_EVENT = "status";
    private static final String DATA_EVENT = "data";

    @Autowired
    private ActorService actorService;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private ForwardRuleService forwardRuleService;

    @PostMapping(value = "",produces = "application/json")
    public ResponseEntity addDataForward(@RequestBody DataForwardRest reqBody,
                                         @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        if (!reqBody.getEvent().equals(STATUS_EVENT) &&
                !reqBody.getEvent().equals(DATA_EVENT)){
            log.warn("this event [{}] is not support",reqBody.getEvent());
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"event is invalid.","{}");
        }
        String tenantId = tokenUtil.getIdFromToken(token);
        if (StringUtils.isBlank(tenantId))
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"tenantId is invalid.","{}");
        Optional<ForwardRule> forwardRule = forwardRuleService.findByTenantIdAndEvent(tenantId,reqBody.getEvent());
        ForwardRule rule;
        if (forwardRule.isPresent()){
            log.warn("forward rule is exists,tenantId [{}],event [{}]",tenantId,reqBody.getEvent());
            rule = forwardRule.get();
            rule.setName(reqBody.getName());
            rule.setDst(reqBody.getDst());
            rule.setUpdateTime(new Date());
        }else {
            rule = new ForwardRule();
            rule.setTenantId(tenantId);
            rule.setName(reqBody.getName());
            rule.setEvent(reqBody.getEvent());
            rule.setType(reqBody.getType());
            rule.setDst(reqBody.getDst());
            Date now = new Date();
            rule.setCreateTime(now);
            rule.setUpdateTime(now);
        }

        rule = forwardRuleService.save(rule);
        actorService.onForwardRuleStateChange(new TenantId(UUIDUtils.toUUID(tenantId)),
                                                rule.getId(), ComponentLifecycleEvent.CREATED);
        return new ResponseEntity(IoTErrorCode.SUCCESS.getErrorCode(),"success","{}");
    }

    @DeleteMapping(value = "/{id}",produces = "application/json")
    public ResponseEntity deleteDataForward(@PathVariable(value = "id") String ruleId,
                                            @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        String tenantId = tokenUtil.getIdFromToken(token);
        if (StringUtils.isBlank(tenantId))
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"tenantId is invalid.","{}");
        forwardRuleService.deleteById(ruleId);
        actorService.onForwardRuleStateChange(new TenantId(UUIDUtils.toUUID(tenantId)),
                                                ruleId, ComponentLifecycleEvent.DELETED);
        return new ResponseEntity(IoTErrorCode.SUCCESS.getErrorCode(),"success","{}");
    }


    /**
     * 数据转发接口查询
     *
     * @param token
     * @return
     */
    @GetMapping("")
    public ResponseEntity getDataForward(@RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        String tenantId = tokenUtil.getIdFromToken(token);
        if (StringUtils.isBlank(tenantId))
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"tenantId is invalid.","{}");
        List<ForwardRule> list = forwardRuleService.findAll();

        return new ResponseEntity(IoTErrorCode.SUCCESS.getErrorCode(),"success",list);
    }
}
