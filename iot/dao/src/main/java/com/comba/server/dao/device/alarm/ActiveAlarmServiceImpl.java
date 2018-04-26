package com.comba.server.dao.device.alarm;

import com.comba.server.common.data.device.ActiveAlarm;
import com.comba.server.common.data.device.AlarmRule;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.device.ActiveAlarmEntity;
import com.comba.server.dao.util.DaoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动告警 业务实现类
 *
 * @作者 sujinxian
 * @创建时间 2018-03-01 11:24:16
 */
@Service("activeAlarmService")
public class ActiveAlarmServiceImpl implements ActiveAlarmService {
	@Resource
	private ActiveAlarmDao activeAlarmDao;
	
	@Resource
	private BaseDao baseDao;
	
		public List<ActiveAlarm> findByIds(Iterable<String> ids){
		List<ActiveAlarmEntity> list =  activeAlarmDao.findAll(ids);
		return DaoUtil.convertDataList(list);
	}

    /**
     * 查询所有对象
     *
     * @return
     */
    @Override
    public List<ActiveAlarm> findAll() {
        return null;
    }

    /**
	 * Save
	 * @param t
	 */
	@Transactional
	@Override
	public ActiveAlarm save(ActiveAlarm t) {
		return  activeAlarmDao.save(new ActiveAlarmEntity(t)).toData();
	}

	@Override
	public void delete(String id) {
		activeAlarmDao.delete(id);
	}

	@Transient
	public String getWhereSql(ActiveAlarm t) {
		StringBuffer sb = new StringBuffer("where 1=1");
        if (StringUtils.isNoneBlank(t.getProductId())) {
            sb.append(" and a.productId = :productId ");
        }
        if (StringUtils.isNoneBlank(t.getTenantId())) {
            sb.append(" and a.tenantId = :tenantId ");
        }
		return sb.toString();
	}
	
	@Transient
	public Map<String, Object> getWhereParam(ActiveAlarm t) {
		Map<String, Object> params = new HashMap<String, Object>();
        if (t.getProductId() != null){
            params.put("productId",t.getProductId());
        }
        if (StringUtils.isNoneBlank(t.getTenantId())) {
            params.put("tenantId",t.getTenantId());
        }
		
		return params;
			
	}



	
	@SuppressWarnings("unchecked")
	@Override
	public Page getDataByPage(int pageNo, int pageSize, ActiveAlarm obj, List<String> orderBysList) {
		StringBuffer hql = new StringBuffer(" from ActiveAlarmEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		String orderBy = "";
		if(orderBysList != null && orderBysList.size()>0){
			for(String i : orderBysList){
				orderBy = i;
			}
		}		
		if(obj!=null){
			hql.append(getWhereSql(obj));
			params = getWhereParam(obj);
		}
		String queryHql = hql.toString();
		
	
		if(orderBy.length()>0){
			queryHql += " order by " + orderBy + " desc ";
		}
		
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<ActiveAlarmEntity> list = (List<ActiveAlarmEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	
	}

    /**
     *  分页查询活动告警，支持设备名称模糊查询
     *
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @param obj 实体
     * @return
     */
	public Page queryAlarmByPage(int pageNo, int pageSize, ActiveAlarm obj){
	    StringBuffer sql = new StringBuffer("select new map(a.id as id ,d.name as devName,ac.alarmName as alarmName,ac.alarmContent as alarmContent,a.startTime as startTime) FROM ActiveAlarmEntity a , DeviceEntity d ,AlarmContentEntity ac " +
                " where a.devId = d.id and a.alarmId = ac.id ");

        Map<String,Object> params = new HashMap<>();
        if (obj != null){
            if (StringUtils.isNoneBlank(obj.getProductId())) {
                sql.append(" and a.productId = :productId ");
                    params.put("productId",obj.getProductId());
            }
            if (StringUtils.isNoneBlank(obj.getTenantId())) {
                sql.append(" and a.tenantId = :tenantId ");
                params.put("tenantId",obj.getTenantId());
            }
            if (StringUtils.isNoneBlank(obj.getDevName())) {
                sql.append(" and d.name like :name ");
                params.put("name","%"+obj.getDevName()+"%");
            }
        }

        sql.append(" order by startTime desc ");


        Page resultPage = baseDao.pagedQuery(sql.toString(), pageNo, pageSize, params);
        return resultPage;

    }

	

	@Override
	public void delete(ActiveAlarm t) {
		activeAlarmDao.delete(new ActiveAlarmEntity(t));
	}
	

	@Override
	public ActiveAlarm getOne(String id) {
		
		ActiveAlarmEntity t = activeAlarmDao.findOne(id);
		if(t==null) return null;
		return t.toData();
		
	}

	@Override
	public boolean exists(String id) {
		return activeAlarmDao.exists(id);
	}

	@Override
	public void delete(Iterable<ActiveAlarm> entities) {
		if(entities!=null){
			for(ActiveAlarm t:entities){
				activeAlarmDao.delete(new ActiveAlarmEntity(t));
			}
		}
	}

    /**
     * 分页查询（含排序功能）
     *
     * @param obj
     * @param pageNo
     * @param pageSize @return
     */
    @Override
    public Page getDataByPage(ActiveAlarm obj, int pageNo, int pageSize) {
        return null;
    }

    @Override
	public void deleteByIds(List<String> ids) {
		if(ids!=null && !ids.isEmpty()){
			for(String id:ids){
				activeAlarmDao.delete(id);
			}
		}
	}


    /**
     * 保存活动告警，如已有告警，则更新告警时间
     *
     * @param alarm 告警
     */
    @Override
    public void saveActiveAlarm(ActiveAlarm alarm){

        //查询是否已存在活动告警,更新个数大于0 ，说明已存在
        Integer ret = activeAlarmDao.updateActiveAlarmEntity(alarm.getDevId(),alarm.getAlarmId());
        if (ret != null && ret > 0){
            return;
        }

        save(alarm);
    }

    /**
     * 删除活动告警
     *
     * @param devId   设备id
     * @param alarmId 告警id
     * @return
     */
    @Override
    public boolean deleteActiveAlarm(String devId, String alarmId) {
        ActiveAlarmEntity alarmDB = activeAlarmDao.findByDevIdAndAlarmId(devId,alarmId);
        if (alarmDB != null){
            delete(alarmDB.getId());
            return true;
        }
        return false;
    }

    /**
     * 是否存在该类告警信息
     *
     * @param devId
     * @param alarmId
     * @return
     */
    @Override
    public ActiveAlarm isExitActiveAlarm(String devId, String alarmId) {
        ActiveAlarmEntity alarm = activeAlarmDao.findByDevIdAndAlarmId(devId,alarmId);
        if (alarm != null){
            return alarm.toData();
        }
        return null;
    }

    /**
     * 统计产品活动告警数
     *
     * @param productId 产品id
     * @return
     */
    @Override
    public long countByProductId(String productId) {
        return activeAlarmDao.countByProductId(productId);
    }
}
