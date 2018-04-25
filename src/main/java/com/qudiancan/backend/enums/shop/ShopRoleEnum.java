package com.qudiancan.backend.enums.shop;

import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum ShopRoleEnum {
    /**
     * 管理员
     */
    SHOP_ADMIN("管理员", "餐厅管理员"),
    /**
     * 店长
     */
    BRANCH_MANAGER("店长", "门店店长"),
    /**
     * 主管
     */
    BRANCH_SUPERVISOR("主管", "门店主管"),
    /**
     * 收银员
     */
    BRANCH_CASHIER("收银员", "门店收银员"),
    /**
     * 服务员
     */
    BRANCH_WAITER("服务员", "门店服务员");

    private String name;
    private String description;

    ShopRoleEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
