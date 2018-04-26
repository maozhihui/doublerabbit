package com.comba.server.dao.device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import com.comba.server.common.data.device.AlarmContent;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.device.AlarmContentEntity;
import com.comba.server.dao.util.DaoUtil;
import org.springframework.stereotype.Service;



/**
 * 告警信息 业务实现类
 *
 * @作者 sujinxian
 * @创建时间 2018-02-26 16:33:41
 */
@Service("alarmContentService")
public class AlarmContentServiceImpl implements AlarmContentService {
	@Resource
	private AlarmContentDao alarmContentDao;
	
	@Resource
	private BaseDao baseDao;
	
		public List<AlarmContent> findByIds(Iterable<String> ids){
		List<AlarmContentEntity> list =  alarmContentDao.findAll(ids);
		return DaoUtil.convertDataList(list);
	}

    /**
     * 查询所有对象
     *
     * @return
     */
    @Override
    public List<AlarmContent> findAll() {
        List<AlarmContentEntity> list =  alarmContentDao.findAll();
        return list == null?null:DaoUtil.convertDataList(list);
    }

    /**
	 * Save
	 * @param t
	 */
	@Transactional
	@Override
	public AlarmContent save(AlarmContent t) {
		return alarmContentDao.save(new AlarmContentEntity(t)).toData();
	}

	@Override
	public void delete(String id) {
		alarmContentDao.delete(id);
	}

	@Transient
	public String getWhereSql(AlarmContent t) {
		StringBuffer sb = new StringBuffer("where 1=1");
		return sb.toString();
	}
	
	@Transient
	public Map<String, Object> getWhereParam(AlarmContent t) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		
		return params;
			
	}



	
	@SuppressWarnings("unchecked")
	public Page getDataByPage(int pageNo, int pageSize, AlarmContent obj, List<String> orderBysList) {
		StringBuffer hql = new StringBuffer(" from AlarmContentEntity a ");
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
		List<AlarmContentEntity> list = (List<AlarmContentEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	
	}

	

	@Override
	public void delete(AlarmContent t) {
		alarmContentDao.delete(new AlarmContentEntity(t));
	}
	

	@Override
	public AlarmContent getOne(String id) {
		
		AlarmContentEntity t = alarmContentDao.findOne(id);
		if(t==null) return null;
		return t.toData();
		
	}

	@Override
	public boolean exists(String id) {
		return alarmContentDao.exists(id);
	}

	@Override
	public void delete(Iterable<AlarmContent> entities) {
		if(entities!=null){
			for(AlarmContent t:entities){
				alarmContentDao.delete(new AlarmContentEntity(t));
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
    public Page getDataByPage(AlarmContent obj, int pageNo, int pageSize) {
        return null;
    }

	public void deleteByIds(String[] ids) {
		if(ids!=null && ids.length>0){
			for(String id:ids){
				alarmContentDao.delete(id);
			}
		}
	}
	
	
}
