package com.comba.server.dao.ruleDevice;

import com.comba.server.common.data.device.RuleDevice;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.device.DeviceEntity;
import com.comba.server.dao.model.device.RuleDeviceEntity;
import com.comba.server.dao.util.DaoUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sujinxian on 2017/7/13.
 */
@Service
public class RuleDeviceServiceImpl implements RuleDeviceService{

    @Autowired
    private RuleDeviceDao ruleDeviceJpaDao;

    @Resource
    private BaseDao baseDao;

    /**
     * 查询所有对象
     *
     * @return
     */
    @Override
    public List<RuleDevice> findAll() {
        List<RuleDeviceEntity> list = ruleDeviceJpaDao.findAll();
        return DaoUtil.convertDataList(list);
    }

    /**
     * 保存对象
     *
     * @param obj
     */
    @Override
    public RuleDevice save(RuleDevice obj) {
        return ruleDeviceJpaDao.save(new RuleDeviceEntity(obj)).toData();
    }

    /**
     * 根据ID删除对象
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        ruleDeviceJpaDao.delete(id);
    }

    /**
     * 删除对象
     *
     * @param obj
     */
    @Override
    public void delete(RuleDevice obj) {
        ruleDeviceJpaDao.delete(new RuleDeviceEntity());
    }

    /**
     * 根据ID获取一个对象
     *
     * @param id
     * @return
     */
    @Override
    public RuleDevice getOne(String id) {
        RuleDeviceEntity ruleDeviceEntity =  ruleDeviceJpaDao.getOne(id);
        if (ruleDeviceEntity == null ) return null;
        return ruleDeviceEntity.toData();
    }

    /**
     * 根据ID判断对象是否存在
     *
     * @param id
     * @return
     */
    @Override
    public boolean exists(String id) {
        return false;
    }

    /**
     * 删除对象集合
     *
     * @param entities
     */
    @Override
    public void delete(Iterable<RuleDevice> entities) {
        for (RuleDevice ruleDevice: entities){
            ruleDeviceJpaDao.delete(ruleDevice.getRuleId());
        }
    }

    /**
     * 分页查询（含排序功能）
     *
     * @param ruleDevice
     * @param pageNo
     * @param pageSize @return
     */
    @Override
    public Page getDataByPage(RuleDevice ruleDevice, int pageNo, int pageSize) {
        StringBuffer hql = new StringBuffer(" from RuleDeviceEntity a ");
        Map<String, Object> params = new HashMap<String, Object>();
        if(ruleDevice!=null){
            hql.append(getWhereSql(ruleDevice));
            params = getWhereParam(ruleDevice);
        }
        String queryHql = hql.toString();

        Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
        List<DeviceEntity> list = (List<DeviceEntity>)resultPage.getResult();
        resultPage.setResult(DaoUtil.convertDataList(list));
        return resultPage;
    }


    public String getWhereSql(RuleDevice device) {
        StringBuffer sb = new StringBuffer("where 1=1");
        if(device.getDeviceId()!=null)
            sb.append(" and a.deviceId = :deviceId ");
        if(device.getRuleId()!=null)
            sb.append(" and a.ruleId = :ruleId ");

        return sb.toString();
    }

    public Map<String, Object> getWhereParam(RuleDevice device) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(device.getDeviceId()!=null)
            params.put("devId", device.getDeviceId());
        if(device.getRuleId()!=null)
            params.put("name", device.getRuleId().trim());
        return params;

    }
}
