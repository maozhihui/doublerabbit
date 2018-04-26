package com.comba.server.dao.device;

import com.comba.server.common.data.device.AttributeData;
import com.comba.server.dao.model.device.LatestDataEntity;

import java.util.List;
import java.util.Map;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/12/25 12:52
 **/
public interface LatestDataService {

    void save(LatestDataEntity entity);

    /**
     * 查询设备最新属性数据 2017-10-13 add by mzh
     * @param devId
     * @return
     */
    List<AttributeData> getLatestDeviceData(String devId);

    /**
     * 获取设备的最新上报时间
     *
     * @param devIds 设备id列表
     * @return
     */
    List<Map<String, Object>> getDeviceLatestUpdateTime(List<String> devIds);

    long getLatestTime(String devId);

    void save(String devId,Map<String,String> dataMap);
}
