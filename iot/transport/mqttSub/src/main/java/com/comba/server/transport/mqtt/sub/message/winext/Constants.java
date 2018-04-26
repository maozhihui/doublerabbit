package com.comba.server.transport.mqtt.sub.message.winext;

/**
 * @author maozhihui
 * @Description: 唯传协议适配相关常量
 * @create 2018/1/26 10:26
 **/
public class Constants {

    // 地磁相关数据包头定义
    public static final int PACK_DICI_HEARTBEAT = 0x03;
    public static final int PACK_DICI_CONFIG = 0x01;
    public static final int PACK_DICI_STATUS = 0x02;
    public static final int PACK_DICI_STATUS_CHANGED = 0x04;

    // fport定义相关设备类型
    // 温湿度
    public static final int TEMPERATURE_HUMIDITY_TYPE = 4;
    // 地磁
    public static final int GEOMAGNETIC_TYPE = 12;
    // 泰和安烟感
    public static final int SMOKE01_TYPE = 2;
    // 唯传烟感
    public static final int SMOKE02_TYPE = 19;
    // 唯传井盖
    public static final int MANHOLE_COVER_TYPE = 31;

}
