package com.comba.mantun.service;

import static com.comba.mantun.message.Constants.*;

import com.comba.mantun.message.BoxBaseInfo;
import com.comba.mantun.message.ChannelInfo;
import com.comba.mantun.message.ResponseBean;
import com.comba.mantun.session.PlatformSessionCtx;
import com.comba.mantun.util.RequestCommon;
import com.comba.server.common.data.Device;
import com.comba.server.common.data.kv.*;
import com.comba.server.common.msg.core.*;
import com.comba.server.common.msg.session.*;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * 检测任务，负责查询当前电箱及电箱下的线程实时信息，并上报数据
 * @author maozhihui
 * @Description:
 * @create 2018/2/25 11:33
 **/
@Slf4j
public class CheckTask implements Runnable {

    // 请求客户端
    private final RestTemplate client;
    // 曼顿设备相关配置信息
    private final AuthorizeConfig config;
    // 会话上下文
    private final PlatformSessionCtx ctx;

    public CheckTask(PlatformSessionCtx ctx,RestTemplate restTemplate,AuthorizeConfig config){
        this.ctx = ctx;
        this.client = restTemplate;
        this.config = config;
    }

    @Override
    public void run() {
        // 查询电箱及电箱下的线程开关信息
        getBoxs();
        getBoxRealInfo();
    }

    /**
     * 获取所有电箱信息
     */
    public void getBoxs(){
        Map<String, Object> paramMap = new TreeMap<String, Object>();
        paramMap.put(PROJECTCODE_VAR, config.getProjectCode());
        paramMap.put(METHOD_VAR, GET_BOXES_METHOD);
        String requestParam = RequestCommon.getRequestParam(paramMap);
        try {
            ResponseEntity<String> res = client.exchange(config.getApiUri(), HttpMethod.POST,
                    RequestCommon.getHttpEntity(requestParam), String.class);
            log.info("getboxs body [{}]",res.getBody());
            ResponseBean<List<BoxBaseInfo>> responseBean = GSON.fromJson(res.getBody(), new TypeToken<ResponseBean<List<BoxBaseInfo>>>(){}.getType());
            if (responseBean.isSuccess()) {
                log.info("mantun get all boxs success");
                for (BoxBaseInfo boxBaseInfo : responseBean.getData()) {
                    boxMapInfo.put(boxBaseInfo.getMac(), boxBaseInfo);
                }
            }else {
                log.warn("mantun get all boxs failed,cause [{}]",responseBean.getMessage());
            }
        }catch (Exception e){
            log.error("mantun getBoxs failed,cause [{}]",e.getMessage());
        }

    }

    /**
     * 获取电箱实时线路信息
     */
    public void getBoxRealInfo(){
        Map<String, Object> paramMap = new TreeMap<String, Object>();
        try {
            for (Map.Entry<String, BoxBaseInfo> entry : boxMapInfo.entrySet()) {
                paramMap.clear();
                paramMap.put(PROJECTCODE_VAR, config.getProjectCode());
                paramMap.put(METHOD_VAR, GET_BOXES_REALTIME_METHOD);
                paramMap.put(MAC_VAR, entry.getKey());
                String requestParam = RequestCommon.getRequestParam(paramMap);
                ResponseEntity<String> res = client.exchange(config.getApiUri(), HttpMethod.POST,
                        RequestCommon.getHttpEntity(requestParam), String.class);
                log.info("mantun getboxs chanel mac [{}] body [{}]",entry.getKey(),res.getBody());
                ResponseBean<List<ChannelInfo>> responseBean = GSON.fromJson(res.getBody(), new TypeToken<ResponseBean<List<ChannelInfo>>>(){}.getType());
                if (responseBean.isSuccess()) {
                    log.info("mantun getboxs chanel success");
                    channelMapInfo.put(entry.getKey(), responseBean.getData());
                    // 对线路开关设备进行在/离线状态检查与数据上报
                    for (ChannelInfo channelInfo : responseBean.getData()){
                        String hardIdentity = entry.getKey() + "-" + String.valueOf(channelInfo.getAddr());
                        if (ctx.checkDeviceExist(hardIdentity)){
                            if (channelInfo.isOnline()){
                                register(ctx.getDevice());
                            }else if (!channelInfo.isOnline()){
                                deregister(ctx.getDevice());
                            }
                            if (channelInfo.isOnline()){
                                uploadTelemetryData(ctx.getDevice(),channelInfo);
                                updateAttributes(ctx.getDevice(),channelInfo);
                            }else {
                                log.warn("mantun box mac [{}],address [{}] is offline.",entry.getKey(),channelInfo.getAddr());
                            }
                        }else {
                            log.warn("mantun box switch hardIdentity [{}] is not exists",hardIdentity);
                        }
                    }
                }else {
                    log.warn("mantun getboxs chanel failed,cause [{}]",responseBean.getMessage());
                }
            }
        }catch (Exception e){
            log.error("mantun getBoxRealInfo failed,cause [{}]",e.getMessage());
        }
    }

