package com.comba.web.controller.device;

import com.alibaba.fastjson.JSON;
import com.comba.server.common.data.Device;
import com.comba.server.common.data.device.DeviceCamera;
import com.comba.server.common.data.device.Product;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.device.DeviceCameraService;
import com.comba.server.dao.device.DeviceService;
import com.comba.server.dao.device.ProductService;
import com.comba.web.common.annotation.SystemControllerLog;
import com.comba.web.common.export.ExportUtils;
import com.comba.web.controller.BaseController;
import com.comba.web.lora.LoRaServerService;
import com.comba.web.response.ResponseBean;
import com.comba.web.security.SpringSecurityUtils;
import com.comba.web.utils.CommonProperties;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static com.comba.server.common.data.web.utils.Constants.CAMERA_CATEGORY_NAME;

/**
 * 设备管理
 * @author wengzhonghui
 *
 */
@Slf4j
@Controller
@RequestMapping("/device")
public class DeviceController extends BaseController{
	@Resource
	private DeviceService deviceService;

	@Autowired
    private ProductService productService;

	@Autowired
    private LoRaServerService loRoServerService;

	@Autowired
    private DeviceCameraService deviceCameraService;

	@Value("${lora.enable}")
    private String enable;

	@RequestMapping("/authorise")
	 public String authorise(@ModelAttribute  Page page, Model model) throws Exception {
		return "device/authorise/authorise_device";
	 }
	@RequestMapping("/authorise_user")
	 public String authoriseUser(@ModelAttribute  Page page, Model model) throws Exception {
		return "device/authorise/authorise_user";
	 }
	@RequestMapping("/list")
	 public String list(@ModelAttribute  Page page, Model model,HttpSession session) throws Exception {
		model.addAttribute(Constants.CUR_PRODUCT_ID, session.getAttribute(Constants.CUR_PRODUCT_ID));
		return "device/device/device_list";
	 }
	@ResponseBody
	 @RequestMapping("/datasByPage")
	 public Page getAuditDataByPage(Device device, Page page, HttpSession session)throws Exception{
		 page = page==null?new Page():page;
		 device.setTenantId(SpringSecurityUtils.getCurrentUserTenantId());
		 return deviceService.pagedQuery(page.getPageNo(), page.getPageSize(), device,page.getOrderBys());
	 }


	@ResponseBody
	 @RequestMapping("/datasByExport")
	 public ResponseBean  datasByExport(Device device, Page page, Integer organId, Integer isQueryChild
			 , String exportFlag, HttpSession session, @RequestParam Map<String, Object> parameterMap
			 )throws Exception{
		page.setPageSize(CommonProperties.maxAllowExportNum);
		Page dataPage = getAuditDataByPage(device, page,session);
		return ExportUtils.exportDatasByExcel(dataPage, exportFlag, session, parameterMap);
	 }

	@RequestMapping("/to_device_edit")
	public String deviceAdd(String deviceId, Model model,HttpSession session) throws Exception{

		Device device = null;
		String categoryId = null;
		String productId = session.getAttribute(Constants.CUR_PRODUCT_ID).toString();
		if(StringHelper.isNotEmpty(deviceId)){
			device = deviceService.getOne(deviceId);
            categoryId = device.getCategoryId();
		}else {
            Product product = productService.getOne(productId);
            categoryId = product.getCategoryId();
        }

        if (productService.queryProductByCategoryName(productId,CAMERA_CATEGORY_NAME)){
            model.addAttribute("cameraType", 1);
        }


		model.addAttribute("device", device);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute(Constants.CUR_PRODUCT_ID, productId);
		return "device/device/device_edit";
	}

	@RequestMapping("/device_view")
	public String deviceCheck(String deviceId, Model model) throws Exception{
		Device device = null;
		if(StringHelper.isNotEmpty(deviceId)){
			device = deviceService.getOne(deviceId);
		}
		model.addAttribute("device", device);

		return "device/device/device_view";
	}



