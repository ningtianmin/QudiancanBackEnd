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
    SHOP_SHOP_UPDATE(2, "SHOP_SHOP_UPDATE"),
    /**
     * 查看门店
     */
    SHOP_BRANCH_SHOW(3, "SHOP_BRANCH_SHOW"),
    /**
     * 创建门店
     */
    SHOP_BRANCH_CREATE(4, "SHOP_BRANCH_CREATE"),
    /**
     * 更新门店
     */
    SHOP_BRANCH_UPDATE(5, "SHOP_BRANCH_UPDATE"),
    /**
     * 查看产品类目
     */
    BRANCH_CATEGORY_SHOW(6, "BRANCH_CATEGORY_SHOW"),
    /**
     * 创建产品类目
     */
    BRANCH_CATEGORY_CREATE(7, "BRANCH_CATEGORY_CREATE"),
    /**
     * 创建产品
     */
    BRANCH_PRODUCT_CREATE(8, "BRANCH_PRODUCT_CREATE"),
    /**
     * 查看产品
     */
    BRANCH_PRODUCT_SHOW(9, "BRANCH_PRODUCT_SHOW"),
    /**
     * 更新产品
     */
    BRANCH_PRODUCT_UPDATE(10, "BRANCH_PRODUCT_UPDATE");

    private Integer id;
    private String path;

    AuthorityEnum(Integer id, String path) {
        this.id = id;
        this.path = path;
    }
}
