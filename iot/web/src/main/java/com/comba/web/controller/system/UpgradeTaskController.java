package com.comba.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.comba.server.actors.service.ActorService;
import com.comba.server.common.data.Device;
import com.comba.server.common.data.UpgradeRecord;
import com.comba.server.common.data.UpgradeTask;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.msg.cmd.BasicCommandMsg;
import com.comba.server.common.msg.cmd.UpgradeCmdRequestMsg;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.device.DeviceService;
import com.comba.server.dao.upgrade.record.UpgradeRecordService;
import com.comba.server.dao.upgrade.task.UpgradeTaskService;
import com.comba.web.common.annotation.SystemControllerLog;
import com.comba.web.response.ResponseBean;
import com.comba.web.security.SpringSecurityUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *  界面控制层实现
 *
 * @作者 sujinxian
 * @创建时间 2018-01-25 16:21:15
 */
@Slf4j
@Controller
@RequestMapping("/upgradeTask")
public class UpgradeTaskController {
	@Resource
	private UpgradeTaskService updateTaskService;

	@Resource
    private ActorService actorService;

    @Resource
    private UpgradeRecordService updateRecordService;

    @Autowired
    private DeviceService deviceService;
	
	/**
	 * 进入列表界面
	 */
	@RequestMapping("/upgradeTask_list")
	 public String list(Model model) throws Exception {
		return "system/upgradeTask/upgradeTask_list";
	 }
	
	 /**
	 * 获取分页数据
	 */
	 @ResponseBody
	 @RequestMapping("/datasByPage")
	 public Page getDataByPage(UpgradeTask updateTask, Page page)throws Exception{
		 page = page==null?new Page():page;
		 Page returnPage = null;
		 List<String> orderBysList = page.getOrderBys();
         String tenantId = SpringSecurityUtils.getCurrentUserTenantId();
         if (StringUtils.isNotBlank(tenantId)){
             updateTask.setTenantId(tenantId);
         }
		 returnPage = updateTaskService.getDataByPage(page.getPageNo(), page.getPageSize(), updateTask,orderBysList);
		 return returnPage;
	 }
	
	/**
	 * 进入编辑界面
	 */
	@RequestMapping("/to_upgradeTask_edit")
	public String updateTaskEdit(String id, Model model) throws Exception{
		
		UpgradeTask upgradeTask = null;
		if(StringHelper.isNotEmpty(id))
            upgradeTask = updateTaskService.getOne(id);

		model.addAttribute("updateTask", upgradeTask);
		return "system/upgradeTask/upgradeTask_edit";
	}

	
	/**
	 * 添加
	 * 
	 */
	@RequestMapping(value="addUpgradeTask",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "添加")
	public ResponseBean addUpdateTask(UpgradeTask updateTask, Model model
			, HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean(ResponseBean.FAIL);

		String tenantId = SpringSecurityUtils.getCurrentUserTenantId();
		if (StringUtils.isBlank(tenantId)){
		    responseBean.setMessage("添加任务失败，租户id为空");
		    return responseBean;
        }

        if (updateTask.getDeviceIdList() == null || updateTask.getDeviceIdList().isEmpty()){
		    responseBean.setMessage("添加任务失败，设备id列表为空");
            return responseBean;
        }

		updateTaskService.saveTask(updateTask,tenantId);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加任务 【"+ updateTask.getName()+"】");
		responseBean.setFlag_success();
		return responseBean;
		
	}

