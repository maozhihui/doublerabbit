package com.comba.server.dao.device;

import com.comba.server.common.data.Device;
import com.comba.server.common.data.device.DeviceCamera;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.device.DeviceCameraEntity;
import com.comba.server.dao.util.DaoUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;



/**
 * 摄像头拓展字段 业务实现类
 *
 * @作者 sujinxian
 * @创建时间 2018-02-06 09:56:37
 */
@Service("deviceCameraService")
public class DeviceCameraServiceImpl implements DeviceCameraService {
	@Resource
	private DeviceCameraDao deviceCameraDao;
	
	@Resource
	private BaseDao baseDao;

    @Override
    public void save(Device device,String devId) {
        DeviceCameraEntity deviceCamera = new DeviceCameraEntity();
        deviceCamera.setDevId(devId);


        deviceCameraDao.save(deviceCamera);
    }

    @Override
    public void save(DeviceCamera camera) {
        deviceCameraDao.save(new DeviceCameraEntity(camera));
    }
}
