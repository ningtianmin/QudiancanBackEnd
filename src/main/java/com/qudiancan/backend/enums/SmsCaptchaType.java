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
    REGISTER("REGISTER", "注册时发送");

    private String key;
    private String value;

    SmsCaptchaType(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
