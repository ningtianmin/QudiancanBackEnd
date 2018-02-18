package com.qudiancan.backend.service;

import com.qudiancan.backend.pojo.po.ProductCategoryPO;
import com.qudiancan.backend.pojo.vo.ProductCategoryVO;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface ProductService {
    /**
     * 获取产品类目列表
     *
     * @param accountId 账户id
     * @param shopId    店铺id
     * @param branchId  门店id
     * @return 产品类目列表
     */
    List<ProductCategoryPO> listProductCategory(Integer accountId, String shopId, Integer branchId);

    /**
     * 创建产品类目
     *
     * @param accountId         账户id
     * @param shopId            店铺id
     * @param branchId          门店id
     * @param productCategoryVO 产品类目信息
     * @return 创建的产品类目
     */
    ProductCategoryPO createProductCategory(Integer accountId, String shopId, Integer branchId, ProductCategoryVO productCategoryVO);

    /**
     * 获取产品类目
     *
     * @param accountId  账户id
     * @param shopId     店铺id
     * @param branchId   门店id
     * @param categoryId 产品类目id
     * @return 获取的产品类目
     */
    ProductCategoryPO getProductCategory(Integer accountId, String shopId, Integer branchId, Integer categoryId);

    /**
     * 更新产品类目
     *
     * @param accountId         账户id
     * @param shopId            店铺id
     * @param branchId          门店id
     * @param categoryId        类目id
     * @param productCategoryVO 类目信息
     * @return 更新后的产品类目
     */
    ProductCategoryPO updateProductCategory(Integer accountId, String shopId, Integer branchId, Integer categoryId, ProductCategoryVO productCategoryVO);
}
