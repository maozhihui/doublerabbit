package com.comba.web.controller.device;

import com.comba.server.common.data.device.HistoryData;
import com.comba.server.common.data.device.TelemetryAttributes;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.device.HistoryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by sujinxian on 2017/7/14.
 */
@Controller
@RequestMapping("/historyData")
public class HistoryDataController {

    @Autowired
    private HistoryDataService historyDataService;



    @ResponseBody
    @RequestMapping("/datasByPage")
    public Page getAuditDataByPage(HistoryData historyData, Page page)throws Exception{
        page = page==null?new Page():page;
        List<String> orderBysList = page.getOrderBys();
        return historyDataService.pagedQuery(page.getPageNo(), page.getPageSize(), historyData,orderBysList);
    }


}
