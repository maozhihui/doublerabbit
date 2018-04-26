package com.comba.mqtt.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comba.mqtt.controller.msg.DeviceData;
import com.comba.mqtt.controller.msg.DeviceMetaData;
import com.comba.mqtt.controller.msg.TreeNode;
import com.comba.mqtt.dao.model.DeviceDataEntity;
import com.comba.mqtt.dao.model.DeviceEntity;
import com.comba.mqtt.dao.service.CategoryService;
import com.comba.mqtt.dao.service.DeviceDataService;
import com.comba.mqtt.dao.service.DeviceService;
import com.comba.mqtt.dao.service.LatestDataService;
import com.comba.mqtt.util.JsonConverter;

/**
 * 处理设备管理功能的请求
 * @author maozhihui
 */
@RestController
@RequestMapping(value = "/api/v1")
public class DeviceMgtController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private DeviceDataService deviceDataService;
	
	@Autowired
	private LatestDataService latestDataService;
	
	@Autowired
	private DeviceService deviceService;
	
    private final static Integer CN = 1;
    private final static Integer EN = 2;

	/**
	 * 获取设备的当前的实时数据
	 * @param devId 设备的数据库ID
	 * @return {"devId":3,"dataStream":[{"time":12354566,"temperture":25.1},{"time":12354566,"humidity":32.2}]}
	 */
	@GetMapping(value = "/{devId}/realData")
	public String getRealTimeData(@PathVariable int devId){
		//List<LatestDataEntity> res = latestDataService.findByDevId(devId);
		DeviceData res = latestDataService.queryByDevId(devId);
		if (res == null ) {
			return "{}";
		}
		return JsonConverter.toJson(res);
	}
	
	/**
	 * 获取设备的历史数据
	 * @param devId 设备的数据库ID
	 * @param startTime 开始时间戳
	 * @param endTime 结束时间戳
	 * @return {"devId":3,"dataStream":[{"time":12354566,"temperture":25.1},{"time":12354566,"humidity":32.2}]}
	 */
	@GetMapping(value = "/{devId}/historyData")
	public String getHistoryData(@PathVariable int devId,
								@RequestParam(value = "startTime") long startTime,
								@RequestParam(value = "endTime") long endTime){
		if (startTime > endTime) {
			return "{}";
		}
		List<DeviceDataEntity> res = deviceDataService.findHistoryData(devId, new Date(startTime), new Date(endTime));
		if (res == null || res.size() == 0) {
			return "{}";
		}
		return JsonConverter.deviceDatatoJson(devId, res);
	}
	
	@GetMapping(value = "/menu")
	public List<TreeNode> getMenuData(Integer type){
		List<TreeNode> treeNodes = categoryService.findAll( type);
		for (TreeNode treeNode : treeNodes) {
			if (treeNode.getParentId() != -1) {
				List<DeviceEntity> devEntities = deviceService.findByCategoryId(treeNode.getId());
				for (DeviceEntity devEntity : devEntities) {
					DeviceMetaData metaData = new DeviceMetaData();
					metaData.setId(devEntity.getId());
					metaData.setDevType(devEntity.getDevType());
					metaData.setDevEui(devEntity.getDevEui());
					//
					if (type == CN ) {
						metaData.setDevName(devEntity.getDevName());
						metaData.setLocation(devEntity.getLocation());
					}else if(type == EN) {
						metaData.setDevName(devEntity.getDevNameEn());
						metaData.setLocation(devEntity.getLocationEn());
					}
					treeNode.addLeaf(metaData);
				}
			}
		}
		return treeNodes;
	}
}
