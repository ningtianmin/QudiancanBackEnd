package com.qudiancan.backend.enums.shop;

import com.qudiancan.backend.enums.StringPair;
import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum ShopBranchStatus implements StringPair {

    /**
     * 正常
     */
    NORMAL("NORMAL", "正常"),
    /**
     * 休息中
     */
    CLOSED("CLOSED", "休息中");

    private String key;
    private String value;

    ShopBranchStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
