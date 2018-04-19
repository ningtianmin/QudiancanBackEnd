package com.qudiancan.backend.enums.shop;

import com.qudiancan.backend.enums.StringPair;
import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum OrderPayMethod implements StringPair {

    /**
     * 微信支付
     */
    WECHAT("WECHAT", "微信支付"),
    /**
     * 现金支付
     */
    CASH("CASH", "现金支付");

    private String key;
    private String value;

    OrderPayMethod(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
