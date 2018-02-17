package com.qudiancan.backend.enums;

import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum AuthorityEnum {
    /**
     * 查看店铺
     */
    SHOP_SHOP_SHOW(1, "SHOP_SHOP_SHOW"),
    /**
     * 更新店铺
     */
    SHOP_SHOP_UPDATE(2, "SHOP_SHOP_UPDATE");

    private Integer id;
    private String path;

    AuthorityEnum(Integer id, String path) {
        this.id = id;
        this.path = path;
    }
}
