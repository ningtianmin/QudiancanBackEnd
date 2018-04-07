package com.qudiancan.backend.enums.shop;

import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum OrderProductStatus {

    /**
     * 正常
     */
    NORMAL("NORMAL", "正常");

    private String key;
    private String value;

    OrderProductStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
