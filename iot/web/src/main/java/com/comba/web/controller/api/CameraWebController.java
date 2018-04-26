package com.comba.web.controller.api;


import com.comba.server.common.data.Device;
import com.comba.server.common.data.device.DeviceCamera;
import com.comba.server.dao.device.DeviceService;
import com.comba.web.security.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/v1/camera")
public class CameraWebController {

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private DeviceService deviceService;

    @GetMapping("{did}/view")
    public String view(@PathVariable(value = "did") String did,
                                String token,
                                Model model){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return "login";
        }


        List<Device> devices = deviceService.findByIds(Arrays.asList(did));
        if (devices.isEmpty()){
            log.error("device not found.did {}",did);
            return "login";
        }

        DeviceCamera camera = devices.get(0).getDeviceCamera();
        if (camera == null){
            log.error("camera not found.did {}",did);
            return "login";
        }

        model.addAttribute("ip",camera.getServerIp());
        model.addAttribute("port",camera.getPort());
        model.addAttribute("username",camera.getUserName());
        model.addAttribute("pwd",camera.getPwd());
        model.addAttribute("proxyIp",camera.getProxyIp());
        return "/device/camera/camera_lite";
    }
}
