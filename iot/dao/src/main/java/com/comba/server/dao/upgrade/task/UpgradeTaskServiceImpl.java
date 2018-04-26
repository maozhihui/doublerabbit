package com.comba.server.dao.upgrade.task;

import com.comba.server.common.data.UpgradeRecord;
import com.comba.server.common.data.UpgradeTask;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.model.UpgradeTaskEntity;
import com.comba.server.dao.upgrade.record.UpgradeRecordService;
import com.comba.server.dao.util.DaoUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.comba.server.common.data.UpgradeRecord.INIT;

/**
 *  业务实现类
 *
 * @作者 sujinxian
 * @创建时间 2018-01-25 16:21:15
 */
@Service("updateTaskService")
public class UpgradeTaskServiceImpl implements UpgradeTaskService {
	@Resource
	private UpgradeTaskDao updateTaskDao;

	@Resource
    private UpgradeRecordService updateRecordService;
	
	@Resource
	private BaseDao baseDao;

	@Override
    public List<UpgradeTask> findByIds(Iterable<String> ids){
		List<UpgradeTaskEntity> list =  updateTaskDao.findAll(ids);
		return DaoUtil.convertDataList(list);
	}

    /**
     * 查询所有对象
     *
     * @return
     */
    @Override
    public List<UpgradeTask> findAll() {
        return null;
    }

    /**
	 * Save
	 * @param t
	 */
	@Transactional
	@Override
	public UpgradeTask save(UpgradeTask t) {
	    return updateTaskDao.save(new UpgradeTaskEntity(t)).toData();
	}

	@Override
	public void delete(String id) {
		updateTaskDao.delete(id);
	}

	@Transient
	public String getWhereSql(UpgradeTask t) {
		StringBuffer sb = new StringBuffer("where 1=1");
		if (t.getTenantId() != null){
            sb.append(" and a.tenantId = :tenantId ");
        }
        if (StringUtils.isNotBlank(t.getName())){
		    sb.append(" and a.name like :name");
        }
		return sb.toString();
	}
	
	@Transient
	public Map<String, Object> getWhereParam(UpgradeTask t) {
		Map<String, Object> params = new HashMap<String, Object>();
        if (t.getTenantId() != null){
            params.put("tenantId",t.getTenantId());
        }
        if (StringUtils.isNotBlank(t.getName())){
            params.put("name",t.getName());
        }
		
		return params;
			
	}



	
    @Override
	public Page getDataByPage(int pageNo, int pageSize, UpgradeTask obj, List<String> orderBysList) {
		StringBuffer hql = new StringBuffer(" from UpgradeTaskEntity a ");
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
		List<UpgradeTaskEntity> list = (List<UpgradeTaskEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	
	}

	

	@Override
	public void delete(UpgradeTask t) {
		updateTaskDao.delete(new UpgradeTaskEntity(t));
	}
	

	@Override
	public UpgradeTask getOne(String id) {
		
		UpgradeTaskEntity t = updateTaskDao.findOne(id);
		if(t==null) return null;
		return t.toData();
		
	}

	@Override
	public boolean exists(String id) {
		return updateTaskDao.exists(id);
	}

	@Override
	public void delete(Iterable<UpgradeTask> entities) {
		if(entities!=null){
			for(UpgradeTask t:entities){
				updateTaskDao.delete(new UpgradeTaskEntity(t));
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
    public Page getDataByPage(UpgradeTask obj, int pageNo, int pageSize) {
        return null;
    }

    @Override
	public void deleteByIds(String[] ids) {
		if(ids!=null && ids.length>0){
			for(String id:ids){
				updateTaskDao.delete(id);
			}
		}
	}

    /**
     * 任务新建
     *
     * @param task
     */
    @Override
    @Transactional
    public void saveTask(UpgradeTask task, String tenantId) {

        List<String> deviceIdList = Lists.newArrayList(task.getDeviceIdList().split(","));
        Date now = new Date();
        //初始化任务信息
        task.setDeviceNum(deviceIdList.size());
        task.setCreateTime(now);
        task.setSuccessNum(UpgradeTask.TASK_INIT);
        task.setStatus(UpgradeTask.TASK_INIT);
        task.setTenantId(tenantId);

        updateTaskDao.save(new UpgradeTaskEntity(task));
    }

    /**
     * 查询出任务下的所有设备
     *
     * @param taskId
     */
    @Override
    public List<Map<String,Object>> queryAllDeviceByTask(String taskId) {
        StringBuffer sql = new StringBuffer("select DISTINCT new map(a.devId as devId ,a.hardIdentity as hardIdentity,a.gatewayId as gatewayId,b.versionId as versionId) from DeviceEntity a,UpgradeRecordEntity b " +
                " where a.id = b.deviceId and b.taskId = :taskId ");
        Map<String,Object> params = new HashMap<>();
        params.put("taskId",taskId);

        List<Map<String,Object>> list = baseDao.queryByHql(sql.toString(),params);
        return list;
    }

    /**
     * 统计任务是否已开启
     *
     * @param status 状态
     * @param taskId 任务id
     * @return
     */
    @Override
    public Integer updateTaskStatus(Integer status, String taskId) {
        return updateTaskDao.updateTaskStatus(status, taskId);
    }

    /**
     * 设备升级后更新任务成功个数
     *
     * @param devId
     * @return
     */
    @Override
    public Integer updateTaskSuccessNum(String devId) {
        StringBuffer sql = new StringBuffer("UPDATE update_task set success_num=success_num+1 " +
                " where id = (SELECT task_id from update_record where device_id = :devId) " +
                " and success_num < update_task.device_num");
        Map<String,Object> params = new HashMap<>();
        params.put("devId",devId);

        int ret = baseDao.updateBySql(sql.toString(),params);
        return ret;
    }
}
