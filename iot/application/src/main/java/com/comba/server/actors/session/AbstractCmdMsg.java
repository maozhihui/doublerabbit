package com.comba.server.actors.session;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.comba.server.common.msg.cmd.BasicCommandMsg;
import com.comba.server.common.msg.cmd.QueryCmdResponseMsg;
import com.comba.server.common.msg.cmd.SetCmdResponseMsg;

public abstract class AbstractCmdMsg {
    public static ConcurrentMap<String, BasicCommandMsg<QueryCmdResponseMsg>> queryRespMsgMap = new ConcurrentHashMap<>();
    public static ConcurrentMap<String, BasicCommandMsg<SetCmdResponseMsg>> setRespMsgMap = new ConcurrentHashMap<>();
}
