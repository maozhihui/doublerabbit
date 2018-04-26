package com.comba.server.dao.device;

import com.comba.server.common.data.device.AttributeData;
import com.comba.server.dao.AbstractDaoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * HistoryDataService Test
 *
 * @author maozhihui
 * @create 2017-10-13 11:05
 **/
public class HistoryDataServiceTest extends AbstractDaoTest {

    @Autowired
    private HistoryDataService dataService;

    @Test
    public void testGetLatestDeviceData(){
        String devId = "ff8080815f05ca0f015f05d1bc600002";
        List<AttributeData> res = dataService.getLatestDeviceData(devId);
        System.out.println(res);
        assertTrue(res.size() > 0);
    }
}
