package com.comba.server.dao.device;


import com.comba.server.dao.model.device.HistoryDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;


/**
 * 参数模板数据层
 * @author huangjinlong
 *
 */
public interface HistoryDataDao extends JpaRepository<HistoryDataEntity, String>{

    /**
     * 查询产品的今日的遥测数据量
     *
     * @param productId 产品
     * @return
     */
    @Query("select count(h)  from HistoryDataEntity h where h.updateTime >= :updateTime and  h.devId in (select d.id from DeviceEntity d where d.productId = :productId)")
    Long countByProductId(@Param("productId") String productId, @Param("updateTime") LocalDateTime updateTime);
}
