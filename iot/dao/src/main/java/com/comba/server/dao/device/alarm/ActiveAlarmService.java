package com.comba.server.dao.device.alarm;


import com.comba.server.common.data.device.ActiveAlarm;
import com.comba.server.common.data.device.AlarmRule;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.CommonService;

import java.util.List;

/**
 * 活动告警 业务实现接口
 *
 * @作者 sujinxian
 * @创建时间 2018-03-01 11:24:16
 */
public interface ActiveAlarmService extends CommonService<ActiveAlarm> {
    /**
     *
     * 分页查询 含排序
     *
     * @param pageNo
     * @param pageSize
     * @param obj
     * @param orderBysList
     * @return
     */
    public Page getDataByPage(int pageNo, int pageSize, ActiveAlarm obj, List<String> orderBysList);


    /**
     * 批量删除
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 通过多个ID查找租户
     * @param ids
     * @return
     */
    List<ActiveAlarm> findByIds(Iterable<String> ids);

    /**
     * 保存活动告警，如已有告警，则更新告警时间
     *
     * @param alarm 告警
     */
    void saveActiveAlarm(ActiveAlarm alarm);

    /**
     * 删除活动告警
     *
     * @param devId 设备id
     * @param alarmId 告警id
     * @return
     */
    boolean deleteActiveAlarm(String devId,String alarmId);

    /**
     * 是否存在该类告警信息
     *
     * @param devId
     * @param alarmId
     * @return
     */
    ActiveAlarm isExitActiveAlarm(String devId,String alarmId);

    /**
     *  分页查询活动告警，支持设备名称模糊查询
     *
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @param obj 实体
     * @return
     */
    Page queryAlarmByPage(int pageNo, int pageSize, ActiveAlarm obj);

    /**
     * 统计产品活动告警数
     *
     * @param productId 产品id
     * @return
     */
    long countByProductId(String productId);
}
