package com.comba.server.dao.upgrade.task;


import com.comba.server.dao.model.UpgradeTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 *  数据库实现类
 *
 * @作者 sujinxian
 * @创建时间 2018-01-25 16:21:15
 */
public interface UpgradeTaskDao extends JpaRepository<UpgradeTaskEntity, String> {

    /**
     * 统计任务是否已开启
     *
     * @param status 状态
     * @param taskId 任务id
     * @return
     */
    @Transactional
    @Modifying
    @Query(" update UpgradeTaskEntity set status=?1 where id = ?2")
    Integer updateTaskStatus(Integer status,String taskId);
}
