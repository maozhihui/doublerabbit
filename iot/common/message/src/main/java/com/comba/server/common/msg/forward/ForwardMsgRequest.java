package com.comba.server.common.msg.forward;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author maozhihui
 * @Description:
 * @create 2018/1/2 19:32
 **/
@Data
@AllArgsConstructor
public class ForwardMsgRequest {

    private String tenantId;
    private final String eventType;
    private final String data;
}
