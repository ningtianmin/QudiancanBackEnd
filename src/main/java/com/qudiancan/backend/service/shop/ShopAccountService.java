package com.qudiancan.backend.service.shop;

import com.qudiancan.backend.enums.SmsCaptchaType;
import com.qudiancan.backend.pojo.dto.shop.ShopAccountDTO;
import com.qudiancan.backend.pojo.dto.shop.ShopAccountTokenDTO;
import com.qudiancan.backend.pojo.vo.shop.LoginVO;
import com.qudiancan.backend.pojo.vo.shop.RegisterVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author NINGTIANMIN
 */
public interface ShopAccountService {
    /**
     * 发送手机验证码
     *
     * @param phone          手机号
     * @param smsCaptchaType 验证码类型
     */
    void sendSmsCaptcha(String phone, SmsCaptchaType smsCaptchaType);

    /**
     * 验证手机验证码
     *
     * @param phone   手机号
     * @param captcha 验证码
     * @return 验证结果
     */
    boolean verifySmsCaptcha(String phone, String captcha);

    /**
     * 账户注册
     *
     * @param registerVO 注册信息
     * @return 注册的账户
     */
    ShopAccountDTO register(RegisterVO registerVO);

    /**
     * 账户登录
     *
     * @param loginVO  登录信息
     * @param response servletResponse
     * @return 账户token
     */
    ShopAccountTokenDTO login(LoginVO loginVO, HttpServletResponse response);

    /**
     * 账户登出
     *
     * @param request  servletRequest
     * @param response servletResponse
     */
    void logout(HttpServletRequest request, HttpServletResponse response);
}
