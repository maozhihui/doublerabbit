package com.comba.server.dao.upgrade.record;


import com.comba.server.dao.model.UpgradeRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 *  数据库实现类
 *
 * @作者 sujinxian
 * @创建时间 2018-01-25 16:21:07
 */
public interface UpgradeRecordDao extends JpaRepository<UpgradeRecordEntity, String> {

    /**
     * 根据设备id和z状态查询记录
     *
     * @param devId 设备id
     * @param status 状态
     * @return
     */
    UpgradeRecordEntity findByDeviceIdAndStatus(String devId, Integer status);

    /**
     * 更新记录状态
     *
     * @param id
     * @param status 状态
     * @return
     */
    @Transactional
    @Modifying
    @Query(" update UpgradeRecordEntity set status=?2 where id = ?1")
    Integer updateRecordStatus(String id,Integer status);
}
