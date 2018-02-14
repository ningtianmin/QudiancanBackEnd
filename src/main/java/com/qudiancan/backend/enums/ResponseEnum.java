package com.qudiancan.backend.enums;

import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum ResponseEnum {
    /**
     * 请求成功
     */
    OK(1000, "OK"),
    /**
     * 该手机号不合法
     */
    SHOP_PHONE_ILLEGAL(2000, "该手机号不合法"),
    /**
     * 该手机号码被占用
     */
    SHOP_PHONE_OCCUPIED(2001, "该手机号码被占用"),
    /**
     * 短信验证码发送失败
     */
    SHOP_SMS_CAPTCHA_FAILURE(2003, "短信验证码发送失败"),
    /**
     * 手机号验证失败
     */
    SHOP_PHONE_VERIFY_FAILURE(2004, "手机号验证失败"),
    /**
     * 请传入参数
     */
    SHOP_NO_PARAM(2005, "请传入参数"),
    /**
     * ID格式有误
     */
    SHOP_WRONG_ID_PATTERN(2006, "ID格式有误"),
    /**
     * 密码格式有误
     */
    SHOP_WRONG_PASSWORD_PATTERN(2007, "密码格式有误"),
    /**
     * 邮箱格式有误
     */
    SHOP_WRONG_EMAIL_PATTERN(2008, "邮箱格式有误"),
    /**
     * 该店铺ID被占用
     */
    SHOP_ID_OCCUPIED(2009, "该店铺ID被占用");

    private Integer code;
    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
