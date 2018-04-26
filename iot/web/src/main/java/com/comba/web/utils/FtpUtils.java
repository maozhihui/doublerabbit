package com.comba.web.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;

public class FtpUtils {


    public static boolean uploadFile(String ftpUserName,String ftpPassword, String ftpIp, Integer ftpPort,
                               String originFileName,InputStream input, String ftpPath){
        boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("utf-8");
        try {
            int reply;
            ftp.connect(ftpIp, ftpPort);// 连接FTP服务器
            ftp.login(ftpUserName, ftpPassword);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(ftpPath );
            ftp.changeWorkingDirectory(ftpPath);
            ftp.storeFile(originFileName,input);
            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {

                }
            }
        }
        return success;
    }

}
