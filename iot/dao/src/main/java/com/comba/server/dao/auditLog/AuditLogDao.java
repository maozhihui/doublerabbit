package com.comba.server.dao.auditLog;


import org.springframework.data.jpa.repository.JpaRepository;

import com.comba.server.dao.model.AuditLogEntity;



/**
 * 日志处理 数据库操作层
 * @author wengzhonghui
 *
 */
public interface AuditLogDao extends JpaRepository<AuditLogEntity, String>{

}
