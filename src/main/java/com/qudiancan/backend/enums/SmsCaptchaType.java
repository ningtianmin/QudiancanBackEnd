package com.qudiancan.backend.enums;

import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum SmsCaptchaType {

    /**
     * 注册时发送
     */
    REGISTER("REGISTER", "注册时发送"),
    /**
     * 重置密码时发送
     */
    RESET_PASSWORD("RESET_PASSWORD", "重置密码"),
    /**
     * 用于创建账户
     */
    CREATE_ACCOUNT("CREATE_ACCOUNT", "创建账户");

    private String key;
    private String value;

    SmsCaptchaType(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
