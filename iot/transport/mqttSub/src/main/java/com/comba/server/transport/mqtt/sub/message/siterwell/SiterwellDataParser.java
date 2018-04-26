package com.comba.server.transport.mqtt.sub.message.siterwell;

import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.data.kv.StringDataEntry;
import com.comba.server.transport.mqtt.sub.message.DataParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/12/28 20:56
 **/
@Slf4j
public class SiterwellDataParser implements DataParser{

    private static final String SMOKE_ATTR = "smoke";
    private static final String COMBUSTIBLE_ATTR = "combustible";
    // 烟雾报警器类型
    private static final int SMOKE_TYPE = 1;
    // 可燃气体报警器类型
    private static final int COMBUSTIBLE_TYPE = 4;
    private static final int ALARM_VAL = 1;
    private static final int NORMAL_VAL = 7;

    @Override
    public List<KvEntry> parserData(int devType, String data) {
        List<KvEntry> kvEntries = new ArrayList<>();
        if (StringUtils.isBlank(data)) {
            log.error("data is empty.");
            return kvEntries;
        }
        byte[] dataBytes = decode(data);
        int typeData = dataBytes[1] >> 4;
        int valueData = dataBytes[1] & 0x0F;
        String key;
        String value;
        if (typeData == SMOKE_TYPE){
            key = SMOKE_ATTR;
        }else if (typeData == COMBUSTIBLE_TYPE){
            key = COMBUSTIBLE_ATTR;
        }else {
            log.warn("type data [{}] not support",typeData);
            return kvEntries;
        }
        if (valueData == ALARM_VAL){
            value = "1";
        }else if (valueData == NORMAL_VAL){
            value = "0";
        }else {
            log.warn("value type [{}] not support",valueData);
            return kvEntries;
        }
        kvEntries.add(new StringDataEntry(key,value));
        return kvEntries;
    }

    private byte[] decode(String str){
        return Base64.getDecoder().decode(str);
    }
}
