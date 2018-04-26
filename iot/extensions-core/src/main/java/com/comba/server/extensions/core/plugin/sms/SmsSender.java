package com.comba.server.extensions.core.plugin.sms;

/**
 * 短信发送接口
 * @author maozhihui
 *
 */
public interface SmsSender {

	boolean send(String isdn,String content);
}
