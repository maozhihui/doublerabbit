package com.comba.server.dao.device;

import com.comba.server.dao.model.device.AlarmContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 告警信息 数据库实现类
 *
 * @作者 sujinxian
 * @创建时间 2018-02-26 16:33:41
 */
public interface AlarmContentDao extends JpaRepository<AlarmContentEntity, String>{

}
