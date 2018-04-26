package com.comba.web.controller.api;

import com.comba.server.common.data.Device;
import com.comba.server.common.data.device.*;
import com.comba.server.common.data.rest.CategoryRest;
import com.comba.server.common.data.rest.CategoryTemplateRest;
import com.comba.server.common.data.rest.DatapointRest;
import com.comba.server.common.data.rest.DeviceRest;
import com.comba.server.common.msg.rest.ResponseEntity;
import com.comba.server.dao.device.CategoryService;
import com.comba.server.dao.device.DeviceService;
import com.comba.server.dao.device.LatestDataService;
import com.comba.server.dao.device.ProductService;
import com.comba.server.dao.device.template.DeviceTemplateService;
import com.comba.server.dao.model.device.DeviceEntity;
import com.comba.server.exception.IoTErrorCode;
import com.comba.web.security.jwt.JwtTokenUtil;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * DeviceController 处理REST请求
 *
 * @author maozhihui
 * @create 2017-10-03 9:51
 **/
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class DeviceRestController {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DeviceTemplateService templateService;

    @Autowired
    private LatestDataService latestDataService;



    /**
     * 增加设备接口
     * @param pid
     * @param reqBody
     * @param token
     * @return
     */
    @PostMapping(value = "/{pid}/device",produces = "application/json")
    public ResponseEntity addDevice(@PathVariable(value = "pid") String pid,
                                    @RequestBody DeviceRest reqBody,
                                    @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        String tenantId = tokenUtil.getIdFromToken(token);
        if (StringUtils.isBlank(tenantId))
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"tenantId is invalid.","{}");
        try {
            // 判断产品是否存在
            Product product = productService.getOne(pid);
            if (product == null){
                log.error("product id [{}] is not exists.",pid);
                return new ResponseEntity<>(IoTErrorCode.item_not_found.getErrorCode(),"pid is not exists","{}");
            }
            if (reqBody.getTypeCode() == null){
                log.error("typeCode is null.");
                return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"typeCode is null","{}");
            }
            // 判断参数模板是否存在
            DeviceTemplate template = templateService.findByCategoryCode(reqBody.getTypeCode());
            if (template == null){
                log.error("typeCode [{}] has not template.",reqBody.getTypeCode());
                return new ResponseEntity<>(IoTErrorCode.item_not_found.getErrorCode(),"typeCode has not template","{}");
            }
            // 判断设备是否己存在
            StringBuilder data = new StringBuilder("{\"id\":\"");
            Optional<Device> existDevice = deviceService.findDeviceByHardIdentity(reqBody.getHardIdentity());
            if (existDevice.isPresent()){
                data.append(existDevice.get().getDevId()).append("\"}");
            }else {
                Device device = new Device();
                device.setTenantId(tenantId);
                device.setProductId(pid);
                device.setName(reqBody.getName());
                device.setHardIdentity(reqBody.getHardIdentity());
                device.setDeviceTemplateId(template.getDeviceTemplateId());
                device.setDeviceTemplateName(template.getName());
                device.setSn(reqBody.getSn());
                device.setDescription(reqBody.getDescription());
                device.setCreateTime(new Date());
                device.setUpdateTime(new Date());
                device.setPosition(reqBody.getPosition());
                if (reqBody.isGateway())
                    device.setIsGateWay(1);
                else
                    device.setIsGateWay(0);
                try {
                    device = deviceService.saveDevice(device);
                }catch (Exception e){
                    log.error("save device failed,cause [{}]",e.getMessage());
                    return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),e.getMessage(),"{}");
                }

                if (StringUtils.isBlank(device.getDevId())){
                    log.error("tenant id [{}] add device name [{}] failed.",tenantId,reqBody.getName());
                    return new ResponseEntity<>(IoTErrorCode.server_error.getErrorCode(),"server error","{}");
                }
                data.append(device.getDevId()).append("\"}");
            }
            return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success",data.toString());
        }catch (Exception e){
            log.error("add device failed,cause [{}]",e.getMessage());
            return new ResponseEntity<>(IoTErrorCode.server_error.getErrorCode(),e.getMessage(),"{}");
        }
    }

    /**
     * 删除设备
     * @param pid
     * @param did
     * @param token
     * @return
     */
    @DeleteMapping(value = "/{pid}/device/{did}",produces = "application/json")
    public ResponseEntity deleteDevice(@PathVariable(value = "pid") String pid,
                                       @PathVariable(value = "did") String did,
                                       @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        deviceService.delete(did,pid);
        return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success","{}");
    }

    /**
     * 更新设备信息
     * @param pid
     * @param did
     * @param reqBody
     * @param token
     * @return
     */
    @PutMapping(value = "/{pid}/device/{did}",produces = "application/json")
    public ResponseEntity updateDevice(@PathVariable(value = "pid") String pid,
                                       @PathVariable(value = "did") String did,
                                       @RequestBody DeviceRest reqBody,
                                       @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        List<Device> devices = deviceService.findByIds(Arrays.asList(did));
        if (devices.isEmpty()){
            log.error("device [{}] is not exists.",did);
            return new ResponseEntity<>(IoTErrorCode.item_not_found.getErrorCode(),"device is not exists","{}");
        }
        Device device = devices.get(0);
        device.setName(reqBody.getName());
        device.setDescription(reqBody.getDescription());
        deviceService.save(device);
        return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success","{}");
    }

    /**
     * 获取产品下的设备列表
     * @param pid
     * @param token
     * @return
     */
    @GetMapping(value = "/{pid}/devices",produces = "application/json")
    public ResponseEntity getDevices(@PathVariable(value = "pid") String pid,
                                     @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        try {
            List<DeviceEntity> devices = deviceService.findByProductId(pid);
            List<DeviceRest> res = new ArrayList<>();
            for (DeviceEntity entity : devices){
                DeviceRest rest = new DeviceRest(entity.getDevId(),entity.getName(),
                                    entity.getHardIdentity(),entity.getDeviceTemplateEntity().getCategoryEntity().getCode(),
                                    entity.getDeviceTemplateId(),entity.getSn(),
                                    (entity.getIsGateWay()==1 ? true : false),entity.getPosition(),entity.getDescription());
                res.add(rest);
            }
            return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success",res);
        }catch (Exception e){
            log.error("get device failed,cause [{}]",e.getMessage());
            return new ResponseEntity<>(IoTErrorCode.server_error.getErrorCode(),e.getMessage(),"{}");
        }
    }

    /**
     * 同步设备类型接口
     * @param token
     * @return
     */
    @GetMapping(value = "/device/types",produces = "application/json")
    public ResponseEntity getDeviceTypes(@RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        List<Category> categories;
        List<CategoryRest> categoryRests = new ArrayList<>();
        try {
            categories = categoryService.findAll();
            for (Category category : categories){
                categoryRests.add(new CategoryRest(category.getCategoryId(),category.getParentId(),
                        category.getCode(),category.getName()));
            }
        }catch (Exception e){
            log.error("find All category failed,cause [{}]",e.getMessage());
            return new ResponseEntity<>(IoTErrorCode.server_error.getErrorCode(),e.getMessage(),"{}");
        }

        return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success",categoryRests);
    }

    /**
     * 同步获取类型模板数据
     * @param token
     * @return
     */
    @ApiOperation(value = "同步获取类型模板数据", notes = " type:0-控制参数，1-遥测参数")
    @GetMapping(value = "device/typeTemplates",produces = "application/json")
    public ResponseEntity getCategoryTemplates(@RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        List<CategoryTemplateRest> res = templateService.findAllTemplate();
        return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success",res);
    }

    /*@GetMapping(value = "/{pid}/devices/datapoints",produces = "application/json")
    public ResponseEntity getDatapointsByPid(@PathVariable(value = "pid") String pid,
                                             @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        List<DeviceEntity> devices = deviceService.findByProductId(pid);
        if (devices.isEmpty()){
            log.warn("pid [{}] has not find device.",pid);
            return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success","{}");
        }
        List<DatapointRest> datapointRests = new ArrayList<>();
        for (DeviceEntity entity : devices){
            DatapointRest rest = new DatapointRest(entity.getDevId());
            List<AttributeData> attributeDatas = historyDataService.getLatestDeviceData(entity.getDevId());
            for (AttributeData data : attributeDatas){
                DatapointRest.Datapoint datapoint = new DatapointRest.Datapoint(data.getName(),data.getCurrentValue(),
                                                    DATE_FORMAT.format(new Date(data.getUpdateTime())));
                rest.getDatapoints().add(datapoint);
            }
            datapointRests.add(rest);
        }
        return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success",datapointRests);
    }*/

    @GetMapping(value = "/{pid}/devices/{did}/datapoints",produces = "application/json")
    public ResponseEntity getDatapointsByDid(@PathVariable(value = "pid") String pid,
                                             @PathVariable(value = "did") String did,
                                             @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        List<AttributeData> attributeDatas = latestDataService.getLatestDeviceData(did);
        List<DatapointRest.Datapoint> datapoints = new ArrayList<>();
        for (AttributeData data : attributeDatas){
            DatapointRest.Datapoint datapoint = new DatapointRest.Datapoint(data.getName(),data.getCurrentValue(),
                    DATE_FORMAT.format(new Date(data.getUpdateTime())));
            datapoints.add(datapoint);
        }
        return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success",datapoints);
    }

    /**
     * 通过传入设备ID列表获取各设备数据点
     * @param dids
     * @param token
     * @return
     */
    @PostMapping(value = "/devices/datapoints",produces = "application/json")
    public ResponseEntity getDatapointsByDids(@RequestBody List<String> dids,
                                              @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }

        List<DatapointRest> datapointRests = new ArrayList<>();
        for (String did : dids){
            DatapointRest rest = new DatapointRest(did);
            List<AttributeData> attributeDatas = latestDataService.getLatestDeviceData(did);
            for (AttributeData data : attributeDatas){
                DatapointRest.Datapoint datapoint = new DatapointRest.Datapoint(data.getName(),data.getCurrentValue(),
                        DATE_FORMAT.format(new Date(data.getUpdateTime())));
                rest.getDatapoints().add(datapoint);
            }
            datapointRests.add(rest);
        }
        return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success",datapointRests);
    }

    @PostMapping(value = "/devices/datapoints/test",produces = "application/json")
    public ResponseEntity getDatapointsByDidsTest(@RequestBody String dids,
                                              @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        if (StringUtils.isBlank(dids)){
            log.error("did [{}] is empty.",dids);
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"dids is empty.","{}");
        }
        List<DatapointRest> datapointRests = new ArrayList<>();
        JsonElement element = new JsonParser().parse(dids);
        if (element.isJsonObject()){
            JsonObject object = element.getAsJsonObject();
            JsonArray didArray = object.getAsJsonArray("dids");
            for (int i = 0; i < didArray.size(); i++){
                String did = didArray.get(i).getAsString();
                DatapointRest rest = new DatapointRest(did);
                List<AttributeData> attributeDatas = latestDataService.getLatestDeviceData(did);
                for (AttributeData data : attributeDatas){
                    DatapointRest.Datapoint datapoint = new DatapointRest.Datapoint(data.getName(),data.getCurrentValue(),
                            DATE_FORMAT.format(new Date(data.getUpdateTime())));
                    rest.getDatapoints().add(datapoint);
                }
                datapointRests.add(rest);
            }
        }
        return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success",datapointRests);
    }

    /**
     * 获取设备的最新上报时间
     *
     * @param dids
     * @param token
     * @return
     */
    @GetMapping(value = "/devices/latestUploadTime",produces = "application/json")
    public ResponseEntity getDeviceLatestUpdateTime(@RequestParam(value = "dids") String dids,
                                                   @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        if (StringUtils.isBlank(dids)){
            log.error("did [{}] is empty.",dids);
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"dids is empty.","{}");
        }

        List<?> ret =  latestDataService.getDeviceLatestUpdateTime(Lists.newArrayList(dids.split(",")));
        return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success",ret);
    }

    @PostMapping(value = "/device/status",produces = "application/json")
    public ResponseEntity getDeviceStatus(@RequestBody List<String> dids,
                                          @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }

        List<OnlineState> statesData = new ArrayList<>();
        for (String did : dids){
            Device device = deviceService.getOne(did);
            if (device != null){
                OnlineState state = new OnlineState(did,device.getStatus());
                statesData.add(state);
            }
        }
        return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success",statesData);
    }

}
