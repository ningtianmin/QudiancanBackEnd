package com.qudiancan.backend.enums.shop;

import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum OrderPayStatus {

    /**
     * 未支付
     */
    UNPAID("UNPAID", "未支付"),
    /**
     * 已支付
     */
    PAID("PAID", "已支付");

    private String key;
    private String value;

    OrderPayStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
