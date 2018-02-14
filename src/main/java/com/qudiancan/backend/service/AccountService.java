package com.qudiancan.backend.service;

import com.qudiancan.backend.enums.SmsCaptchaType;
import com.qudiancan.backend.pojo.dto.AccountDTO;
import com.qudiancan.backend.pojo.vo.RegisterVO;

/**
 * @author NINGTIANMIN
 */
public interface AccountService {
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
     * @return 验证通过返回true
     */
    boolean verifySmsCaptcha(String phone, String captcha);

    /**
     * 店铺后台账户注册
     *
     * @param registerVO 用于接收注册所需字段值
     * @return 账户登录ID
     * @apiNote registerVO:
     * shopId 正则校验
     * password 正则校验
     * name 不填默认为手机号
     * email 正则校验
     * phone
     * phoneCaptcha
     */
    AccountDTO register(RegisterVO registerVO);
}
