package com.qudiancan.backend.controller.shop;

import com.qudiancan.backend.common.AccountHolder;
import com.qudiancan.backend.common.RequiredAuthority;
import com.qudiancan.backend.enums.AuthorityEnum;
import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.Session;
import com.qudiancan.backend.pojo.po.ProductCategoryPO;
import com.qudiancan.backend.pojo.vo.ProductCategoryVO;
import com.qudiancan.backend.service.ProductService;
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
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 获取产品类目列表
     *
     * @param session  账户session
     * @param shopId   店铺id
     * @param branchId 门店id
     * @return 获取的产品类目列表
     */
    @GetMapping("/{shopId}/branches/{branchId}/categories")
    @RequiredAuthority(AuthorityEnum.BRANCH_CATEGORY_SHOW)
    public Response<List<ProductCategoryPO>> listProductCategory(Session session, @PathVariable String shopId, @PathVariable Integer branchId) {
        log.info("[获取产品类目列表]session:{}", session);
        return Response.success(productService.listProductCategory(AccountHolder.get().getId(), shopId, branchId));
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
    @RequiredAuthority(AuthorityEnum.BRANCH_CATEGORY_CREATE)
    public Response<ProductCategoryPO> createProductCategory(Session session, @PathVariable String shopId, @PathVariable Integer branchId, ProductCategoryVO productCategoryVO) {
        log.info("[创建产品类目]session:{}", session);
        return Response.success(productService.createProductCategory(AccountHolder.get().getId(), shopId, branchId, productCategoryVO));
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
    @RequiredAuthority(AuthorityEnum.BRANCH_CATEGORY_SHOW)
    public Response<ProductCategoryPO> getProductCategory(Session session, @PathVariable String shopId, @PathVariable Integer branchId, @PathVariable Integer categoryId) {
        log.info("[获取产品类目]session:{}", session);
        return Response.success(productService.getProductCategory(AccountHolder.get().getId(), shopId, branchId, categoryId));
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
    @RequiredAuthority(AuthorityEnum.BRANCH_CATEGORY_SHOW)
    public Response<ProductCategoryPO> updateBranch(Session session, @PathVariable String shopId, @PathVariable Integer branchId,
                                                    @PathVariable Integer categoryId, ProductCategoryVO productCategoryVO) {
        log.info("[更新产品类目]session:{}", session);
        return Response.success(productService.updateProductCategory(AccountHolder.get().getId(), shopId, branchId, categoryId, productCategoryVO));
    }
}
