package com.comba.server.dao.auditLog.consumer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.comba.server.common.data.AuditLog;
import com.comba.server.dao.auditLog.AuditLogService;
import com.comba.server.dao.model.AuditLogEntity;



/**
 *  日志的保存线程
 * @author wengzhonghui
 *
 */
@Component
public class AuditLogConsumer  implements Runnable{

	private static Logger logger = LoggerFactory.getLogger(AuditLogConsumer.class);
    public static final int DEFAULT_BATCH_SIZE = 64;
    private AuditLogQueue auditLogQueue;
    private int batchSize = DEFAULT_BATCH_SIZE;
    private boolean active = true;
    private Thread thread;
    
    @Resource
    private AuditLogService auditLogService;
    
    
    @PostConstruct
    public void init() {
        thread = new Thread(this);
        thread.start();
    }

    @PreDestroy
    public void close() {
        active = false;
    }

    public void run() {
        while (active) {
            execute();
        }
    }
    
    public void execute() {
        List<AuditLog> auditDtos = new ArrayList<AuditLog>();

        try {
            int size = 0;

            while (size < batchSize) {
            	AuditLog auditLog = auditLogQueue.poll();

                if (auditLog == null) {
                    break;
                }

                auditDtos.add(auditLog);
                size++;
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage(), ex);
        }

        if (!auditDtos.isEmpty()) {
        	auditLogService.batchLog(auditDtos);
        }
    }

    @Resource
	public void setAuditLogQueue(AuditLogQueue auditLogQueue) {
		this.auditLogQueue = auditLogQueue;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
    
    

}
