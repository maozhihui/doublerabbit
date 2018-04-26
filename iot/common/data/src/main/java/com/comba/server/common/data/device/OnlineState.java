package com.comba.server.common.data.device;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author maozhihui
 * @Description: 设备在线状态实体
 * @create 2018/1/30 13:04
 **/
@Data
@AllArgsConstructor
public class OnlineState implements Serializable{

    private String did;
    private int onlineState;
}
