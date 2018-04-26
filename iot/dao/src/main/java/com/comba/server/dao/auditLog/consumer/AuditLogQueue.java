package com.comba.server.dao.auditLog.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.comba.server.common.data.AuditLog;




@Component
public class AuditLogQueue {
    private static BlockingQueue<AuditLog> blockingQueue = new LinkedBlockingQueue<AuditLog>();

    public void add(AuditLog auditLog) {
        blockingQueue.add(auditLog);
    }

    public AuditLog poll() throws InterruptedException {
        return blockingQueue.poll(1, TimeUnit.SECONDS);
    }
    
    public static void addSystemLog(AuditLog auditLog) {
        blockingQueue.add(auditLog);
    }
    
    
    
}
