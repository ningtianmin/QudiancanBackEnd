package com.qudiancan.backend.enums.shop;

import com.qudiancan.backend.enums.StringPair;
import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum ShopBranchProductStatus implements StringPair {

    /**
     * 正常
     */
    NORMAL("NORMAL", "正常");

    private String key;
    private String value;

    ShopBranchProductStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
