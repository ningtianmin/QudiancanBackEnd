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
    OK(0, "OK"),
    /**
     * 请求失败
     */
    FAILURE(-1, "FAILURE"),
    /**
     * 餐厅待完善
     */
    SHOP_REMAIN_PERFECT(1000, "餐厅待完善"),
    /**
     * 服务器内部错误
     */
    SERVER_INTERNAL_ERROR(1001, "服务器内部错误"),
    /**
     * 权限不足
     */
    AUTHORITY_NOT_ENOUGH(1002, "权限不足"),
    /**
     * 请检查您的请求
     */
    BAD_REQUEST(1003, "请检查您的请求"),
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
     * 该餐厅ID被占用
     */
    SHOP_ID_OCCUPIED(2009, "该餐厅ID被占用"),
    /**
     * 登录失败
     */
    SHOP_LOGIN_FAILURE(2010, "登录失败"),
    /**
     * 请传入sessionId
     */
    SHOP_NO_SESSION_ID(2011, "请传入sessionId"),
    /**
     * sessionId无效
     */
    SHOP_INVALID_SESSION_ID(2012, "sessionId无效"),
    /**
     * 参数不完整
     */
    SHOP_INCOMPLETE_PARAM(2013, "参数不完整"),
    /**
     * 未知账户
     */
    SHOP_UNKNOWN_ACCOUNT(2014, "未知账户"),
    /**
     * 参数不正确
     */
    SHOP_PARAM_WRONG(2015, "参数不正确"),
    /**
     * 参数不正确
     */
    WECHAT_WRONG_PARAM(2016, "参数不正确"),
    /**
     * 请求错误
     */
    WECHAT_BAD_REQUEST(2017, "请求错误"),
    /**
     * 参数不完整
     */
    PARAM_INCOMPLETE(2018, "参数不完整"),
    /**
     * 参数无效
     */
    PARAM_INVALID(2019, "参数无效"),
    /**
     * 门店状态不正常
     */
    BRANCH_STATUS_UNUSUAL(2020, "门店状态不正常"),
    /**
     * 门店桌位状态不正常
     */
    BRANCH_TABLE_STATUS_UNUSUAL(2021, "门店桌位状态不正常"),
    /**
     * 订单不存在
     */
    ORDER_NOT_EXIST(2022, "订单不存在"),
    /**
     * 订单产品不存在
     */
    ORDER_PRODUCT_NOT_EXIST(2023, "订单产品不存在"),
    /**
     * 订单状态不正常
     */
    ORDER_STATUS_UNUSUAL(2024, "订单状态不正常"),
    /**
     * 购物车不存在
     */
    WECHAT_CART_NOT_EXIST(2025, "购物车不存在"),
    /**
     * 会员不存在
     */
    WECHAT_MEMBER_NOT_EXIST(2026, "会员不存在"),
    /**
     * 手机号被占用
     */
    PHONE_OCCUPIED(2026, "手机号被占用");

    private Integer code;
    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
