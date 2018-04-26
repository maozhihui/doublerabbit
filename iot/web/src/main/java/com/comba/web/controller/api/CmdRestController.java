package com.comba.web.controller.api;

import com.comba.server.actors.service.ActorService;
import com.comba.server.common.data.Device;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.rest.SetCmdRest;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.msg.cmd.BasicCommandMsg;
import com.comba.server.common.msg.cmd.SetCmdRequestMsg;
import com.comba.server.common.msg.rest.ResponseEntity;
import com.comba.server.dao.device.DeviceService;
import com.comba.server.dao.device.LatestDataService;
import com.comba.server.exception.IoTErrorCode;
import com.comba.web.security.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/12/20 13:34
 **/
@Slf4j
@RestController
@RequestMapping("/api/v1/cmd")
public class CmdRestController {

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private ActorService actorService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private LatestDataService latestDataService;

    @PostMapping(value = "/set",produces = "application/json")
    public ResponseEntity setCmdCtrl(@RequestBody SetCmdRest reqBody,
                                     @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        String tenantId = tokenUtil.getIdFromToken(token);
        if (StringUtils.isBlank(tenantId))
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"tenantId is invalid.","{}");
        log.info("setCmdCtrl reqBody [{}]",reqBody);
        for (SetCmdRest.Attribute attribute : reqBody.getContent()){

            SetCmdRequestMsg setCmdMsg= null;
            for (Map.Entry<String,String> entry : attribute.getData().entrySet()){
                String hardIdentity = deviceService.findByDevIdAndAttributeName(attribute.getDid(),entry.getKey());
                if (hardIdentity == null){
                    log.warn("device or attribute id [{}] is not exists.",attribute.getDid());
                    continue;
                }

                setCmdMsg = new SetCmdRequestMsg(new DeviceId(UUIDUtils.toUUID(attribute.getDid())));
                setCmdMsg.addAttribute(entry.getKey(),entry.getValue());
                setCmdMsg.setHardIdentity(hardIdentity);
            }
            if (setCmdMsg != null){
                BasicCommandMsg<SetCmdRequestMsg> cmdMsg = new BasicCommandMsg<>(new TenantId(UUIDUtils.toUUID(tenantId)),setCmdMsg.getDeviceId(),null,setCmdMsg);
                cmdMsg.setCreateTime(System.currentTimeMillis());
                actorService.onCommandProcess(cmdMsg);
            }

            // TODO 目前先添加到最新表中，后续根据执行状态上报
            //latestDataService.save(device.getDevId(),setCmdMsg.getAttributes());
        }

        return new ResponseEntity(IoTErrorCode.SUCCESS.getErrorCode(),"success","");
    }
}