    /**
     * 进行注册
     */
    private void register(Device device){
        BasicRegisterRequest request = new BasicRegisterRequest(device.getHardIdentity());
        AdaptorToSessionActorMsg msg = new BasicAdaptorToSessionActorMsg(ctx, request);
        ctx.getProcessor().process(new BasicToDeviceActorSessionMsg(device, msg));
    }

    /**
     * 进行去注册
     */
    private void deregister(Device device){
        BasicDeregisterRequest request = new BasicDeregisterRequest("",device.getHardIdentity());
        AdaptorToSessionActorMsg msg = new BasicAdaptorToSessionActorMsg(ctx, request);
        ctx.getProcessor().process(new BasicToDeviceActorSessionMsg(device, msg));
    }

    /**
     * 上报采集数据信息
     * @param device
     * @param realInfo
     */
    private void uploadTelemetryData(Device device, ChannelInfo realInfo){
        BasicTelemetryUploadRequest request = new BasicTelemetryUploadRequest(device.getDevId());
        long ts = System.currentTimeMillis();
        request.add(ts,new DoubleDataEntry(WATT_ATTR,(double) realInfo.getAW()));
        request.add(ts,new DoubleDataEntry(VOLTAGE_ATTR,(double) realInfo.getAV()));
        request.add(ts,new DoubleDataEntry(ELECTRICITY_ATTR,(double) realInfo.getAA()));
        request.add(ts,new DoubleDataEntry(TEMPERATURE_ATTR,(double) realInfo.getAT()));
        request.add(ts,new DoubleDataEntry(POWER_ATTR,(double) realInfo.getPower()));

        AdaptorToSessionActorMsg msg = new BasicAdaptorToSessionActorMsg(ctx, request);
        ctx.getProcessor().process(new BasicToDeviceActorSessionMsg(ctx.getDevice(), msg));
    }


    /**
     * 更新控制类属性信息
     * @param device
     * @param realInfo
     */
    private void updateAttributes(Device device,ChannelInfo realInfo){
        long ts = System.currentTimeMillis();

        BasicUpdateAttributesRequest attrUpdateRequest = new BasicUpdateAttributesRequest(device.getDevId(), BasicRequest.DEFAULT_REQUEST_ID);
        AttributeKvEntry attributeKvEntry = new BaseAttributeKvEntry(new StringDataEntry(SWITCH_ATTR,(realInfo.isOc() == true ? "1" : "0")),ts);
        attrUpdateRequest.add(attributeKvEntry);
        AdaptorToSessionActorMsg msg = new BasicAdaptorToSessionActorMsg(ctx, attrUpdateRequest);
        ctx.getProcessor().process(new BasicToDeviceActorSessionMsg(ctx.getDevice(), msg));
    }
}
