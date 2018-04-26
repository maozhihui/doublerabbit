package com.comba.server.dao.device;


import com.comba.server.common.data.Device;
import com.comba.server.common.data.device.DeviceCamera;

/**
 * 摄像头拓展字段 业务实现接口
 *
 * @作者 sujinxian
 * @创建时间 2018-02-06 09:56:37
 */
public interface DeviceCameraService {

    void save(Device device,String devId);

    void save(DeviceCamera camera);
}
