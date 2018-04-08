package com.qudiancan.backend.enums.shop;

import com.qudiancan.backend.enums.StringPair;
import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum ShopStatus implements StringPair {

    /**
     * 待完善
     */
    REMAIN_PERFECT("REMAIN_PERFECT", "待完善"),
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
