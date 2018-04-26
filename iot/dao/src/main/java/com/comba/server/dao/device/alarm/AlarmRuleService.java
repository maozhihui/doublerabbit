package com.comba.server.dao.device.alarm;

import com.comba.server.common.data.device.AlarmRule;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.CommonService;

import java.util.List;


/**
 * 告警规则 业务实现接口
 *
 * @作者 sujinxian
 * @创建时间 2018-02-28 15:55:17
 */
public interface AlarmRuleService extends CommonService<AlarmRule> {

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
    public Page getDataByPage(int pageNo, int pageSize, AlarmRule obj, List<String> orderBysList);


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
    List<AlarmRule> findByIds(Iterable<String> ids);

    /**
     * 查询该租户下面的所有规则
     *
     * @param productId
     * @return
     */
    List<AlarmRule> findByProductId(String productId);

}
