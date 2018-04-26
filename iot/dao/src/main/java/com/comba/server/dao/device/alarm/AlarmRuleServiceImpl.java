package com.comba.server.dao.device.alarm;

import com.comba.server.common.data.device.AlarmRule;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.device.AlarmRuleEntity;
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
 * 告警规则 业务实现类
 *
 * @作者 sujinxian
 * @创建时间 2018-02-28 15:55:17
 */
@Service("alarmRuleService")
public class AlarmRuleServiceImpl implements AlarmRuleService {
	@Resource
	private AlarmRuleDao alarmRuleDao;
	
	@Resource
	private BaseDao baseDao;
		public List<AlarmRule> findByIds(Iterable<String> ids){
		List<AlarmRuleEntity> list =  alarmRuleDao.findAll(ids);
		return DaoUtil.convertDataList(list);
	}

    /**
     * 查询所有对象
     *
     * @return
     */
    @Override
    public List<AlarmRule> findAll() {
        return null;
    }

    /**
	 * Save
	 * @param t
	 */
	@Transactional
	@Override
	public AlarmRule save(AlarmRule t) {
		return alarmRuleDao.save(new AlarmRuleEntity(t)).toData();
	}

	@Override
	public void delete(String id) {
		alarmRuleDao.delete(id);
	}

	@Transient
	public String getWhereSql(AlarmRule t) {
		StringBuffer sb = new StringBuffer("where 1=1");
        if (StringUtils.isNoneBlank(t.getProductId())) {
            sb.append(" and a.productId = :productId ");
        }
        if (StringUtils.isNoneBlank(t.getTenantId())) {
            sb.append(" and a.tenantId = :tenantId ");
        }if (StringUtils.isNoneBlank(t.getName())) {
            sb.append(" and a.name like :name ");
        }
		return sb.toString();
	}
	
	@Transient
	public Map<String, Object> getWhereParam(AlarmRule t) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (t.getProductId() != null){
		    params.put("productId",t.getProductId());
        }
        if (StringUtils.isNoneBlank(t.getTenantId())) {
            params.put("tenantId",t.getTenantId());
         }if (StringUtils.isNoneBlank(t.getName())) {
            params.put("name","%" + t.getName()+"%");
        }
		
		return params;
			
	}


	@SuppressWarnings("unchecked")
	public Page getDataByPage(int pageNo, int pageSize, AlarmRule obj, List<String> orderBysList) {
		StringBuffer hql = new StringBuffer(" from AlarmRuleEntity a ");
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
		List<AlarmRuleEntity> list = (List<AlarmRuleEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	
	}

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<String> ids) {
        if(ids!=null && !ids.isEmpty()){
            for(String id:ids){
                alarmRuleDao.delete(id);
            }
        }
    }


    @Override
	public void delete(AlarmRule t) {
		alarmRuleDao.delete(new AlarmRuleEntity(t));
	}
	

	@Override
	public AlarmRule getOne(String id) {
		
		AlarmRuleEntity t = alarmRuleDao.findOne(id);
		if(t==null) return null;
		return t.toData();
		
	}

	@Override
	public boolean exists(String id) {
		return alarmRuleDao.exists(id);
	}

	@Override
	public void delete(Iterable<AlarmRule> entities) {
		if(entities!=null){
			for(AlarmRule t:entities){
				alarmRuleDao.delete(new AlarmRuleEntity(t));
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
    public Page getDataByPage(AlarmRule obj, int pageNo, int pageSize) {
        return null;
    }


    /**
     * 查询该租户下面的所有规则
     *
     * @param tenantId
     * @return
     */
    @Override
    public List<AlarmRule> findByProductId(String tenantId) {

        List<AlarmRuleEntity> list = alarmRuleDao.findByProductId(tenantId);

        return list== null?null:DaoUtil.convertDataList(list);
    }
}
