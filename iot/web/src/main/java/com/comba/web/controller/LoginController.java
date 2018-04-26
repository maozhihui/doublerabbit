package com.comba.web.controller;

import com.comba.license.LicenseService;
import com.comba.license.LicenseStatus;
import com.comba.server.common.data.User;
import com.comba.server.dao.user.UserService;
import com.comba.server.extensions.core.plugin.sms.SmsPlatformSender;
import com.comba.web.response.ResponseBean;
import com.comba.web.utils.GenerateCodeUtils;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import static com.comba.server.common.data.web.utils.Constants.*;
import static com.comba.web.response.ResponseBean.FAIL;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    private SmsPlatformSender smsSender;

    @Autowired
    private UserService userJpaService;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Value("${phone.codeLength}")
    private  int length;

    @Value("${iotstp.version}")
    private  String version;

	/**
	 * Go login.jsp
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String login(Model model) throws Exception {
	    //先更新缓存中的license文件
        LicenseService.getInstance().parseLicense();
        //获取license解析结果
        LicenseStatus status = LicenseService.getInstance().getLicenseStatus();
		if(!LicenseStatus.VALIDITY_LICENSE.equals(status)){
            model.addAttribute("status",status);
		    return "invalidLicense";
        }

        return "login";
	}
	
	/**
	 * Exit
	 * @return
	 */
	@RequestMapping("logout")
	public String logout() {
		return "redirect:/index";
	}

	
    /**
     * 校证验证码
     * @param session
     * @param validateCode
     * @return
     */
    @RequestMapping("auth/validateCode")
    @ResponseBody
    public int validateCode(HttpSession session,String validateCode){ 
    	String validate_code_rand = (String)session.getAttribute("validate_code_rand");
    	if(validateCode!=null && validate_code_rand!=null && validateCode.equalsIgnoreCase(validate_code_rand)){
    		return 1;
    	}
    	return 0;
    }

	/**
	 * 找回密码
	 *
	 * @return
	 */
	@GetMapping("/findPassword")
    public String findPassword() throws Exception{
		return "find_password";
	}
	
	public Color getRandColor(int fc, int bc) { //给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 发送短信验证码
     *
     * @param msisdn 手机号码
     * @param session
     * @return
     */
    @PostMapping("/sendCode")
    @ResponseBody
    public ResponseBean sendPhoneCode(String msisdn,HttpSession session) {
        //生成随机验证码
        String code = GenerateCodeUtils.generateCode(length);
        log.info("generate code success，{}",code);

        //调用发送短信验证码接口
        ResponseBean responseBean = new ResponseBean();
        StringBuffer content = new StringBuffer();
        content.append(SMS_HEADER);
        content.append(code);
        content.append(SMS_FOOT);

        if(!smsSender.send(msisdn,code)){
            responseBean.setFlag_fail();
            //return responseBean;
        }

        //成功后保存到session
        session.setAttribute(VALISATE_CODE,code);
        session.setAttribute(VALIDATE_TIME,System.currentTimeMillis());

        responseBean.setFlag_success();
        return responseBean;
    }

    /**
     * 重置密码
     *
     * @param msisdn 手机号码
     * @param password 密码
     * @param msCaption 验证码
     * @param session
     * @return
     */
    @PostMapping("/resetPassword")
    @ResponseBody
    public ResponseBean ResetPassword(@RequestParam String msisdn,
                                      @RequestParam String password,
                                      @RequestParam String msCaption,
                                      HttpSession session){
        ResponseBean response = new ResponseBean(FAIL);

        if(session.getAttribute(VALISATE_CODE) != null && session.getAttribute(VALISATE_CODE).toString().equals(msCaption)){
            User user = userJpaService.findByMsisdn(msisdn);
            if (user != null){
                user.setPwd(password);
                userJpaService.save(user);
                response.setFlag_success();
            }else{
                response.setMessage("用户不存在");
            }
        }else{
            response.setMessage("验证码不正确");
        }

        return response;
    }


    @GetMapping("/images/verifyImg")
    public void getRandomCode(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception{
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            httpServletRequest.getSession().setAttribute("validate_code_rand", createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    @ResponseBody
    @GetMapping("/version")
    public String getVersion(){
        return version;
    }
}
