package com.comba.server.dao.device.alarm;

import com.comba.server.dao.model.device.ActiveAlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


/**
 * 活动告警 数据库实现类
 *
 * @作者 sujinxian
 * @创建时间 2018-03-01 11:24:16
 */
public interface ActiveAlarmDao extends JpaRepository<ActiveAlarmEntity, String>{

    ActiveAlarmEntity findByDevIdAndAlarmId(@Param(value = "devId") String devId, @Param(value = "alarmId") String alarmId);

    @Transactional
    @Modifying
    @Query("update ActiveAlarmEntity set startTime = now() where devId = :devId and alarmId = :alarmId")
    Integer updateActiveAlarmEntity(@Param(value = "devId") String devId, @Param(value = "alarmId") String alarmId);

    /**
     * 统计产品活动告警数
     *
     * @param productId 产品id
     * @return
     */
    long countByProductId(String productId);
}
