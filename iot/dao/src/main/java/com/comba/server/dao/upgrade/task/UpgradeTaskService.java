package com.comba.server.dao.upgrade.task;


import com.comba.server.common.data.UpgradeTask;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.CommonService;

import java.util.List;
import java.util.Map;

/**
 *  业务实现接口
 *
 * @作者 sujinxian
 * @创建时间 2018-01-25 16:21:15
 */
public interface UpgradeTaskService extends CommonService<UpgradeTask> {

    /**
     * @param pageNo
     * @param pageSize
     * @param task
     * @return
     * @throws Exception
     */
    Page getDataByPage(int pageNo, int pageSize, UpgradeTask task, List<String> orderBysList)throws Exception;

    /**
     * 批量删除
     * @param ids
     */
    void deleteByIds(String[] ids);

    /**
     * 通过多个ID查找租户
     * @param ids
     * @return
     */
    List<UpgradeTask> findByIds(Iterable<String> ids);

    /**
     * 任务新建
     */
    void saveTask(UpgradeTask task, String tenantId);

    /**
     * 查询出任务下的所有设备
     */
    List<Map<String,Object>> queryAllDeviceByTask(String taskId);

    /**
     * 统计任务是否已开启
     *
     * @param status 状态
     * @param taskId 任务id
     * @return
     */
    Integer updateTaskStatus(Integer status,String taskId);

    /**
     * 设备升级后更新任务成功个数
     *
     * @param devId
     * @return
     */
    Integer updateTaskSuccessNum(String devId);
}
