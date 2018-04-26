package com.comba.server.extensions.api.plugins.msg;

import com.comba.server.common.msg.core.BasicCmdMsg;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author maozhihui
 * @Description:
 * @create 2018/1/8 20:44
 **/
@Data
@AllArgsConstructor
public class CtrlCmdMsg implements Serializable {

    List<BasicCmdMsg> cmdMsgs;

}
