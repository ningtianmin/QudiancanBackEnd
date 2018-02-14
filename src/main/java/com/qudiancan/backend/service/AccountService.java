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
     * shopId 店铺id
     * password 账户密码
     * name 账户名,默认为手机号
     * email 账户邮箱
     * phone 账户手机号
     * phoneCaptcha 短信验证码
     */
    AccountDTO register(RegisterVO registerVO);
}
