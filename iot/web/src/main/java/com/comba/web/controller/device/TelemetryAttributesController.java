package com.comba.web.controller.device;

import com.comba.server.common.data.device.TelemetryAttributes;
import com.comba.server.common.data.device.TelemetryDTO;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.device.TelemetryAttributesService;
import com.comba.server.dao.util.DateConvertUtil;
import com.comba.web.common.annotation.SystemControllerLog;
import com.comba.web.common.export.ExportUtils;
import com.comba.web.controller.BaseController;
import com.comba.web.response.ResponseBean;
import com.comba.web.utils.CommonProperties;
import com.google.common.collect.Lists;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;
/**
 * 遥测参数管理
 * @author wengzhonghui
 *
 */
@Controller
@RequestMapping("/telemetryAttributes")
public class TelemetryAttributesController extends BaseController{
	@Resource
	private TelemetryAttributesService telemetryAttributesService;

	@RequestMapping("/list")
	 public String list(@ModelAttribute  Page page, Model model,String devId) throws Exception {
		return "device/telemetryAttributes/telemetryAttributes_list";
	 }
	
	
	@RequestMapping("/param")
	 public String param(@ModelAttribute  Page page, Model model,String deviceTemplateId) throws Exception {
		return "device/telemetryAttributes/telemetryAttributes_param";
	 }


	
	@RequestMapping("/to_telemetryAttributes_edit")
	public String userAdd(String telemetryAttributesId, Model model) throws Exception{
		TelemetryAttributes telemetryAttributes = null;
		if(StringHelper.isNotEmpty(telemetryAttributesId)){
			telemetryAttributes = telemetryAttributesService.getOne(telemetryAttributesId);
		}
		model.addAttribute("telemetryAttributes", telemetryAttributes);
		
		return "device/telemetryAttributes/telemetryAttributes_edit";
	}
	
	@RequestMapping("/telemetryAttributes_view")
	public String userCheck(String telemetryAttributesId, Model model) throws Exception{
		TelemetryAttributes telemetryAttributes = null;
		if(StringHelper.isNotEmpty(telemetryAttributesId)){
			telemetryAttributes = telemetryAttributesService.getOne(telemetryAttributesId);
		}
		model.addAttribute("telemetryAttributes", telemetryAttributes);
		
		return "device/telemetryAttributes/telemetryAttributes_view";
	}

	
	
	@RequestMapping("/get_telemetryAttributesData")
	@ResponseBody
	public TelemetryAttributes get_telemetryAttributesData(String attributesTemplateId) throws Exception{
		TelemetryAttributes attributesTemplate = null;
		if(StringHelper.isNotEmpty(attributesTemplateId)){
			attributesTemplate = telemetryAttributesService.getOne(attributesTemplateId);
		}
		return attributesTemplate;
	}
	
	@RequestMapping(value="statisByDevAndAttributeName")
	@ResponseBody
	public Map<String,Object> deviceStatisByDay(Date startTime, Date endTime, String devId, String attributeName) throws Exception{
		Map<String,Object> dataM = new HashMap<>();
		List<String> legends = new ArrayList<>();//显示的legend名称,一组值，对应一个legend
		List<Object> x_axis = new ArrayList<>();//X轴坐标
		List<List<Object>> datas = new ArrayList<>();//Y轴具体统计值,可以有多组值
		List<String> attributeNames = Lists.newArrayList(attributeName.split(","));
		LocalDate startDate = DateConvertUtil.convertToLocalDateTime(startTime).toLocalDate();
		LocalDate endDate = DateConvertUtil.convertToLocalDateTime(endTime).toLocalDate().plusDays(1);

		boolean isRun = false;
		for (String name:attributeNames){
			List<Object> dataList = new ArrayList<>();
			List<Object[]> list = (List<Object[]>)telemetryAttributesService.attributeStaticsByDevIdAndAttributeName(startDate, endDate, devId, name);
			legends.add(name);

			LocalDate calDate = startDate;
			//X轴取每一天，Y轴取实际的值，没有则默认为0
			while (calDate.isBefore(endDate)){
				Object data = 0;
				String calDateStr = DateConvertUtil.LocalDateToStr(calDate);
                for (Object[] object :list){
                    String date = (String) object[1];
                    if (date.equals(calDateStr)){
                        data = object[0];
                        break;
                    }
                }
				if (!isRun){
					x_axis.add(calDateStr);
				}
				dataList.add(data);
				calDate = calDate.plusDays(1);
			}
			isRun = true;
			datas.add(dataList);
		}

		dataM.put("legends", legends);
		dataM.put("xaxis", x_axis);
		dataM.put("datas", datas);
		return dataM;
	}
	
	
	/**
	 * 按天统计某个产品的遥测数据
	 * 
	 * @author wengzhonghui 2017年7月14日
	 * @param startTime
	 * @param endTime
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="statisByDay")
	@ResponseBody
	public Map<String,Object> deviceStatisByDay(Date startTime,Date endTime, String productId) throws Exception{
		List<Object[]> list = null;
		list = telemetryAttributesService.attributeStatisByDayOfProduct(startTime, endTime, productId);
		
		Map<String,Object> dataM = new HashMap<String,Object>();
		List<String> legends = new ArrayList<String>();//显示的legend名称,一组值，对应一个legend
		List<Object> xaxis = new ArrayList<Object>();//X轴坐标
		List<List<Object>> datas = new ArrayList<List<Object>>();//Y轴具体统计值,可以有多组值 
		List<Object> stadata1 = new ArrayList<Object>();
		legends.add("遥测数据");
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
     * 导出设备每天的遥测数据量
     *
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param devId         设备ID
     * @param attributeName 属性名
     * @return
     */
    @PostMapping("/exportTelemetryAttributesByDay")
    @ResponseBody
    public ResponseBean exportTelemetryAttributesByDay(Date startTime,
                                                       Date endTime,
                                                       String devId,
                                                       String attributeName,
                                                       @RequestParam Map<String ,Object> parameterMap,
                                                       HttpSession session){
        List<String> attributeNames = Lists.newArrayList(attributeName.split(","));
        LocalDate startDate = DateConvertUtil.convertToLocalDateTime(startTime).toLocalDate();
        LocalDate endDate = DateConvertUtil.convertToLocalDateTime(endTime).toLocalDate().plusDays(1);
        List<List<?>> dataList = new ArrayList<>();


        for (String name: attributeNames){
            List<Object[]> list = (List<Object[]>)telemetryAttributesService.attributeStaticsByDevIdAndAttributeName(startDate, endDate, devId, name);
            List<TelemetryDTO> telemetryDTOS = Lists.newArrayList();
            LocalDate calDate = startDate;
            while (calDate.isBefore(endDate)){
                TelemetryDTO dto = new TelemetryDTO();
                Object data = 0;
                String calDateStr = DateConvertUtil.LocalDateToStr(calDate);
                for (Object[] object :list){
                    String date = (String) object[1];
                    if (date.equals(calDateStr)){
                        data = object[0];
                        break;
                    }
                }
                dto.setDeviceNum(data.toString());
                dto.setUploadTime(calDateStr);
                telemetryDTOS.add(dto);
                calDate = calDate.plusDays(1);
            }
            dataList.add(telemetryDTOS);
        }


        return ExportUtils.export(dataList, parameterMap, attributeNames,session);
    }
}
