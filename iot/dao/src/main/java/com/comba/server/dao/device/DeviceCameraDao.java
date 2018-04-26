package com.comba.server.dao.device;


import com.comba.server.dao.model.device.DeviceCameraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 摄像头拓展字段 数据库实现类
 *
 * @作者 sujinxian
 * @创建时间 2018-02-06 09:56:37
 */
public interface DeviceCameraDao extends JpaRepository<DeviceCameraEntity, String> {

}
