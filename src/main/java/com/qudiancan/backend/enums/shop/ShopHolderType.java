package com.qudiancan.backend.enums.shop;

import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum ShopHolderType {

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
