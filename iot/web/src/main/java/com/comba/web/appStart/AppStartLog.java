package com.comba.web.appStart;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.comba.server.common.data.AuditLog;
import com.comba.server.dao.auditLog.consumer.AuditLogQueue;

@Component
@Order(value=2)
public class AppStartLog implements CommandLineRunner {
	@Override
    public void run(String... args) throws Exception {
        AuditLog log = new AuditLog();
        log.setDescription("系统启动");    
        log.setRequestIp("");    
        log.setCreateBy("");    
        log.setType(AuditLog.TYPE_SYSTEM_LOG);
        log.setCreateDate(new Date());  
        //保存数据库    
        AuditLogQueue.addSystemLog(log);
	}
}
