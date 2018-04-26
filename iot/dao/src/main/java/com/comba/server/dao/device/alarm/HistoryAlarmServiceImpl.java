package com.comba.server.dao.device.alarm;

import com.comba.server.common.data.device.HistoryAlarm;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.device.HistoryAlarmEntity;
import com.comba.server.dao.util.DaoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 历史告警 业务实现类
 *
 * @作者 sujinxian
 * @创建时间 2018-03-05 09:18:44
 */
@Service("historyAlarmService")
public class HistoryAlarmServiceImpl implements HistoryAlarmService {
	@Resource
	private HistoryAlarmDao historyAlarmDao;
	
	@Resource
	private BaseDao baseDao;
	
		public List<HistoryAlarm> findByIds(Iterable<String> ids){
		List<HistoryAlarmEntity> list =  historyAlarmDao.findAll(ids);
		return DaoUtil.convertDataList(list);
	}

    /**
     * 查询所有对象
     *
     * @return
     */
    @Override
    public List<HistoryAlarm> findAll() {
        return null;
    }

    /**
	 * Save
	 * @param t
	 */
	@Transactional
	@Override
	public HistoryAlarm save(HistoryAlarm t) {
		return historyAlarmDao.save(new HistoryAlarmEntity(t)).toData();
	}

	@Override
	public void delete(String id) {
		historyAlarmDao.delete(id);
	}

	@Transient
	public String getWhereSql(HistoryAlarm t) {
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
	public Map<String, Object> getWhereParam(HistoryAlarm t) {
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
	public Page getDataByPage(int pageNo, int pageSize, HistoryAlarm obj, List<String> orderBysList) {
		StringBuffer hql = new StringBuffer(" from HistoryAlarmEntity a ");
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
		List<HistoryAlarmEntity> list = (List<HistoryAlarmEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	
	}

	

	@Override
	public void delete(HistoryAlarm t) {
		historyAlarmDao.delete(new HistoryAlarmEntity(t));
	}
	

	@Override
	public HistoryAlarm getOne(String id) {
		
		HistoryAlarmEntity t = historyAlarmDao.findOne(id);
		if(t==null) return null;
		return t.toData();
		
	}

	@Override
	public boolean exists(String id) {
		return historyAlarmDao.exists(id);
	}

	@Override
	public void delete(Iterable<HistoryAlarm> entities) {
		if(entities!=null){
			for(HistoryAlarm t:entities){
				historyAlarmDao.delete(new HistoryAlarmEntity(t));
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
    public Page getDataByPage(HistoryAlarm obj, int pageNo, int pageSize) {
        return null;
    }

    @Override
	public void deleteByIds(List<String> ids) {
		if(ids!=null && !ids.isEmpty()){
			for(String id:ids){
				historyAlarmDao.delete(id);
			}
		}
	}


    /**
     * 分页查询活动告警，支持设备名称模糊查询
     *
     * @param pageNo   页数
     * @param pageSize 每页大小
     * @param obj      实体
     * @return
     */
    @Override
    public Page queryAlarmByPage(int pageNo, int pageSize, HistoryAlarm obj) {
        StringBuffer sql = new StringBuffer("select new map(a.id as id ,a.confirmTime as confirmTime,d.name as devName,ac.alarmName as alarmName,ac.alarmContent as alarmContent,a.startTime as startTime)" +
                " FROM HistoryAlarmEntity a , DeviceEntity d ,AlarmContentEntity ac " +
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
}
