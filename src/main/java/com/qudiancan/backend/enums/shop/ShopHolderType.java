package com.qudiancan.backend.enums.shop;

import com.qudiancan.backend.enums.StringPair;
import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum ShopHolderType implements StringPair {

    /**
     * 个人
     */
    PERSON("PERSON", "个人"),
    /**
     * 企业
     */
    COMPANY("COMPANY", "企业");

    private String key;
    private String value;

    ShopHolderType(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
