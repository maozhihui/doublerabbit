package com.comba.server.dao.device.alarm;

import com.comba.server.dao.model.device.HistoryAlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;




/**
 * 历史告警 数据库实现类
 *
 * @作者 sujinxian
 * @创建时间 2018-03-05 09:18:44
 */
public interface HistoryAlarmDao extends JpaRepository<HistoryAlarmEntity, String>{

}
