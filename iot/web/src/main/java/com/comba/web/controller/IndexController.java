package com.comba.web.controller;

import com.comba.server.common.data.User;
import com.comba.server.dao.device.DeviceService;
import com.comba.server.dao.rule.RuleJpaService;
import com.comba.server.dao.user.UserService;
import com.comba.web.security.SpringSecurityUtils;
import com.comba.web.spring.SessionCounter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Controller
public class IndexController {
	@Resource
	private RuleJpaService ruleService;
	@Resource
	private UserService userJpaService;
	@Resource
	private DeviceService deviceService;

	/**
	 * 普通用户首页
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model) {
		model.addAttribute("_displayName",SpringSecurityUtils.getCurrentUserDisplayName());
		if(SpringSecurityUtils.isSupperAdmin()){
			return "redirect:index_admin";
		}else if(SpringSecurityUtils.isTenantAdmin()){
			return "redirect:index_tenant";
		} else if (SpringSecurityUtils.isCommonUser()){
			return  "redirect:index_normal";
		}else{
			return "login";
		}

	}
	
	/**
	 * 租户管理员首页
	 * @return
	 */
	@RequestMapping("index_tenant")
	public String index_common(Model model) {
        if(SpringSecurityUtils.isTenantAdmin()){
            model.addAttribute("_displayName",SpringSecurityUtils.getCurrentUserDisplayName());
            return "index_tenant";
        }
		return "login";
	}
	/**
	 * 普通用户首页
	 * @return
	 */
	@RequestMapping("index_normal")
	public String index_normal(Model model) {
        if(!SpringSecurityUtils.isCommonUser()){
            return "login";
        }

		model.addAttribute("_displayName",SpringSecurityUtils.getCurrentUserDisplayName());
		return "index_normal";
	}
	/**
	 * 管理员首页
	 * @return
	 */
	@RequestMapping("index_admin")
	public String index_admin(Model model) {
        if(!SpringSecurityUtils.isSupperAdmin()){
            return "login";
        }

		model.addAttribute("_displayName",SpringSecurityUtils.getCurrentUserDisplayName());
		return "index_admin";
	}
	
	/**
	 * 管理员首页内容
	 * @return
	 */
	@RequestMapping("mainContent_admin")
	public String mainContent_admin() {
		return "admin/mainContent";
	}
	
	/**
	 * 管理员首页统计
	 * 
	 * @author wengzhonghui 2017年7月3日
	 * @return devNum_notGateWay:非网关设备的数量，devNum_gateWay：网关设备数量 ,userNum:用户数统计,ruleNum:规则数统计
	 * @throws Exception
	 */
	@RequestMapping(value="homeStatis_admin")
	@ResponseBody
	public Map<String,Object> homeStatis_admin() throws Exception{
		//设备数量统计
		Map<String,Object> statisDatas = deviceService.deviceStatisByIsGateWay();
		statisDatas = statisDatas==null?new HashMap<String,Object>():statisDatas;
		//用户数统计
		Long userNum = userJpaService.countByStateFlag(User.STATE_FLAG_ACTIVE);
		//规则数统计
		Long ruleNum = ruleService.countByTenantId(SpringSecurityUtils.getCurrentUserTenantId());
		statisDatas.put("userNum", userNum);
		statisDatas.put("ruleNum", ruleNum);
		return statisDatas;
	}
	
	
	/**
	 * unauthor
	 * @return
	 */
	@RequestMapping("unauthor")
	public String unauthor() {
		return "unauthor.jsp";
	}
	
	/**
	 * reports
	 * @return
	 */
	@RequestMapping("reports")
	public String reports() {
		return "reports.jsp";
	}

	@GetMapping("/activeSession")
	@ResponseBody
	public Integer getActiveSession(){
		return SessionCounter.getActiveSession();
	}
}
