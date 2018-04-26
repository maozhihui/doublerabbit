package com.comba.web.controller.api;

import com.comba.server.common.data.device.HistoryData;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.device.HistoryDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import static com.comba.server.dao.util.DateConvertUtil.convertToLocalDateTime;

/**
 *  开放接口controller
 */
@RestController
@RequestMapping("/api")
@Api("Open API")
public class OpenApiController {

    @Autowired
    private HistoryDataService historyDataService;

    @Value("${api.dataLimit}")
    private Integer apiDataLimit;

    /**
     * 开放接口查询设备数据
     *
     * @param did 设备ID
     * @param start_ts 开始查询时间
     * @param end_ts 结束时间
     * @param limit 查询最大个数
     * @return
     */
    @ApiOperation("Get Device Attribute History Data")
    @ApiImplicitParams({
    		@ApiImplicitParam(paramType = "path",name = "did",dataType = "String",required = true,value = "Device ID"),
    		@ApiImplicitParam(paramType = "query",name = "start_ts",dataType ="Long" ,required = true,value = "start time(Millisecond):"),
    		@ApiImplicitParam(paramType = "query",name = "end_ts",dataType = "Long",required = true,value = "end time(Millisecond):"),
    		@ApiImplicitParam(paramType = "query",name = "limit",dataType = "int",value = "max records return:")
    })
    @ApiResponses({
    	@ApiResponse(code = 400,message = "client param is invalid")
    })
    @GetMapping("/device/{did}/data")
    public Map<String,Object> getDeviceData(@PathVariable String did,
                                            @RequestParam Long start_ts,
                                            @RequestParam Long end_ts,
                                            @RequestParam(required = false,name = "limit") Integer limit){
        if (limit == null){
            limit = apiDataLimit;
        }
        HistoryData historyData = new HistoryData();
        Date startDate = new Date(start_ts);
        Date endDate = new Date(end_ts);
        historyData.setStartDate(convertToLocalDateTime(startDate));
        historyData.setEndDate(convertToLocalDateTime(endDate));
        historyData.setDevId(did);

        Page page = new Page();
        page.setPageSize(limit);

        return historyDataService.getDeviceData(historyData,page);
    }
}
