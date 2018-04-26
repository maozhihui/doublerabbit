package com.comba.server.dao.upgrade;

import com.comba.server.common.data.AppVersion;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.model.AppVersionEntity;
import com.comba.server.dao.util.DaoUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  业务实现类
 *
 * @作者 sujinxian
 * @创建时间 2018-01-24 16:49:36
 */
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {
	@Resource
	private AppVersionDao appVersionDao;
	
	@Resource
	private BaseDao baseDao;
	
    public List<AppVersion> findByIds(Iterable<String> ids){
    List<AppVersionEntity> list =  appVersionDao.findAll(ids);
    return DaoUtil.convertDataList(list);
	}

    /**
     * 查询所有对象
     *
     * @return
     */
    @Override
    public List<AppVersion> findAll() {
        return null;
    }

    /**
	 * Save
	 * @param t
	 */
	@Transactional
	@Override
	public AppVersion save(AppVersion t) {
		 return appVersionDao.save(new AppVersionEntity(t)).toData();
	}

	@Override
	public void delete(String id) {
		appVersionDao.delete(id);
	}

	@Transient
	public String getWhereSql(AppVersion t) {
		StringBuffer sb = new StringBuffer("where 1=1");
        if (t.getTenantId() != null){
            sb.append(" and a.tenantId = :tenantId ");
        }
        if (StringUtils.isNotBlank(t.getVersion())){
            sb.append(" and a.name like :name");
        }
		return sb.toString();
	}
	
	@Transient
	public Map<String, Object> getWhereParam(AppVersion t) {
		Map<String, Object> params = new HashMap<String, Object>();
        if (t.getTenantId() != null){
            params.put("tenantId",t.getTenantId());
        }
        if (StringUtils.isNotBlank(t.getVersion())){
            params.put("name",t.getVersion());
        }
		
		return params;
			
	}

	@Override
	public void delete(AppVersion t) {
		appVersionDao.delete(new AppVersionEntity(t));
	}
	

	@Override
	public AppVersion getOne(String id) {
		
		AppVersionEntity t = appVersionDao.findOne(id);
		if(t==null) return null;
		return t.toData();
		
	}

	@Override
	public boolean exists(String id) {
		return appVersionDao.exists(id);
	}

	@Override
	public void delete(Iterable<AppVersion> entities) {
		if(entities!=null){
			for(AppVersion t:entities){
				appVersionDao.delete(new AppVersionEntity(t));
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
    public Page getDataByPage(AppVersion obj, int pageNo, int pageSize) {
        StringBuffer hql = new StringBuffer(" from AppVersionEntity a ");
        Map<String, Object> params = new HashMap<String, Object>();
        String orderBy = "";
        if(obj!=null){
            hql.append(getWhereSql(obj));
            params = getWhereParam(obj);
        }
        String queryHql = hql.toString();


        if(orderBy.length()>0){
            queryHql += " order by " + orderBy + " desc ";
        }

        Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
        List<AppVersionEntity> list = (List<AppVersionEntity>)resultPage.getResult();
        resultPage.setResult(DaoUtil.convertDataList(list));
        return resultPage;
    }

    @Override
	public void deleteByIds(String[] ids) {
		if(ids!=null && ids.length>0){
			for(String id:ids){
				appVersionDao.delete(id);
			}
		}
	}



	
}
