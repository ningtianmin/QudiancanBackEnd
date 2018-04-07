package com.qudiancan.backend.enums.shop;

import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum ShopBranchTableStatus {

    /**
     * 空闲的
     */
    LEISURE("LEISURE", "空闲的"),
    /**
     * 占用中
     */
    OCCUPIED("OCCUPIED", "占用中");

    private String key;
    private String value;

    ShopBranchTableStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
