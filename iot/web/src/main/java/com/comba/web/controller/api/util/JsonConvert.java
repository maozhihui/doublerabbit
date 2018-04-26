package com.comba.web.controller.api.util;

import com.comba.server.common.data.Device;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.data.rest.ProductRest;
import com.comba.server.common.msg.rest.ResponseEntity;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;

import java.util.Optional;
import java.util.UUID;

/**
 * JsonConvert 工具类
 *
 * @author maozhihui
 * @create 2017-10-03 12:01
 **/
public class JsonConvert {

    private static final Gson GSON = new Gson();

    public static Device convertToDevice(JsonElement jsonElement){
        JsonObject object = jsonElement.getAsJsonObject();
        if (object == null)
            throw new IllegalArgumentException("can not get as a Json Object.");
        Device device = new Device();
        try {
            String devTypeCode = object.getAsJsonPrimitive("devTypeCode").getAsString();
            device.setCategoryId(UUIDUtils.toUUID(UUID.fromString(devTypeCode)));
            String devName = object.getAsJsonPrimitive("devName").getAsString();
            device.setName(devName);
            String hardIdentity = object.getAsJsonPrimitive("hardIdentity").getAsString();
            device.setHardIdentity(hardIdentity);
            String sn = object.getAsJsonPrimitive("sn").getAsString();
            device.setSn(sn);
            String description = "";
            if(object.get("description") != null)
                description = object.getAsJsonPrimitive("description").getAsString();
            device.setDescription(description);
            String tenantId = object.getAsJsonPrimitive("tenantId").getAsString();
            device.setTenantId(UUIDUtils.toUUID(UUID.fromString(tenantId)));
        }catch (Exception e){
            throw new IllegalArgumentException("attribute value invalid.");
        }
        return device;
    }

    public static String toJson(ResponseEntity response){
        return GSON.toJson(response);
    }

    public static Optional<ProductRest> convertToProduct(String jsonStr){
        if (StringUtils.isBlank(jsonStr))
            return Optional.ofNullable(null);
        ProductRest productRest = GSON.fromJson(jsonStr,ProductRest.class);
        return Optional.of(productRest);
    }
}
