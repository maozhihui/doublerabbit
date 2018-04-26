package com.comba.server.dao.auditLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Transient;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comba.server.common.data.AuditLog;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.AuditLogEntity;



/**
 * 日志 处理服务类
 * @author wengzhonghui
 *
 */
@Service
@Transactional
public class AuditLogServiceImpl implements AuditLogService{
	
	@Resource
	private AuditLogDao auditLogDao;
	@Resource
	private BaseDao baseDao;
	/**
	 * 保存
	 * @param auditLog
	 */
	@Override
	public void log(AuditLog auditLog){
		auditLogDao.save(new AuditLogEntity(auditLog));
	} 
	/**
	 * 批量保存
	 * @param auditLogs
	 */
	@Override
	public void batchLog(List<AuditLog> auditLogs){
		for(AuditLog auditLog:auditLogs){
			auditLogDao.save(new AuditLogEntity(auditLog));
		}
	}
	
	@Override
	public Page pagedQuery(int pageNo, int pageSize,AuditLog auditLog){
		StringBuffer hql = new StringBuffer(" from AuditLogEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(auditLog!=null){
			hql.append(getWhereSql(auditLog));
			params = getWhereParam(auditLog);
		}
		hql.append(" ORDER BY createDate DESC ");
		return baseDao.pagedQuery(hql.toString(), pageNo, pageSize, params);
	 }
	
	@Override
	 public Page pagedQuery(int pageNo, int pageSize,AuditLog auditLog,String type,String userId,boolean isAll){
		StringBuffer hql = new StringBuffer(" from AuditLogEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(auditLog!=null){
			hql.append(getWhereSql(auditLog));
			params = getWhereParam(auditLog);
		}
		if(type!=null && type.length()>0 && !"-1".equals(type)){
			hql.append(" and a.type="+type+" ");
		}
		if(!isAll){
			if(userId!=null && userId.length()>0){
				hql.append(" and a.userId="+userId+" ");
			}
		}
		hql.append(" ORDER BY createDate DESC ");
		return baseDao.pagedQuery(hql.toString(), pageNo, pageSize, params);
	 }
	 
	public String getWhereSql(AuditLog auditLog) {
		if(auditLog == null) return "";
		StringBuffer sb = new StringBuffer("where 1=1");
		
		if(auditLog.getId()!=null)
			sb.append(" and a.id = :id ");
		if(StringHelper.isNotEmpty(auditLog.getRequestIp())){
			if(auditLog.getRequestIp().indexOf("###") >=1){
				String[] ipArr = auditLog.getRequestIp().split("###");
				for(String i : ipArr){
					sb.append(" and a.requestIp like '%"+i+"%' ");
				}
			}else{
				sb.append(" and a.requestIp like :requestIp ");
			}		
		}	
		if(StringHelper.isNotEmpty(auditLog.getDescription())){
			if(auditLog.getDescription().indexOf("###") >=1){
				String[] deArr = auditLog.getDescription().split("###");
				for(String i : deArr){
					sb.append(" and a.description like '%"+i+"%' ");
				}
			}else{
				sb.append(" and a.description like :description ");
			}
		}
		if(StringHelper.isNotEmpty(auditLog.getCreateBy())){
			if(auditLog.getCreateBy().indexOf("###") >=1){
				String[] ceArr = auditLog.getCreateBy().split("###");
				for(String i : ceArr){
					sb.append(" and a.createBy like '%"+i+"%' ");
				}
			}else{
				sb.append(" and a.createBy like :createBy ");
			}
		}
		return sb.toString();
	}
	
	public String getWhereSql_2(AuditLog auditLog) {
		if(auditLog == null) return "";
		StringBuffer sb = new StringBuffer();
		if(StringHelper.isNotEmpty(auditLog.getCreateBy())){
			if(auditLog.getCreateBy().indexOf("###") >=1){
				String[] ceArr = auditLog.getCreateBy().split("###"); 
				for(String i : ceArr){
					sb.append(" and a.createBy like '%"+i+"%' ");
				}
			}else{
				sb.append(" and a.createBy like :createBy ");
			}
		}
		if(StringHelper.isNotEmpty(auditLog.getRequestIp())){
			if(auditLog.getRequestIp().indexOf("###") >=1){
				String[] ipArr = auditLog.getRequestIp().split("###");
				for(String i : ipArr){
					sb.append(" and a.requestIp like '%"+i+"%' ");
				}
			}else{
				sb.append(" and a.requestIp like :requestIp ");
			}
		}
		if(StringHelper.isNotEmpty(auditLog.getDescription())){
			if(auditLog.getDescription().indexOf("###") >=1){
				String[] deArr = auditLog.getDescription().split("###");
				for(String i : deArr){
					sb.append(" and a.description like '%"+i+"%' ");
				}
			}else{
				sb.append(" and a.description like :description ");
			}
		}	
		return sb.toString();
	}
	
	public Map<String, Object> getWhereParam(AuditLog auditLog) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(auditLog.getId()!=null)
			params.put("id", auditLog.getId());
		if(StringHelper.isNotEmpty(auditLog.getCreateBy()))
			params.put("createBy", "%" + auditLog.getCreateBy().trim() + "%");
		if(StringHelper.isNotEmpty(auditLog.getRequestIp()))
			params.put("requestIp", "%" + auditLog.getRequestIp().trim() + "%");
		if(StringHelper.isNotEmpty(auditLog.getDescription())) 
			params.put("description", "%" + auditLog.getDescription().trim() + "%");
		return params;
			
	}
	
}
