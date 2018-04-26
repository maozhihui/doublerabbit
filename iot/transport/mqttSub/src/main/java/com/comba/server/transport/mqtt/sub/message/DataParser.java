package com.comba.server.transport.mqtt.sub.message;

import com.comba.server.common.data.kv.KvEntry;

import java.util.List;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/12/27 19:19
 **/
public interface DataParser {

    List<KvEntry> parserData(int devType,String data);
}