    /**
     * 更新
     *
     * @param updateTask 任务
     * @return
     */
    @Transactional
    @PostMapping("updateTask")
    @ResponseBody
    @SystemControllerLog(description = "修改")
    public ResponseBean updateUpdateTask(UpgradeTask updateTask
            , HttpServletRequest request){
        ResponseBean responseBean = new ResponseBean();
        String tenantId = SpringSecurityUtils.getCurrentUserTenantId();

        UpgradeTask task = updateTaskService.getOne(updateTask.getId());
        if (task == null){
            responseBean.setMessage("更新任务失败，id为空");
            return responseBean;
        }

        if (UpgradeTask.TASK_START.equals(task.getStatus())){
            responseBean.setMessage("更新任务失败，任务正在执行中");
            return responseBean;
        }

        if (updateTask.getDeviceIdList() == null || updateTask.getDeviceIdList().isEmpty()){
            responseBean.setMessage("添加任务失败，设备id列表为空");
            return responseBean;
        }


        updateTaskService.saveTask(updateTask,tenantId);
        request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "修改任务 【"+ updateTask.getName()+"】");
        responseBean.setFlag_success();
        return responseBean;
    }
	
	/**
	 * 删除
	 */
	@RequestMapping(value="deleteUpgradeTask",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除")
	public @ResponseBody  ResponseBean deleteupdateTask(@RequestParam(value = "ids[]")  String[] ids
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		if(ids == null || ids.length < 1){
			return responseBean;
		}
		List<String> updateTaskIdList= new ArrayList<>();
		for(String updateTaskId : ids){
			updateTaskIdList.add(updateTaskId);
		}
		StringBuilder logDesc = new StringBuilder();
		List<UpgradeTask> updateTaskList = updateTaskService.findByIds(updateTaskIdList);
		for (UpgradeTask task : updateTaskList){
		    if(task.getStatus() < 2){
                responseBean.setMessage("任务未完成！");
                return responseBean;
            }
            logDesc.append(task.getName());
            logDesc.append(" ");
		}

        request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除升级任务 【"+logDesc.toString()+"】");

		updateTaskService.deleteByIds(ids);
		responseBean.setFlag_success();
		return responseBean;
	}

    /**
     * 开启任务
     *
     * @return
     */
    @PostMapping("/start")
    @ResponseBody
    @Transactional
	public ResponseBean startTask(String taskId,HttpSession session){
        ResponseBean responseBean = new ResponseBean(ResponseBean.FAIL);
        String  tenantId = SpringSecurityUtils.getCurrentUserTenantId();
        if (StringUtils.isBlank(tenantId)){
            responseBean.setMessage("获取租户id失败");
            return responseBean;
        }

        UpgradeTask task = updateTaskService.getOne(taskId);
        if (UpgradeTask.TASK_START.equals(task.getStatus())){
            responseBean.setMessage("任务正在执行中");
            return responseBean;
        }

        List<String> idList = Lists.newArrayList(task.getDeviceIdList().split(","));
        List<Device> list = deviceService.findByDevIdList(idList);
        for (Device device:list){
            String devId = device.getDevId();
            UpgradeRecord record = new UpgradeRecord(task.getVersionId(), taskId, devId, tenantId, UpgradeRecord.INIT, new Date());
            updateRecordService.save(record);
            //设备是通过网关连接的话，需要设置网关id
            DeviceId gatewayId = device.getGatewayId() !=null?new DeviceId(UUIDUtils.toUUID(device.getGatewayId())):new DeviceId(UUIDUtils.toUUID(devId));

            UpgradeCmdRequestMsg msg = new UpgradeCmdRequestMsg();
            msg.setDeviceId(new DeviceId(UUIDUtils.toUUID(devId)));
            msg.setHardIdentity(device.getHardIdentity());

            BasicCommandMsg<UpgradeCmdRequestMsg> cmdMsg = new BasicCommandMsg<>(new TenantId(UUIDUtils.toUUID(tenantId)), gatewayId, session.getId(), msg);
            cmdMsg.setCreateTime(new Date().getTime());
            actorService.onCommandProcess(cmdMsg);
            log.info("消息下发成功，devId{}", JSON.toJSONString(cmdMsg.getDeviceId()));
        }

        //更新任务状态为已开启
        updateTaskService.updateTaskStatus(UpgradeTask.TASK_START,taskId);

        responseBean.setFlag_success();
        return responseBean;
    }

}
