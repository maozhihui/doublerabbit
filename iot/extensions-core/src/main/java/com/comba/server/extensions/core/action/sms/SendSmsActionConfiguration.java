package com.comba.server.extensions.core.action.sms;

import lombok.Data;

/**
 * @author huangjinlong
 */
@Data
public class SendSmsActionConfiguration {
    private String dstNum;
    private String content;
}
