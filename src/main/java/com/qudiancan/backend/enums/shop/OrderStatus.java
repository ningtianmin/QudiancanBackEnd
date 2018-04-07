package com.qudiancan.backend.enums.shop;

import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum OrderStatus {

    /**
     * 新下单
     */
    NEW("NEW", "新下单"),
    /**
     * 已取消
     */
    CANCELED("CANCELED", "已取消"),
    /**
     * 已完结
     */
    FINISHED("FINISHED", "已完结");

    private String key;
    private String value;

    OrderStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
