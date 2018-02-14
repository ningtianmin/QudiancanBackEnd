package com.qudiancan.backend.common;

/**
 * @author NINGTIANMIN
 */
public interface Constant {
    Integer SMS_CAPTCHA_LENGTH = 6;
    String REDIS_SMS_CAPTCHA_KEY_PREFIX = "sms_captcha_token_";
    Integer REDIS_SMS_CAPTCHA_EXPIRY = 5;
}
