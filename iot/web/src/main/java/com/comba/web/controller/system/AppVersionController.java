package com.comba.web.controller.system;

import com.comba.server.common.data.AppVersion;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.upgrade.AppVersionService;
import com.comba.web.common.annotation.SystemControllerLog;
import com.comba.web.common.export.ExportUtils;
import com.comba.web.response.ResponseBean;
import com.comba.web.security.SpringSecurityUtils;
import com.comba.web.utils.CommonProperties;
import com.comba.web.utils.FtpUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *  界面控制层实现
 *
 * @作者 sujinxian
 * @创建时间 2018-01-24 16:49:36
 */
@Slf4j
@Controller
@RequestMapping("/appVersion") 
public class AppVersionController{
	@Resource
	private AppVersionService appVersionService;

    @Value("${upgrade.path}")
    private String updatePath;
	
	/**
	 * 进入列表界面
	 */
	@RequestMapping("/list")
	 public String list(Model model) throws Exception {
		return "system/appVersion/appVersion_list";
	 }
	
	 /**
	 * 获取分页数据
	 */
	 @ResponseBody
	 @RequestMapping("/datasByPage")
	 public Page getDataByPage(AppVersion appVersion, Page page)throws Exception{
		 page = page==null?new Page():page;
		 Page returnPage = null;
		 List<String> orderBysList = page.getOrderBys();
         String tenantId = SpringSecurityUtils.getCurrentUserTenantId();
         if (StringUtils.isNotBlank(tenantId)){
             appVersion.setTenantId(tenantId);
         }
		 returnPage = appVersionService.getDataByPage(appVersion,page.getPageNo(), page.getPageSize());
		 return returnPage;
	 }
	
	 /**
	 * 导出列表数据
	 */
	@ResponseBody
	 @RequestMapping("/datasByExport")
	 public ResponseBean ExportData(AppVersion appVersion, Page page, HttpSession session
			 , @RequestParam Map<String, Object> parameterMap, String exportFlag
			 )throws Exception{
		 page = page==null?new Page():page;
		 Page returnPage = null;
		 List<String> orderBysList = page.getOrderBys();
		 page.setPageSize(CommonProperties.maxAllowExportNum);
		 returnPage = appVersionService.getDataByPage(appVersion,page.getPageNo(), page.getPageSize());
		return ExportUtils.exportDatasByExcel(returnPage, exportFlag, session, parameterMap);
	 }
	
	
	/**
	 * 进入编辑界面
	 */
	@RequestMapping("/to_appVersion_edit")
	public String appVersionEdit(String id, Model model) throws Exception{
		
		AppVersion appVersion = null;
		if(StringHelper.isNotEmpty(id))
			appVersion = appVersionService.getOne(id);
		model.addAttribute("appVersion", appVersion);
		
		return "system/appVersion/appVersion_edit";
	}
	
	/**
	 * 进入查看界面
	 * 
	 */
	@RequestMapping("/to_appVersion_view")
	public String appVersionView(String id, Model model) throws Exception{
		AppVersion appVersion = appVersionService.getOne(id);
			
		model.addAttribute("appVersion", appVersion);
		
		return "system/appVersion/appVersion_view";
	}
	
	
	
	/**
	 * 添加
	 * 
	 */
	@RequestMapping(value="addAppVersion",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "添加")
	public ResponseBean addAppVersion(AppVersion appVersion, Model model
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		appVersion.setCreateTime(new Date());
        String tenantId = SpringSecurityUtils.getCurrentUserTenantId();
        if (StringUtils.isNotBlank(tenantId)){
            appVersion.setTenantId(tenantId);
        }
		appVersionService.save(appVersion);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加版本"+appVersion.getFileName());
		responseBean.setFlag_success();
		return responseBean;
		
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value="updateAppVersion",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "更新")
	public ResponseBean updateAppVersion(AppVersion appVersion,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		appVersionService.save(appVersion);
		responseBean.setFlag_success();
		return responseBean;
	}


    /**
     * 删除版本
     *
     * @param ids
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping(value="deleteAppVersion",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除")
	public @ResponseBody  ResponseBean deleteappVersion(@RequestParam(value = "ids[]")  String[] ids
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		if(ids == null || ids.length < 1){
			responseBean.setFlag_fail();
			return responseBean;
		}
		List<String> appVersionIdList= new ArrayList<String>();
		for(String appVersionId : ids){
			appVersionIdList.add(appVersionId);
		}
		StringBuilder logDesc = new StringBuilder();
		List<AppVersion> appVersionList = appVersionService.findByIds(appVersionIdList);
		if(appVersionList!=null && appVersionList.size()>0){
            for (AppVersion appVersion :appVersionList){
                logDesc.append(appVersion.getFileName());
            }
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除"+appVersionIdList);
		}
		appVersionService.deleteByIds(ids);
		responseBean.setFlag_success();
		return responseBean;
	}

    /**
     * 上传升级文件 , 需要把这个url在 MyCsrfMatcher里设置为不拦截
     *
     * @param request
     * @param appFile
     * @return
     */
    @PostMapping("/import")
    @ResponseBody
    @SystemControllerLog(description = "版本导入")
    public ResponseBean importFile(HttpServletRequest request,@RequestParam MultipartFile appFile) {
        ResponseBean responseBean = new ResponseBean(ResponseBean.FAIL);

        //先判断文件夹路径是否存在，没有则新建文件夹
        File webFileTemp = new File(updatePath);
        if (!webFileTemp.exists()) {
            webFileTemp.mkdirs();
        }

        String fileName = appFile.getOriginalFilename();
        String filePath = updatePath + File.separator + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }


        try {
            if (FtpUtils.uploadFile(CommonProperties.FTP_USER_NAME,CommonProperties.FTP_PASSWD,CommonProperties.FTP_IP,
                    CommonProperties.FTP_PORT,appFile.getOriginalFilename(),appFile.getInputStream(),CommonProperties.FTP_PATH)){
                //返回文件路径和大小
                Map<String ,Object> ret = new HashMap<>();
                ret.put("fileSize",appFile.getSize());
                ret.put("path",webFileTemp);
                ret.put("fileName",fileName);

                request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "uploadFile success name = "+fileName);
                responseBean.setFlag_success();
                responseBean.setRelateData(ret);
            }
        } catch (IOException e) {
            log.error("copy updateFile fail,{}",e.getMessage());
            responseBean.setMessage("copy updateFile fail");
            return responseBean;
        }


        return responseBean;
    }



}
