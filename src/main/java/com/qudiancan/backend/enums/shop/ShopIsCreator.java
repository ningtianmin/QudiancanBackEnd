package com.qudiancan.backend.enums.shop;

import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum ShopIsCreator {

    /**
     * 是账户创建者
     */
    YES("YES", "是"),
    /**
     * 不是账户创建者
     */
    NO("NO", "否");

    private String key;
    private String value;

    ShopIsCreator(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
