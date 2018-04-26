package com.comba.server.common.msg.forward;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;

/**
 * @author maozhihui
 * @Description:
 * @create 2018/1/2 19:34
 **/
@AllArgsConstructor
public class StatusForwardMsg implements ForwardMsg {

    private static final Gson GSON = new Gson();

    private String did;
    private int status;
    private long ts;

    @Override
    public String toJsonData() {
        return GSON.toJson(this);
    }
}
