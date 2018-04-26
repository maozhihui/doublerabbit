package com.comba.web.controller.device;

import com.comba.server.common.data.Device;
import com.comba.server.common.data.device.Product;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.dao.device.DeviceService;
import com.comba.server.dao.device.ProductService;
import com.comba.web.common.export.ExportUtils;
import com.comba.web.response.ResponseBean;
import com.comba.web.security.SpringSecurityUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpringSecurityUtils.class,ExportUtils.class})
public class DeviceControllerTest {

    @InjectMocks
    private DeviceController deviceController;

    @Mock
    private DeviceService deviceService;

    @Mock
    private ProductService productService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        PowerMockito.mockStatic(SpringSecurityUtils.class);
        PowerMockito.mockStatic(ExportUtils.class);
    }

    @Test
    public void authorise() throws Exception {
    }

    @Test
    public void authoriseUser() throws Exception {
    }

    @Test
    public void list() throws Exception {
    }

    @Test
    public void getAuditDataByPage() throws Exception {
    }

    @Test
    public void datasByExport() throws Exception {
    }

    @Test
    public void deviceAdd() throws Exception {
        String devId = "1";
        Device device = newDevice();
        String productId = "1";
        HttpSession session = new MockHttpSession();
        session.setAttribute(Constants.CUR_PRODUCT_ID,productId);
        when(deviceService.getOne(devId)).thenReturn(device);

        String ret = deviceController.deviceAdd(devId,new BindingAwareModelMap(),session);
        Assert.assertEquals("device/device/device_edit",ret);
    }

    @Test
    public void deviceAdd2() throws Exception {
        String productId = "1";
        HttpSession session = new MockHttpSession();
        session.setAttribute(Constants.CUR_PRODUCT_ID,productId);


        when(productService.getOne(productId)).thenReturn(new Product());

        String ret = deviceController.deviceAdd("",new BindingAwareModelMap(),session);
        Assert.assertEquals("device/device/device_edit",ret);
    }

    @Test
    public void deviceCheck() throws Exception {
    }

    @Test
    public void addDevice() throws Exception {
        Device device = newDevice();

        when(SpringSecurityUtils.getCurrentUserTenantId()).thenReturn("1");

/*        ResponseBean responseBean = deviceController.addDevice(device,new MockHttpServletRequest());
        Mockito.verify(deviceService).saveDeviceAndAttributes(device);
        Assert.assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());*/
    }

    @Test
    public void updateDevice() throws Exception {
        Device device = newDevice();

        when(deviceService.getOne(device.getDevId())).thenReturn(device);

        ResponseBean responseBean = deviceController.updateDevice(device,new MockHttpServletRequest());
        Mockito.verify(deviceService).save(device);
        Assert.assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    @Test
    public void deleteGdevice() throws Exception {
        String[] ids = "1".split(",");
        List<String> idList = Lists.newArrayList(ids);

        List<Device> list = Lists.newArrayList();
        list.add(newDevice());
        when(deviceService.findByIds(idList)).thenReturn(list);

        ResponseBean responseBean = deviceController.deleteGdevice(ids,new MockHttpServletRequest());
        Mockito.verify(deviceService).deleteByIds(idList);
        Assert.assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    @Test
    public void deviceStatisByDay() throws Exception {
    }

    @Test
    public void deviceStatisByIsGateWay() throws Exception {
    }

    @Test
    public void deviceStatisByIsGateWayOfProduct() throws Exception {
    }

    @Test
    public void queryByProductId() throws Exception {
    }

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void importDeviceExcel() throws Exception {
        MultipartFile file = new MockMultipartFile("test","content:123".getBytes());
        List<HSSFRow> hssfRowList = Lists.newArrayList();
        String productId = "1";
        HttpSession session = new MockHttpSession();
        session.setAttribute(Constants.CUR_PRODUCT_ID,productId);

        when(ExportUtils.readExcel(file)).thenReturn(hssfRowList);

        ResponseBean responseBean = deviceController.importDeviceExcel(new MockHttpServletRequest(),file,session);
        Mockito.verify(deviceService).saveDeviceList(hssfRowList,productId,null);
        Assert.assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    @Test
    public void importDeviceExcelFail() throws Exception {
        MultipartFile file = new MockMultipartFile("test","content:123".getBytes());
        String productId = "1";
        HttpSession session = new MockHttpSession();
        session.setAttribute(Constants.CUR_PRODUCT_ID,productId);

        when(ExportUtils.readExcel(file)).thenThrow(IOException.class);


        ResponseBean responseBean = deviceController.importDeviceExcel(new MockHttpServletRequest(),file,session);
        Assert.assertEquals(ResponseBean.FAIL,responseBean.getFlag());
        Assert.assertEquals("导入设备失败，文件出错",responseBean.getMessage());
    }

    @Test
    public void importDeviceExcelFail2() throws Exception {
        MultipartFile file = new MockMultipartFile("test","content:123".getBytes());
        List<HSSFRow> hssfRowList = Lists.newArrayList();
        String productId = "1";
        HttpSession session = new MockHttpSession();
        session.setAttribute(Constants.CUR_PRODUCT_ID,productId);

        when(ExportUtils.readExcel(file)).thenReturn(hssfRowList);
        doThrow(new Exception("导入设备失败，保存记录失败")).when(deviceService).saveDeviceList(hssfRowList,productId,null);


        ResponseBean responseBean = deviceController.importDeviceExcel(new MockHttpServletRequest(),file,session);
        Assert.assertEquals(ResponseBean.FAIL,responseBean.getFlag());
        Assert.assertEquals("导入设备失败，保存记录失败",responseBean.getMessage());
    }

    @Test
    public void validateDeviceName() throws Exception {
        Device device = newDevice();
        String name = "test1";

        when(SpringSecurityUtils.getCurrentUserTenantId()).thenReturn(device.getTenantId());
        when(deviceService.getOne(device.getDevId())).thenReturn(device);
        when(deviceService.countByTenantIdAndName(device.getTenantId(),name)).thenReturn(0);

        ResponseBean responseBean = deviceController.validateDeviceName(device.getDevId(),name);
        Assert.assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    @Test
    public void validateDeviceName2() throws Exception {
        Device device = newDevice();
        String name = "test1";

        when(SpringSecurityUtils.getCurrentUserTenantId()).thenReturn(device.getTenantId());
        when(deviceService.countByTenantIdAndName(device.getTenantId(),name)).thenReturn(0);

        ResponseBean responseBean = deviceController.validateDeviceName("",name);
        Assert.assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    @Test
    public void validateDeviceHardIdentity() throws Exception {
        Device device = newDevice();
        String identity = "11";

        when(deviceService.getOne(device.getDevId())).thenReturn(device);
        when(deviceService.countByHardIdentity(device.getHardIdentity())).thenReturn(0);

        ResponseBean responseBean = deviceController.validateDeviceHardIdentity(device.getDevId(),identity);
        Assert.assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    @Test
    public void validateDeviceHardIdentity2() throws Exception {
        Device device = newDevice();
        String identity = "11";

        when(deviceService.countByHardIdentity(device.getHardIdentity())).thenReturn(0);

        ResponseBean responseBean = deviceController.validateDeviceHardIdentity("",identity);
        Assert.assertEquals(ResponseBean.SUCCESS,responseBean.getFlag());
    }

    @Test
    public void exportDeviceModelExcel() throws Exception {
        HttpSession session = new org.springframework.mock.web.MockHttpSession();
        Map<String,Object> parameter = new HashMap<>();

        deviceController.exportDeviceModelExcel(session,parameter);

    }

    private Device newDevice(){
        Device device = new Device();
        device.setDevId("1");
        device.setProductId("1");
        device.setHardIdentity("1");
        device.setCategoryId("1");
        device.setTenantId("1");
        device.setName("test");
        return device;
    }


}