	@RequestMapping(value="addDevice",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "添加设备")
	public ResponseBean addDevice(Device device, HttpServletRequest request, HttpSession session) throws Exception{
		ResponseBean responseBean = new ResponseBean(ResponseBean.FAIL);

        String productId = session.getAttribute(Constants.CUR_PRODUCT_ID).toString();
        if (StringUtils.isBlank(productId)){
            responseBean.setMessage("当前产品为空，请选择产品");
            return responseBean;
        }

		if (device.getIsLora() !=null && device.getIsLora() == 1){
            if (device.getIsGateWay()== 1){
                String hardIdentity = device.getHardIdentity().substring(2);
                if (loRoServerService.createGateway(device.getName(),hardIdentity,device.getDescription()) == null){
                    responseBean.setMessage("lora server 添加网关失败");
                    return responseBean;
                }
            }else{
                if (loRoServerService.createDevice(device.getName(),device.getHardIdentity(),device.getDescription()) ==null){
                    responseBean.setMessage("lora server 添加设备失败");
                    return responseBean;
                }

                if (loRoServerService.createAppkey(device.getHardIdentity(),device.getDescription()) == null){
                    loRoServerService.deleteDevice(device.getHardIdentity());
                    responseBean.setMessage("更新AppKey失败");
                    return responseBean;
                }
            }
        }

		device.setCreateTime(new Date());
		device.setUpdateTime(new Date());
		device.setTenantId(SpringSecurityUtils.getCurrentUserTenantId());
		DeviceCamera camera = null;
        if (productService.queryProductByCategoryName(productId,CAMERA_CATEGORY_NAME)){
            //摄像头设备需要保存拓展信息
            camera = device.getDeviceCamera();
            if (camera == null || StringUtils.isBlank(camera.getUserName()) || StringUtils.isBlank(camera.getPwd()) || StringUtils.isBlank(camera.getServerIp())
                || camera.getPort() == null){
                responseBean.setMessage("请输入摄像头的具体信息");
                return responseBean;
            }

        }

        deviceService.saveDeviceAndAttributes(device);
        request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加设备【"+device.getName()+"】");
		responseBean.setFlag_success();
		return responseBean;
	}


