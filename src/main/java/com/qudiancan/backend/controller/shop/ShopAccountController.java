package com.qudiancan.backend.controller.shop;

import com.qudiancan.backend.enums.SmsCaptchaType;
import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.dto.shop.ShopAccountDTO;
import com.qudiancan.backend.pojo.dto.shop.ShopAccountTokenDTO;
import com.qudiancan.backend.pojo.vo.shop.LoginVO;
import com.qudiancan.backend.pojo.vo.shop.RegisterVO;
import com.qudiancan.backend.service.shop.ShopAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author NINGTIANMIN
 */

@RestController
@RequestMapping("/shops")
@Slf4j
public class ShopAccountController {

    @Autowired
    private ShopAccountService shopAccountService;

    /**
     * 发送账户注册验证码
     *
     * @param phone 手机号
     * @return 发送状态
     */
    @PostMapping("/register/send_sms_captcha")
    public Response sendSmsCaptcha(String phone) {
        shopAccountService.sendSmsCaptcha(phone, SmsCaptchaType.REGISTER);
        return Response.success();
    }

    /**
     * 发送重置密码手机验证码
     *
     * @param phone 手机号
     * @return 发送状态
     */
    @PostMapping("/resetPassword/send_sms_captcha")
    public Response sendResetPasswordSmsCaptcha(@RequestParam String phone) {
        shopAccountService.sendSmsCaptcha(phone, SmsCaptchaType.RESET_PASSWORD);
        return Response.success();
    }

    /**
     * 发送创建账户手机验证码
     *
     * @param phone 手机号
     * @return 发送状态
     */
    @PostMapping("/createAccount/send_sms_captcha")
    public Response sendCreateAccountSmsCaptcha(@RequestParam String phone) {
        shopAccountService.sendSmsCaptcha(phone, SmsCaptchaType.CREATE_ACCOUNT);
        return Response.success();
    }

    /**
     * 账户注册
     *
     * @param registerVO 注册信息
     * @return 注册的账户
     */
    @PostMapping("/register")
    public Response<ShopAccountDTO> register(RegisterVO registerVO) {
        return Response.success(shopAccountService.register(registerVO));
    }

    /**
     * 重置密码
     *
     * @return 请求状态
     */
    @PostMapping("/resetPassword")
    public Response resetPassword(@RequestParam String phone, @RequestParam String phoneCaptcha, @RequestParam String newPassword) {
        shopAccountService.resetPassword(phone, phoneCaptcha, newPassword);
        return Response.success();
    }

    /**
     * 账户登录
     *
     * @param loginVO 登录信息
     * @return 账户token
     */
    @PostMapping("/login")
    public Response<ShopAccountTokenDTO> login(LoginVO loginVO, HttpServletResponse response) {
        return Response.success(shopAccountService.login(loginVO, response));
    }

}
