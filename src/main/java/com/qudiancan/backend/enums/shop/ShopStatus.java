package com.qudiancan.backend.enums.shop;

import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum ShopStatus {

    /**
     * 新建的，门店待创建
     */
    NEW("NEW", "新建的"),
    /**
     * 正常
     */
    NORMAL("NORMAL", "正常");

    private String key;
    private String value;

    ShopStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
