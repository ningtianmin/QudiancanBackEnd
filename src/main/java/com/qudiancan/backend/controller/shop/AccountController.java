package com.qudiancan.backend.controller.shop;

import com.qudiancan.backend.enums.SmsCaptchaType;
import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.dto.AccountDTO;
import com.qudiancan.backend.pojo.dto.AccountTokenDTO;
import com.qudiancan.backend.pojo.vo.LoginVO;
import com.qudiancan.backend.pojo.vo.RegisterVO;
import com.qudiancan.backend.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NINGTIANMIN
 */

@RestController
@RequestMapping("/shop")
@Slf4j
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
     * 发送账户注册验证码
     *
     * @param phone 手机号
     * @return 发送状态
     */
    @PostMapping("/register/send_sms_captcha")
    public Response sendSmsCaptcha(String phone) {
        accountService.sendSmsCaptcha(phone, SmsCaptchaType.REGISTER);
        return Response.success();
    }

    /**
     * 账户注册
     *
     * @param registerVO 注册信息
     * @return 注册的账户
     */
    @PostMapping("/register")
    public Response<AccountDTO> register(RegisterVO registerVO) {
        return Response.success(accountService.register(registerVO));
    }

    /**
     * 账户登录
     *
     * @param loginVO 登录信息
     * @return 账户token
     */
    @PostMapping("/login")
    public Response<AccountTokenDTO> login(LoginVO loginVO) {
        return Response.success(accountService.login(loginVO));
    }
}
