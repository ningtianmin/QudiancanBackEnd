package com.qudiancan.backend.controller.shop;

import com.qudiancan.backend.common.ShopAccountHolder;
import com.qudiancan.backend.common.ShopRequiredAuthority;
import com.qudiancan.backend.enums.shop.ShopAuthorityEnum;
import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.Session;
import com.qudiancan.backend.pojo.po.BranchProductPO;
import com.qudiancan.backend.pojo.po.ProductCategoryPO;
import com.qudiancan.backend.pojo.vo.shop.BranchProductVO;
import com.qudiancan.backend.pojo.vo.shop.ProductCategoryVO;
import com.qudiancan.backend.service.shop.ShopProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
@RestController
@RequestMapping("/shops")
@Slf4j
public class ShopProductController {
    @Autowired
    private ShopProductService shopProductService;

    /**
     * 获取产品类目列表
     *
     * @param session  账户session
     * @param shopId   店铺id
     * @param branchId 门店id
     * @return 获取的产品类目列表
     */
    @GetMapping("/{shopId}/branches/{branchId}/categories")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_CATEGORY_SHOW)
    public Response<List<ProductCategoryPO>> listProductCategory(Session session, @PathVariable String shopId, @PathVariable Integer branchId) {
        log.info("[获取产品类目列表]session:{}", session);
        return Response.success(shopProductService.listProductCategory(ShopAccountHolder.get().getId(), shopId, branchId));
    }

    /**
     * 创建产品类目
     *
     * @param session           账户session
     * @param shopId            店铺id
     * @param branchId          门店id
     * @param productCategoryVO 产品类目信息
     * @return 创建的产品类目
     */
    @PostMapping("/{shopId}/branches/{branchId}/categories")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_CATEGORY_CREATE)
    public Response<ProductCategoryPO> createProductCategory(Session session, @PathVariable String shopId, @PathVariable Integer branchId, ProductCategoryVO productCategoryVO) {
        log.info("[创建产品类目]session:{}", session);
        return Response.success(shopProductService.createProductCategory(ShopAccountHolder.get().getId(), shopId, branchId, productCategoryVO));
    }

    /**
     * 获取产品类目
     *
     * @param session    账户session
     * @param shopId     店铺id
     * @param branchId   门店id
     * @param categoryId 类目id
     * @return 获取的产品类目
     */
    @GetMapping("/{shopId}/branches/{branchId}/categories/{categoryId}")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_CATEGORY_SHOW)
    public Response<ProductCategoryPO> getProductCategory(Session session, @PathVariable String shopId, @PathVariable Integer branchId, @PathVariable Integer categoryId) {
        log.info("[获取产品类目]session:{}", session);
        return Response.success(shopProductService.getProductCategory(ShopAccountHolder.get().getId(), shopId, branchId, categoryId));
    }

    /**
     * 更新产品类目
     *
     * @param session           账户session
     * @param shopId            店铺id
     * @param branchId          门店id
     * @param categoryId        类目id
     * @param productCategoryVO 类目信息
     * @return 更新后的产品类目
     */
    @PostMapping("/{shopId}/branches/{branchId}/categories/{categoryId}")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_CATEGORY_SHOW)
    public Response<ProductCategoryPO> updateBranch(Session session, @PathVariable String shopId, @PathVariable Integer branchId,
                                                    @PathVariable Integer categoryId, ProductCategoryVO productCategoryVO) {
        log.info("[更新产品类目]session:{}", session);
        return Response.success(shopProductService.updateProductCategory(ShopAccountHolder.get().getId(), shopId, branchId, categoryId, productCategoryVO));
    }

    /**
     * 创建门店产品
     *
     * @param session         账户session
     * @param shopId          店铺id
     * @param branchId        门店id
     * @param branchProductVO 门店产品信息
     * @return 创建的门店产品
     */
    @PostMapping("/{shopId}/branches/{branchId}/products")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_PRODUCT_CREATE)
    public Response<BranchProductPO> createBranchProduct(Session session, @PathVariable String shopId,
                                                         @PathVariable Integer branchId, BranchProductVO branchProductVO) {
        log.info("[创建门店产品]session:{}", session);
        return Response.success(shopProductService.createBranchProduct(ShopAccountHolder.get().getId(), shopId, branchId, branchProductVO));
    }

    /**
     * 获取门店产品
     *
     * @param session   账户session
     * @param shopId    店铺id
     * @param branchId  门店id
     * @param productId 产品id
     * @return 获取的门店产品
     */
    @GetMapping("/{shopId}/branches/{branchId}/products/{productId}")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_PRODUCT_SHOW)
    public Response<BranchProductPO> getBranchProduct(Session session, @PathVariable String shopId,
                                                      @PathVariable Integer branchId, @PathVariable Integer productId) {
        log.info("[获取门店产品]session:{}", session);
        return Response.success(shopProductService.getBranchProduct(ShopAccountHolder.get().getId(), shopId, branchId, productId));
    }

    /**
     * 更新产品
     *
     * @param session         账户session
     * @param shopId          店铺id
     * @param branchId        门店id
     * @param productId       产品id
     * @param branchProductVO 产品信息
     * @return 更新后的产品
     */
    @PostMapping("/{shopId}/branches/{branchId}/products/{productId}")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_PRODUCT_UPDATE)
    public Response<BranchProductPO> updateBranchProduct(Session session, @PathVariable String shopId,
                                                         @PathVariable Integer branchId, @PathVariable Integer productId,
                                                         BranchProductVO branchProductVO) {
        log.info("[更新产品]session:{}", session);
        return Response.success(shopProductService.updateBranchProduct(ShopAccountHolder.get().getId(), shopId, branchId, productId, branchProductVO));
    }
}
