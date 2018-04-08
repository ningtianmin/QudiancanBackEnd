package com.qudiancan.backend.enums.shop;

import com.qudiancan.backend.enums.StringPair;
import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum OrderPayStatus implements StringPair {

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
