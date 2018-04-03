package com.qudiancan.backend.service.shop;

import com.qudiancan.backend.pojo.dto.shop.ShopProductDTO;
import com.qudiancan.backend.pojo.po.BranchProductPO;
import com.qudiancan.backend.pojo.po.ProductCategoryPO;
import com.qudiancan.backend.pojo.vo.shop.BranchProductVO;
import com.qudiancan.backend.pojo.vo.shop.ProductCategoryVO;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface ShopProductService {
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

    /**
     * 创建门店产品
     *
     * @param accountId       账户id
     * @param shopId          店铺id
     * @param branchId        门店id
     * @param branchProductVO 门店产品信息
     * @return 创建的门店产品
     */
    BranchProductPO createBranchProduct(Integer accountId, String shopId, Integer branchId, BranchProductVO branchProductVO);

    /**
     * 获取门店产品
     *
     * @param accountId 账户id
     * @param shopId    店铺id
     * @param branchId  门店id
     * @param productId 产品id
     * @return 获取的门店产品
     */
    BranchProductPO getBranchProduct(Integer accountId, String shopId, Integer branchId, Integer productId);

    /**
     * 更新产品
     *
     * @param accountId       账户id
     * @param shopId          店铺id
     * @param branchId        门店id
     * @param productId       产品id
     * @param branchProductVO 产品信息
     * @return 更新后的产品
     */
    BranchProductPO updateBranchProduct(Integer accountId, String shopId, Integer branchId, Integer productId, BranchProductVO branchProductVO);

    /**
     * 获取门店上架产品信息
     *
     * @param branchId 门店id
     * @return 门店产品信息
     */
    List<ShopProductDTO> listProductUp(Integer branchId);
}
