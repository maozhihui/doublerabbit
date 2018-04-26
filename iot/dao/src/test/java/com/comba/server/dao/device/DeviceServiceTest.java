package com.comba.server.dao.device;

import com.comba.server.common.data.Device;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.dao.AbstractDaoTest;
import com.comba.server.dao.model.device.DeviceEntity;
import com.comba.server.dao.util.DateConvertUtil;
import org.apache.poi.hssf.model.InternalSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DeviceServiceTest extends AbstractDaoTest{

    @Autowired
    private DeviceService deviceService;

    private Device device = newDevice("DeviceServiceTest1");

    @Test
    public void findAll() throws Exception {
        List<Device> list = deviceService.findAll();
        assertTrue(list.size() >= 0);
    }

    @Test
    public void pagedQuery() throws Exception {
        List<String> orderBy = Lists.newArrayList("updateTime");
        Page page = deviceService.pagedQuery(100,1000,device,orderBy);
        assertTrue(page.getResultSize() >= 0);
    }

    @Test
    public void getDataByPage() throws Exception {
        Page page = deviceService.getDataByPage(device,100,1000);
        assertTrue(page.getResultSize() >= 0);
    }

    @Test
    public void deleteByIds() throws Exception {

    }

    @Test
    public void findByIds() throws Exception {
        List<String> ids = Lists.newArrayList("000000005de02363015dea3a0a2d0007");
        List<Device> device = deviceService.findByIds(ids);
        System.out.println(device);

    }

    @Test
    public void deviceStatisByDay() throws Exception {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.plusDays(-1);
        List<Object[]> list = deviceService.deviceStatisByDay(DateConvertUtil.convertToDate(startTime),DateConvertUtil.convertToDate(endTime));
        Assert.assertNotNull(list);
    }

    @Test
    public void deviceStatisByDayOfProduct() throws Exception {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.plusDays(-1);

        List<Object[]> list = deviceService.deviceStatisByDayOfProduct(DateConvertUtil.convertToDate(startTime),DateConvertUtil.convertToDate(endTime),"DeviceServiceTest");
        Assert.assertNotNull(list);
    }

    @Test
    public void deviceStatisByIsGateWayOfProduct() throws Exception {
        Map<String,Object> list = deviceService.deviceStatisByIsGateWayOfProduct("DeviceServiceTest");
        Assert.assertNotNull(list);
    }

    @Test
    public void deviceStatisByIsGateWay() throws Exception {
        Map<String,Object> list = deviceService.deviceStatisByIsGateWay();
        Assert.assertNotNull(list);
    }

    @Test
    public void countByProductId() throws Exception {
        Long count = deviceService.countByProductId("DeviceServiceTest");
        assertTrue(count >= 0);
    }

    @Test
    public void queryDeviceByProductId() throws Exception {
        Page page = deviceService.queryDeviceByProductId("DeviceServiceTest",new Page());
        Assert.assertNotNull(page);
    }

    @Test
    public void saveDeviceAndAttributes() throws Exception {
        deviceService.saveDeviceAndAttributes(device);
    }

    @Test
    public void saveDeviceList() throws Exception {
        List<HSSFRow> list= newHSSFList();
        deviceService.saveDeviceList(list,device.getProductId(),device.getTenantId());
    }

    @Test
    public void deleteByTenantId() throws Exception {
    }

    @Test
    public void countByTenantIdAndName() throws Exception {
        int count = deviceService.countByTenantIdAndName(device.getTenantId(),device.getName());
        assertTrue(count >= 0);
    }

    @Test
    public void countByHardIdentity() throws Exception {
        int count = deviceService.countByHardIdentity(device.getHardIdentity());
        assertTrue(count >= 0);
    }

    @Test
    public void findByProductId() throws Exception {
        List<DeviceEntity> deviceDB = deviceService.findByProductId(device.getProductId());
        Assert.assertNotNull(deviceDB);
    }

    @Test
    public void countByDeviceTemplateId() throws Exception {
        int count = deviceService.countByDeviceTemplateId(Lists.newArrayList(device.getDeviceTemplateId()));
        assertTrue(count >= 0);
    }

    @Test
    public void findDeviceById() throws Exception {
    }

    @Test
    public void findDeviceByHardIdentity() throws Exception {
        deviceService.findDeviceByHardIdentity(device.getHardIdentity());
    }

    @Test
    public void saveDevice() throws Exception {
        deviceService.saveDevice(newDevice("DeviceServiceTest2"));
    }

    @Test
    public void updateStatus() throws Exception {
        Boolean ret = deviceService.updateStatus(device);
        Assert.assertNotNull(ret);
    }

    private Device newDevice(String identity){
        Device device = new Device();
        device.setName("DeviceServiceTest");
        device.setProductId("DeviceServiceTest");
        device.setHardIdentity(identity);
        device.setTenantId("ffffffffffffffffffffffffffffffff");
        device.setDeviceTemplateId("8a8aeb8e5dc5d396015dc5dd5f8c0002");
        device.setIsGateWay(1);
        device.setIsGateWayStr("是");
        device.setCreateTime(new Date());
        device.setDescription("");
        device.setSn("1111");
        device.setId(new DeviceId(UUIDUtils.create()));
        return device;
    }

    private List<HSSFRow> newHSSFList(){
        List<HSSFRow> list = Lists.newArrayList();

        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet();
        HSSFRow row = sheet.createRow(1);

        Cell name= row.createCell(0);
        name.setCellValue(device.getName());

        Cell hardIdentity= row.createCell(1);
        hardIdentity.setCellValue(device.getHardIdentity());

        Cell deviceTemplateId= row.createCell(2);
        deviceTemplateId.setCellValue("网关参数模板");

        Cell sn= row.createCell(3);
        sn.setCellValue(device.getSn());

        Cell isGateWayStr= row.createCell(4);
        isGateWayStr.setCellValue(device.getIsGateWayStr());

        Cell description= row.createCell(5);
        description.setCellValue(device.getDescription());

        list.add(row);
        return list;
    }

}