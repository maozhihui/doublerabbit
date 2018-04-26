package com.comba.mantun.session;

import com.comba.mantun.util.RequestCommon;
import com.comba.server.common.data.id.SessionId;
import com.comba.server.common.msg.cmd.CommandMsgType;
import com.comba.server.common.msg.cmd.SetCmdRequestMsg;
import com.comba.server.common.msg.core.BasicResponseMsg;
import com.comba.server.common.msg.core.ToDeviceRequestMsg;
import com.comba.server.common.msg.session.SessionActorToAdaptorMsg;
import com.comba.server.common.msg.session.SessionCtrlMsg;
import com.comba.server.common.msg.session.SessionType;
import com.comba.server.common.msg.session.ex.SessionException;
import com.comba.server.common.transport.SessionMsgProcessor;
import com.comba.server.common.transport.auth.DeviceAuthService;
import com.comba.server.common.transport.session.DeviceAwareSessionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.TreeMap;

import static com.comba.mantun.message.Constants.*;

/**
 * 平台对接会话
 * @author maozhihui
 * @Description:
 * @create 2018/2/25 9:27
 **/
@Slf4j
public class PlatformSessionCtx extends DeviceAwareSessionContext{

    private final SessionId sessionId;
    private final RestTemplate client;

    public PlatformSessionCtx(SessionMsgProcessor processor,DeviceAuthService authService,
                              RestTemplate restTemplate){
        super(processor,authService);
        this.sessionId = new PlatformSessionId();
        this.client = restTemplate;
    }

    @Override
    public SessionType getSessionType() {
        return SessionType.ASYNC;
    }

    @Override
    public void onMsg(SessionActorToAdaptorMsg msg) throws SessionException {
        if(msg.getMsg() instanceof BasicResponseMsg){
            //TODO 此处不需要处理
            log.debug("session id [{}],msg [{}]",msg.getSessionId(),msg.getMsg());
        }
        else if(msg.getMsg() instanceof ToDeviceRequestMsg) {
            // 进行设备命令下发处理
            ToDeviceRequestMsg reqMsg = (ToDeviceRequestMsg) msg.getMsg();
            putBoxControl(reqMsg);
        }
    }

    @Override
    public void onMsg(SessionCtrlMsg msg) throws SessionException {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public long getTimeout() {
        return 0;
    }

    @Override
    public String getRegisterToken() {
        return "";
    }

    @Override
    public SessionId getSessionId() {
        return sessionId;
    }

    /**
     * 进行电箱线路控制
     */
    public void putBoxControl(ToDeviceRequestMsg request){
        CommandMsgType cmdMsgType = request.getCmdMsg().getData().getMsgType();

        if(cmdMsgType == CommandMsgType.SET_REQUEST){
            SetCmdRequestMsg setCmdMsg = (SetCmdRequestMsg) request.getCmdMsg().getData();
            String hardIdentity = setCmdMsg.getHardIdentity();
            Map<String,String> attributeMap = setCmdMsg.getAttributes();
            String[] splitArray = hardIdentity.split("-");
            log.info("mantun set command,box mac [{}],add [{}]",splitArray[0],splitArray[1]);
            if (!attributeMap.containsKey(SWITCH_ATTR)){
                log.info("mantun set command attribute error.");
                return;
            }
            String setVal = attributeMap.get(SWITCH_ATTR);
            Map<String, Object> paramMap = new TreeMap<String, Object>();
            paramMap.put(PROJECTCODE_VAR, configuration.getProjectCode());
            paramMap.put(METHOD_VAR, PUT_BOX_CONTROL_METHOD);
            paramMap.put(CMD_VAR, "OCSWITCH");
            paramMap.put(VALUE1_VAR, (setVal.equals("1") ? "open" : "close"));
            paramMap.put(VALUE2_VAR, splitArray[1]);
            paramMap.put(MAC_VAR, splitArray[0]);
            String requestParam = RequestCommon.getRequestParam(paramMap);
            ResponseEntity<String> res = client.exchange(configuration.getApiUri(), HttpMethod.POST,
                    RequestCommon.getHttpEntity(requestParam), String.class);
            System.out.println("getboxs body="+res.getBody());
            log.info("mantun set command result [{}],box mac [{}],addr [{}],value [{}]",
                            res.getBody(),splitArray[0],splitArray[1],setVal);
        }else {
            log.warn("mantun CommandMsgType [{}] not support.",cmdMsgType);
        }
    }
}
