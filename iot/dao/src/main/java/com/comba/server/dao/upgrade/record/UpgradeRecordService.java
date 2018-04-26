package com.comba.server.dao.upgrade.record;


import com.comba.server.common.data.UpgradeRecord;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.CommonService;

import java.util.List;
import java.util.Map;

/**
 *  业务实现接口
 *
 * @作者 sujinxian
 * @创建时间 2018-01-25 16:21:07
 */
public interface UpgradeRecordService extends CommonService<UpgradeRecord> {

    public Page getDataByPage(int pageNo, int pageSize, UpgradeRecord obj, List<String> orderBysList);

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
    List<UpgradeRecord> findByIds(Iterable<String> ids);

    /**
     * 根据设备id和z状态查询记录
     *
     * @param devId 设备id
     * @param status 状态
     * @return
     */
    UpgradeRecord findByDeviceIdAndStatus(String devId, Integer status);

    /**
     * 获取更新文件路径
     *
     * @param devId 设备ID
     * @param status 状态
     * @return
     */
    List<String> getUpdatePath(String devId,Integer status);

    /**
     * 更新记录状态
     *
     * @param devId 设备id
     * @param status 状态
     * @return
     */
    Integer updateRecordStatus(String devId,Integer status);

    /**
     * 查询指定设备最新的一条记录
     *
     * @param devId 设备
     * @return
     */
    UpgradeRecord queryLatestRecordByDevId(String devId);

    /**
     * 更新设备最新一条记录状态
     *
     * @param devId 设备id
     * @param status 状态
     * @return
     */
    Integer updateLatestRecordStatus(String devId,Integer status);

}
