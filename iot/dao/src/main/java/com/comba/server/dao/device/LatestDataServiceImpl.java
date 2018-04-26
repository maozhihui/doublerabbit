package com.comba.server.dao.device;

import com.comba.server.common.data.device.AttributeData;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.device.LatestDataEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/12/25 12:53
 **/
@Slf4j
@Service
public class LatestDataServiceImpl implements LatestDataService {

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private LatestDataDao dao;

    @Override
    public void save(LatestDataEntity entity) {
        LatestDataEntity currentEntity = dao.findByAttributeNameAndDevId(entity.getAttributeName(),entity.getDevId());
        if (currentEntity != null){
            currentEntity.setValue(entity.getValue());
            currentEntity.setUpdateTime(entity.getUpdateTime());
            dao.save(currentEntity);
        }else {
            dao.save(entity);
        }
    }

    @Override
    public List<AttributeData> getLatestDeviceData(String devId) {
        List<AttributeData> res = new ArrayList<>();
        List<LatestDataEntity> dataEntities = dao.findByDevId(devId);
        for (LatestDataEntity entity : dataEntities){
            AttributeData data = new AttributeData(entity.getAttributeName(),entity.getValue(),
                    entity.getUpdateTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            res.add(data);
        }
        return res;
    }

    /**
     * 获取设备的最新上报时间
     *
     * @param devIds 设备id列表
     * @return
     */
    public List<Map<String, Object>> getDeviceLatestUpdateTime(List<String> devIds){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT DEV_ID as did,DATE_FORMAT((UPDATE_TIME),'%Y-%m-%d %H:%i:%s') as updateTime,VALUE as currentValue ,ATTRIBUTE_NAME as name " +
                " FROM latest_data " +
                " WHERE DEV_ID IN (:devIds) ");

        Map<String,Object> params = new HashMap<>();
        params.put("devIds",devIds);
        List<Map<String,Object>> map =  baseDao.queryBySql(sql.toString(),params, Transformers.ALIAS_TO_ENTITY_MAP);
        return map;
    }

    @Override
    public void save(String devId, Map<String, String> dataMap) {
        for (Map.Entry<String,String> entry : dataMap.entrySet()){
            LatestDataEntity latestDataEntity = dao.findByAttributeNameAndDevId(entry.getKey(),devId);
            if (latestDataEntity == null){
                log.warn("latest_data does not exist,attributeName [{}],devId [{}]",entry.getKey(),devId);
                latestDataEntity = new LatestDataEntity();
                latestDataEntity.setAttributeId("");
            }
            latestDataEntity.setFloatValue(0);
            latestDataEntity.setIntValue(0);
            latestDataEntity.setUpdateTime(LocalDateTime.now());
            latestDataEntity.setAttributeName(entry.getKey());
            latestDataEntity.setValue(entry.getValue());
            latestDataEntity.setDevId(devId);
            dao.saveAndFlush(latestDataEntity);
        }
    }

    @Override
    public long getLatestTime(String devId){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT MAX(update_time) AS latestTime FROM latest_data WHERE dev_id=:devId");
        Map<String,Object> params = new HashMap<>();
        params.put("devId",devId);
        List<Object> res = baseDao.queryBySql(sql.toString(),params);
        if (!res.isEmpty() && (res.get(0) != null)){
            return ((Timestamp)(res.get(0))).getTime();
        }else {
            return 0L;
        }
    }
}
