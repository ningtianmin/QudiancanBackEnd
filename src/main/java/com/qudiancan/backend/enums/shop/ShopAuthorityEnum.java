package com.qudiancan.backend.enums.shop;

import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum ShopAuthorityEnum {

    /**
     * 查看餐厅
     */
    SHOP_SHOP_SHOW(1, "SHOP_SHOP_SHOW"),
    /**
     * 更新餐厅
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
    BRANCH_PRODUCT_UPDATE(10, "BRANCH_PRODUCT_UPDATE"),
    /**
     * 创建出品部门
     */
    BRANCH_DEPARTMENT_CREATE(11, "BRANCH_DEPARTMENT_CREATE"),
    /**
     * 查看出品部门
     */
    BRANCH_DEPARTMENT_SHOW(12, "BRANCH_DEPARTMENT_SHOW"),
    /**
     * 更新出品部门
     */
    BRANCH_DEPARTMENT_UPDATE(13, "BRANCH_DEPARTMENT_UPDATE"),
    /**
     * 创建桌台类型
     */
    BRANCH_TABLE_CATEGORY_CREATE(14, "BRANCH_TABLE_CATEGORY_CREATE"),
    /**
     * 查看桌台类型
     */
    BRANCH_TABLE_CATEGORY_SHOW(15, "BRANCH_TABLE_CATEGORY_SHOW"),
    /**
     * 更新桌台类型
     */
    BRANCH_TABLE_CATEGORY_UPDATE(16, "BRANCH_TABLE_CATEGORY_UPDATE"),
    /**
     * 创建桌台
     */
    BRANCH_TABLE_CREATE(17, "BRANCH_TABLE_CREATE"),
    /**
     * 获取桌台
     */
    BRANCH_TABLE_SHOW(18, "BRANCH_TABLE_SHOW"),
    /**
     * 更新桌台
     */
    BRANCH_TABLE_UPDATE(19, "BRANCH_TABLE_UPDATE"),
    /**
     * 上架产品
     */
    BRANCH_PRODUCT_UP(20, "BRANCH_PRODUCT_UP"),
    /**
     * 下架产品
     */
    BRANCH_PRODUCT_DOWN(21, "BRANCH_PRODUCT_DOWN"),
    /**
     * 账号设置
     */
    SHOP_ACCOUNT_SETTING(22, "SHOP_ACCOUNT_SETTING"),
    /**
     * 账号创建
     */
    SHOP_ACCOUNT_CREATE(23, "SHOP_ACCOUNT_CREATE"),
    /**
     * 收银中心
     */
    SHOP_BRANCH_CASHIER(24, "SHOP_BRANCH_CASHIER"),
    /**
     * 查看订单
     */
    BRANCH_ORDER_SHOW(25, "BRANCH_ORDER_SHOW"),
    /**
     * 创建订单
     */
    BRANCH_ORDER_CREATE(26, "BRANCH_ORDER_CREATE");

    private Integer id;
    private String path;

    ShopAuthorityEnum(Integer id, String path) {
        this.id = id;
        this.path = path;
    }

}
