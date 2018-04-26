package com.comba.server.extensions.core.plugin.sms;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import lombok.Data;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Data
@Service
public class SmsPlatformSender {

    private static Logger logger = Logger.getLogger(SmsPlatformSender.class);
    private static int MAX_SEND_TIMES = 3;
    @Value("${sms.url}")
    private String platformUrl;
    @Value("${sms.name}")
    private String platformUserName;
    @Value("${sms.pwd}")
    private String platformPwd;
    @Value("${sms.sign}")
    private String platformSign;
    private String platformExtno = "";


    public SmsPlatformSender() {

    }

    public SmsPlatformSender(String platformUrl, String platformUserName,
                             String platformPwd, String platformSign, String platformExtno) {
        this.platformUrl = platformUrl;
        this.platformUserName = platformUserName;
        this.platformPwd = platformPwd;
        this.platformSign = platformSign;
        this.platformExtno = platformExtno;
    }

    public boolean send(String tel,String message) {
        boolean success = false;
        int sendTime = 1;
        while (!success) {
            try {
                success = sendMessage(tel,message);
                sendTime++;
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }

            if (!success) {
                // 发送失败并且超过最大发送次数MAX_SEND_TIMES
                if (sendTime > MAX_SEND_TIMES) {
                    logger.error("send message " + message + " to "
                            + tel + "failed!");
                    break;
                }

                try {
                    Thread.sleep(2000);
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        }

        return success;
    }

    private boolean sendMessage(String tel,String message) {
        boolean result = false;
        try {
            // 短信平台厂家的url
            String platformUrl = this.platformUrl;
            // 用户名
            String name = this.platformUserName;
            // 密码
            String pwd = this.platformPwd;
            // 手机号
            String mobile = URLEncoder.encode(tel, "UTF-8");
            // 发送内容
            String content = URLEncoder.encode(message, "UTF-8");
            // 追加发送时间，可为空，为空为及时发送
            String stime = "";
            // 签名
            String sign = URLEncoder.encode(this.platformSign, "UTF-8");
            // type为固定值pt
            String type = "pt";
            // extno为扩展码，必须为数字 可为空
            String extno = this.platformExtno;

            StringBuffer sb = new StringBuffer("");
            sb.append(platformUrl + "?");
            sb.append("name=" + name);
            sb.append("&pwd=" + pwd);
            sb.append("&mobile=" + mobile);
            sb.append("&content=" + content);
            sb.append("&stime=" + stime);
            sb.append("&sign=" + sign);
            sb.append("&type=" + type);
            sb.append("&extno=" + extno);
            // 创建url对象
            URL url = new URL(sb.toString());
            // 打开url连接
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            // 设置url请求方式 ‘post’
            connection.setRequestMethod("POST");
            // 发送
            InputStream is = url.openStream();
            // 转换返回值
            String returnStr = SmsPlatformSender.convertStreamToString(is);
            if (returnStr != null && !returnStr.isEmpty()) {
                String[] strs = returnStr.split(",");
                if (strs.length == 0) {
                    return result;
                }
                if ("0".equals(strs[0])) {
                    result = true;
                    logger.info("send success[" + returnStr+"]");
                } else {
                    logger
                            .info("send fail,["
                                    + strs[0]
                                    + "],{1:含有敏感词汇,2:余额不足,3:没有号码,4:包含sql语句,10:账号不存在,11:账号注销,12:账号停用,13:IP鉴权失败,14:格式错误,-1:云平台系统异常}");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 转换返回值类型为UTF-8格式.
     *
     * @param is
     * @return
     */
    private static String convertStreamToString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        byte[] bytes = new byte[4096];
        int size = 0;

        try {
            while ((size = is.read(bytes)) > 0) {
                String str = new String(bytes, 0, size, "UTF-8");
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}

