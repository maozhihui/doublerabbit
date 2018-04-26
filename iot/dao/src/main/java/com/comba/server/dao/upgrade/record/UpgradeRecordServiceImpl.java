package com.comba.server.dao.upgrade.record;

import com.comba.server.common.data.UpgradeRecord;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.model.UpgradeRecordEntity;
import com.comba.server.dao.model.device.DeviceEntity;
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
 * @创建时间 2018-01-25 16:21:07
 */
@Service("updateRecordService")
public class UpgradeRecordServiceImpl implements UpgradeRecordService {
	@Resource
	private UpgradeRecordDao updateRecordDao;
	
	@Resource
	private BaseDao baseDao;
	
		public List<UpgradeRecord> findByIds(Iterable<String> ids){
		List<UpgradeRecordEntity> list =  updateRecordDao.findAll(ids);
		return DaoUtil.convertDataList(list);
	}

    /**
     * 查询所有对象
     *
     * @return
     */
    @Override
    public List<UpgradeRecord> findAll() {
        return null;
    }

    /**
	 * Save
	 * @param t
	 */
	@Transactional
	@Override
	public UpgradeRecord save(UpgradeRecord t) {
		return updateRecordDao.save(new UpgradeRecordEntity(t)).toData();
	}

	@Override
	public void delete(String id) {
		updateRecordDao.delete(id);
	}

	@Transient
	public String getWhereSql(UpgradeRecord t) {
		StringBuffer sb = new StringBuffer("where 1=1");
        if (t.getTenantId() != null){
            sb.append(" and a.tenantId = :tenantId ");
        }
		return sb.toString();
	}
	
	@Transient
	public Map<String, Object> getWhereParam(UpgradeRecord t) {
		Map<String, Object> params = new HashMap<String, Object>();
        if (t.getTenantId() != null){
            params.put("tenantId",t.getTenantId());
        }
		
		return params;
			
	}



	
	@SuppressWarnings("unchecked")
	@Override
	public Page getDataByPage(int pageNo, int pageSize, UpgradeRecord obj, List<String> orderBysList) {
		StringBuffer hql = new StringBuffer(" from UpgradeRecordEntity a ");
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
		List<UpgradeRecordEntity> list = (List<UpgradeRecordEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	
	}

	

	@Override
	public void delete(UpgradeRecord t) {
		updateRecordDao.delete(new UpgradeRecordEntity(t));
	}
	

	@Override
	public UpgradeRecord getOne(String id) {
		
		UpgradeRecordEntity t = updateRecordDao.findOne(id);
		if(t==null) return null;
		return t.toData();
		
	}

	@Override
	public boolean exists(String id) {
		return updateRecordDao.exists(id);
	}

	@Override
	public void delete(Iterable<UpgradeRecord> entities) {
		if(entities!=null){
			for(UpgradeRecord t:entities){
				updateRecordDao.delete(new UpgradeRecordEntity(t));
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
    public Page getDataByPage(UpgradeRecord obj, int pageNo, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer(" select new map(a.id as id ,c.version as version,b.name as taskName,d.name as deviceName," +
                "a.status as status,a.createTime as createTime) "
                + " from UpgradeRecordEntity a ,UpgradeTaskEntity b ,AppVersionEntity c,DeviceEntity d "+
                " where  a.taskId = b.id and a.versionId = c.id and a.deviceId = d.id");
        if (obj != null){
            setParam(obj,hql,params);
        }

        hql.append(" order by a.createTime desc");
        String queryHql = hql.toString();
        Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
        return resultPage;
    }

    private void setParam(UpgradeRecord t, StringBuffer hql, Map<String, Object> params ) {
        if (StringUtils.isNotBlank(t.getTenantId())) {
            hql.append(" and c.tenantId = :tenantId ");
            params.put("tenantId", t.getTenantId());
        }
        if (StringUtils.isNotBlank(t.getVersion())) {
            hql.append(" and c.version like :version ");
            params.put("version", "%" + t.getVersion().trim() + "%");
        }
        if (StringUtils.isNotBlank(t.getTaskName())) {
            hql.append(" and c.taskName like :taskName ");
            params.put("taskName", "%" + t.getTaskName().trim() + "%");
        }
        if (StringUtils.isNotBlank(t.getDeviceName())) {
            hql.append(" and c.deviceName like :deviceName ");
            params.put("deviceName", "%" + t.getDeviceName().trim() + "%");
        }
    }
    @Override
	public void deleteByIds(String[] ids) {
		if(ids!=null && ids.length>0){
			for(String id:ids){
				updateRecordDao.delete(id);
			}
		}
	}


    /**
     * 根据设备id和z状态查询记录
     *
     * @param devId  设备id
     * @param status 状态
     * @return
     */
    @Override
    public UpgradeRecord findByDeviceIdAndStatus(String devId, Integer status) {
        UpgradeRecordEntity entity =  updateRecordDao.findByDeviceIdAndStatus(devId, status);
        return entity == null? null:entity.toData();
    }

    /**
     * 获取更新文件路径
     *
     * @param hardIdentity  设备硬件标识
     * @param status 状态
     * @return
     */
    @Override
    public List<String> getUpdatePath(String hardIdentity, Integer status) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select b.file_name from upgrade_record a ,app_version b,device c where a.version_id = b.id " +
                " and a.device_id = c.id and c.hard_identity =:hardIdentity and a.status = :status ");

        Map<String,Object> params = new HashMap<>();
        params.put("hardIdentity",hardIdentity);
        params.put("status",status);
        List<String> list = baseDao.queryBySql(buffer.toString(),params);
        return list == null?null:list;
    }

    /**
     * 更新记录状态
     *
     * @param id  记录id
     * @param status 状态
     * @return
     */
    @Override
    public Integer updateRecordStatus(String id, Integer status) {
        return updateRecordDao.updateRecordStatus(id, status);
    }

    /**
     * 查询指定设备最新的一条记录
     *
     * @param hardIdentity 设备
     * @return
     */
    @Override
    public UpgradeRecord queryLatestRecordByDevId(String hardIdentity) {
        StringBuffer sql = new StringBuffer("SELECT a.id,a.version_id,a.task_id,a.device_id,a.tenant_id,a.status,a.create_time " +
                " FROM upgrade_record a inner join device b " +
                " where a.device_id = b.id and b.HARD_IDENTITY=:hardIdentity" +
                " order by create_time desc LIMIT 1 ");
        Map<String ,Object> params = new HashMap<>();
        params.put("hardIdentity",hardIdentity);

        List ret = baseDao.queryBySql(sql.toString(),params,new UpgradeRecordEntity());
        if (ret == null || ret.isEmpty())
            return null;
        List<UpgradeRecord> list =  DaoUtil.convertDataList(ret);
        return list.get(0);
    }

    /**
     * 更新设备最新一条记录状态
     *
     * @param devId  设备id
     * @param status 状态
     * @return
     */
    @Override
    public Integer updateLatestRecordStatus(String devId, Integer status) {
        UpgradeRecord record =  queryLatestRecordByDevId(devId);
        if (record == null)
            return 0;
        return updateRecordDao.updateRecordStatus(record.getId(), status);
    }
}
