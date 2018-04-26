package com.comba.mantun.service;

import com.comba.mantun.message.Constants;
import lombok.extern.slf4j.Slf4j;

/**
 * 定期更新token任务
 * @author maozhihui
 * @Description:
 * @create 2018/2/25 17:01
 **/
@Slf4j
public class RefreshTokenTask implements Runnable {

    private AuthorizeService authorizeService;

    public RefreshTokenTask(AuthorizeService authorizeService){
        this.authorizeService = authorizeService;
    }

    @Override
    public void run() {
        Constants.token = authorizeService.getAccessToken();
        log.info("mantun refresh token [{}]",Constants.token);
    }
}
