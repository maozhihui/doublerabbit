package com.comba.server.dao.auditLog;


import java.util.List;

import com.comba.server.common.data.AuditLog;
import com.comba.server.common.data.web.page.Page;



/**
 * 日志 处理服务类
 * @author wengzhonghui
 *
 */
public interface AuditLogService {
	
	/**
	 * 保存
	 * @param auditLog
	 */
	public void log(AuditLog auditLog);
	/**
	 * 批量保存
	 * @param auditLogs
	 */
	public void batchLog(List<AuditLog> auditLogs);
	
	
	/**
	 * 日志分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param auditLog
	 * @return
	 */
	public Page pagedQuery(int pageNo, int pageSize,AuditLog auditLog);
	
	
	/**
	 * 按类型查询日志
	 * @param pageNo
	 * @param pageSize
	 * @param auditLog
	 * @param type
	 * @return
	 */
	public Page pagedQuery(int pageNo, int pageSize,AuditLog auditLog,String type,String userId,boolean isAll);
	 
	
	
	
	
}
