package com.comba.server.dao.device.alarm;

import com.comba.server.common.data.device.HistoryAlarm;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.CommonService;

import java.util.List;

/**
 * 历史告警 业务实现接口
 *
 * @作者 sujinxian
 * @创建时间 2018-03-05 09:18:44
 */
public interface HistoryAlarmService extends CommonService<HistoryAlarm> {
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
    public Page getDataByPage(int pageNo, int pageSize, HistoryAlarm obj, List<String> orderBysList);


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
    List<HistoryAlarm> findByIds(Iterable<String> ids);

    /**
     *  分页查询活动告警，支持设备名称模糊查询
     *
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @param obj 实体
     * @return
     */
    Page queryAlarmByPage(int pageNo, int pageSize, HistoryAlarm obj);
}
