package com.qudiancan.backend.service;

import com.aliyuncs.exceptions.ClientException;

/**
 * @author NINGTIANMIN
 */
public interface SmsService {
    /**
     * 发送验证码至手机
     *
     * @param phone   手机号
     * @param captcha 验证码
     * @throws ClientException 发送失败
     */
    void sendCaptcha(String phone, String captcha) throws ClientException;
}