	@RequestMapping(value="updateDevice",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "更新设备")
	public ResponseBean updateDevice(Device device, HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean(ResponseBean.FAIL);
		if (StringUtils.isBlank(device.getTenantId())){
			device.setTenantId(SpringSecurityUtils.getCurrentUserTenantId());
		}

		Device deviceDB = deviceService.getOne(device.getDevId());
		if (deviceDB != null){
            device.setCreateTime(deviceDB.getCreateTime());
            device.setTenantId(deviceDB.getTenantId());
            device.setStatus(deviceDB.getStatus());
            device.setHardIdentity(deviceDB.getHardIdentity());
        }else{
		    responseBean.setMessage("设备不存在");
		    return responseBean;
        }

        if (device.getDeviceCamera() != null){
            device.getDeviceCamera().setId(deviceDB.getDeviceCamera().getId());
        }

        device.setUpdateTime(new Date());
		deviceService.save(device);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加设备【"+device.getName()+"】");
		if(device!=null && device.getName()!=null){
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "修改设备信息"+device.getName()+"】");
		}
		responseBean.setFlag_success();
		return responseBean;
	}
	@RequestMapping(value="deleteDevice",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除设备")
	public @ResponseBody  ResponseBean deleteGdevice(@RequestParam(value = "ids[]")  String[] deviceIds
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean(ResponseBean.FAIL);
		List<String> deviceIdList= Lists.newArrayList(deviceIds);

		StringBuilder logDesc = new StringBuilder();
		List<Device> deviceList = deviceService.findByIds(deviceIdList);
		if(deviceList!=null && deviceList.size()>0){
			for(Device device : deviceList){
				if(device!=null && device.getName()!=null){
					if(logDesc.length()>0) logDesc.append(",");
					logDesc.append(device.getName());
				}

                if (device.getIsLora()!= null && device.getIsLora() == 1){
                    if (device.getIsGateWay() == 1){
                        if (loRoServerService.deleteGateway(device.getHardIdentity().substring(2)) == null){
                            responseBean.setMessage("lora删除网关出错");
                            return responseBean;
                        }
                    }else{
                        if(loRoServerService.deleteDevice(device.getHardIdentity()) == null){
                            responseBean.setMessage("lora删除设备出错");
                            return responseBean;
                        }
                    }
                }
			}
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除设备【"+logDesc.toString()+"】");
		}
		deviceService.deleteByIds(deviceIdList);
		responseBean.setFlag_success();
		return responseBean;
	}

	@RequestMapping(value="deviceStatisByDay")
	@ResponseBody
	public Map<String,Object> deviceStatisByDay(Date startTime,Date endTime, String productId) throws Exception{
		List<Object[]> list = null;
		if(SpringSecurityUtils.isSupperAdmin()){
			list = deviceService.deviceStatisByDay(startTime, endTime);
		}else{
			list = deviceService.deviceStatisByDayOfProduct(startTime, endTime, productId);
		}

		Map<String,Object> dataM = new HashMap<String,Object>();
		List<String> legends = new ArrayList<String>();//显示的legend名称,一组值，对应一个legend
		List<Object> xaxis = new ArrayList<Object>();//X轴坐标
		List<List<Object>> datas = new ArrayList<List<Object>>();//Y轴具体统计值,可以有多组值
		List<Object> stadata1 = new ArrayList<Object>();
		legends.add("设备数量");
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] dt = list.get(i);
				if(dt.length>=2){
					stadata1.add(dt[0]);
					xaxis.add(dt[1]);
				}
			}
		}
		datas.add(stadata1);
		dataM.put("legends", legends);
		dataM.put("xaxis", xaxis);
		dataM.put("datas", datas);
		return dataM;
	}

	/**
	 * 统计设备是否为网关类型--管理员
	 *
	 * @author wengzhonghui 2017年7月3日
	 *  @return devNum_notGateWay:非网关设备的数量，devNum_gateWay：网关设备数量
	 * @throws Exception
	 */
	@RequestMapping(value="deviceStatisByIsGateWay")
	@ResponseBody
	public Map<String,Object> deviceStatisByIsGateWay() throws Exception{
		return deviceService.deviceStatisByIsGateWay();
	}

	/**
	 * 统计设备是否为网关类型--租户管理员通过产品ID获取
	 *
	 * @author wengzhonghui 2017年7月3日
	 *  @return devNum_notGateWay:非网关设备的数量，devNum_gateWay：网关设备数量
	 * @throws Exception
	 */
	@RequestMapping(value="deviceStatisByIsGateWayOfProduct")
	@ResponseBody
	public Map<String,Object> deviceStatisByIsGateWayOfProduct(String productId) throws Exception{
		if(productId==null || productId.length()==0){
			return new HashMap<String,Object>();
		}
		return deviceService.deviceStatisByIsGateWayOfProduct(productId);
	}

	/**
	 * 根据产品类型查询设备列表
	 *
	 * @param productId 产品ID
	 * @param page
	 * @return
	 */
	@GetMapping("/queryByProductId")
	@ResponseBody
	public Page queryByProductId(String productId,Page page){
		return deviceService.queryDeviceByProductId(productId,page);
	}

	@RequestMapping("/insert")
	 public String insert(@ModelAttribute  Page page, Model model,HttpSession session) throws Exception {
		return "device/device/device_insert";
	 }

	/**
	 * 读取设备excel文件
	 *
	 * @param deviceFile excel文件
	 * @return
	 */
    @PostMapping("/importExcel")
    @ResponseBody
	@SystemControllerLog(description = "导入设备文件")
    public ResponseBean importDeviceExcel(HttpServletRequest request, @RequestParam MultipartFile deviceFile, HttpSession session) {
		ResponseBean response = new ResponseBean(ResponseBean.FAIL);
        String productId = session.getAttribute(Constants.CUR_PRODUCT_ID).toString();
        String tenantId = SpringSecurityUtils.getCurrentUserTenantId();
		List<HSSFRow> deviceJpaList = null;

		try {
			deviceJpaList = ExportUtils.readExcel(deviceFile);
		} catch (IOException e) {
		    log.error("导入设备失败，文件出错,{}",e.getMessage());
            request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "导入设备失败，文件出错");
			response.setMessage("导入设备失败，文件出错");
			return response;
		}

        try {
            List<Device> list = deviceService.saveDeviceList(deviceJpaList,productId,tenantId);
            for (Device device:list){
                if (Constants.IS_LORA.equals(device.getIsLoraStr())){
                    if (device.getIsGateWay()== 1){
                        String hardIdentity = device.getHardIdentity().substring(2);
                        if (loRoServerService.createGateway(device.getName(),hardIdentity,device.getDescription()) == null){
                            response.setMessage("lora server 添加网关失败");
                            return response;
                        }
                    }else{
                        if (loRoServerService.createDevice(device.getName(),device.getHardIdentity(),device.getDescription()) ==null){
                            response.setMessage("lora server 添加设备失败");
                            return response;
                        }

                        if (loRoServerService.createAppkey(device.getHardIdentity(),device.getDescription()) == null){
                            loRoServerService.deleteDevice(device.getHardIdentity());
                            response.setMessage("更新AppKey失败");
                            return response;
                        }
                        }
                }
            }

            for (Device device : list){
                deviceService.saveDeviceAndAttributes(device);
            }

        } catch (Exception e) {
            request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "导入设备失败，保存记录失败"+e.getMessage());
            response.setMessage(e.getMessage());
            return response;
        }


        request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "导入设备成功");
        response.setFlag_success();
        return response;
    }

	/**
	 * 设备添加名称校验
	 *
	 * @param devId 设备ID
	 * @param name 名称
	 * @return
	 */
	@PostMapping("/validateDeviceName")
	@ResponseBody
    public ResponseBean validateDeviceName(String devId,String name){
    	String tenantId = SpringSecurityUtils.getCurrentUserTenantId();
    	ResponseBean response = new ResponseBean(ResponseBean.FAIL);

    	if (StringUtils.isNotBlank(devId)){
    		Device device = deviceService.getOne(devId);
    		if (!device.getName().equals(name)){
    			if (deviceService.countByTenantIdAndName(tenantId,name) > 0){
					response.setMessage("已存在相同名称的设备");
					return response;
				}
			}
		}else if(deviceService.countByTenantIdAndName(tenantId,name) > 0){
			response.setMessage("已存在相同名称的设备");
			return  response;
		}

		response.setFlag_success();
		return response;
	}

	/**
	 * 校验设备标识是否重复
	 *
	 * @param devId 设备ID
	 * @param hardIdentity 设备标识
	 * @return
	 */
	@PostMapping("/validateDeviceHardIdentity")
	@ResponseBody
	public ResponseBean validateDeviceHardIdentity(String devId,String hardIdentity){
		ResponseBean response = new ResponseBean(ResponseBean.FAIL);

		if (StringUtils.isNotBlank(devId)){
			Device device = deviceService.getOne(devId);
			if (!device.getHardIdentity().equals(hardIdentity) && deviceService.countByHardIdentity(hardIdentity) > 0){
					response.setMessage("已存在相同标识的设备");
					return response;
			}
		}else if(deviceService.countByHardIdentity(hardIdentity) > 0){
			response.setMessage("已存在相同标识的设备");
			return  response;
		}

		response.setFlag_success();
		return response;
	}

    /**
     * 导出设备模板文件
     *
     * @param parameterMap 列名列表
     * @return
     */
    @PostMapping("exportDeviceModelExcel")
    @ResponseBody
	public ResponseBean exportDeviceModelExcel(HttpSession session,@RequestParam Map<String, Object> parameterMap){
        //设置一行模板数据
	    List<List<?>> dataList = getDataList();
        List<String> sheetName = Lists.newArrayList("设备导入模板");

	    return ExportUtils.export(dataList,parameterMap,sheetName,session);
    }

    private List<List<?>> getDataList(){
        List<List<?>> dataList = Lists.newArrayList();
        List<Device> devices = Lists.newArrayList();
        Device device = new Device();
        device.setName("智能电灯");
        device.setHardIdentity("123");
        device.setDeviceTemplateName("温度设备模板");
        device.setSn("123");
        device.setIsGateWayStr("是");
        device.setDescription("智能电器");
        device.setPosition("设备位置");
        device.setIsLoraStr("否");
        device.setStatusStr(Constants.ONLINE);

        devices.add(device);
        dataList.add(devices);
        return dataList;
    }

    @GetMapping("/showCamera")
    public String showCameraWindow(String devId,Model model){
        Device device = deviceService.getOne(devId);
        if (device ==null){
            return "login";
        }

        model.addAttribute("ip",device.getDeviceCamera().getServerIp());
        model.addAttribute("port",device.getDeviceCamera().getPort());
        model.addAttribute("username",device.getDeviceCamera().getUserName());
        model.addAttribute("pwd",device.getDeviceCamera().getPwd());
        model.addAttribute("proxyIp",device.getDeviceCamera().getProxyIp());
        return "/device/camera/camera";
    }
}
