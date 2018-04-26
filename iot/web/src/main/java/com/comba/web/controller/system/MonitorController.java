package com.comba.web.controller.system;


import com.comba.server.common.data.web.utils.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("monitor")
public class MonitorController {
	
	@RequestMapping(value="index")
	public String login() throws Exception {
		
		return "monitor/monitorIndex";
	}
	
	@RequestMapping(value="analysis")
	public String analysis(Model model,HttpSession session) throws Exception {
        model.addAttribute(Constants.CUR_PRODUCT_ID, session.getAttribute(Constants.CUR_PRODUCT_ID));
        return "monitor/analysis";
	}
}
