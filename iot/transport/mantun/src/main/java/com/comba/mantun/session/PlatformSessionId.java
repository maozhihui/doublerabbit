package com.comba.mantun.session;

import com.comba.server.common.data.id.SessionId;

import java.util.UUID;

/**
 * 随机生成一个UUID
 * @author maozhihui
 * @Description:
 * @create 2018/2/25 10:46
 **/
public class PlatformSessionId implements SessionId {

    private UUID uuid;

    public PlatformSessionId(){
        this.uuid = UUID.randomUUID();
    }

    @Override
    public String toUidStr() {
        return uuid.toString();
    }

    @Override
    public String toString(){
        return toUidStr();
    }
}
