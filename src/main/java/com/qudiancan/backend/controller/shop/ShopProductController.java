package com.qudiancan.backend.controller.shop;

import com.aliyun.oss.OSSClient;
import com.qudiancan.backend.common.AliyunConfig;
import com.qudiancan.backend.common.ShopAccountHolder;
import com.qudiancan.backend.common.ShopRequiredAuthority;
import com.qudiancan.backend.enums.shop.ShopAuthorityEnum;
import com.qudiancan.backend.pojo.PageResponse;
import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.po.BranchProductPO;
import com.qudiancan.backend.pojo.po.ProductCategoryPO;
import com.qudiancan.backend.pojo.vo.shop.BranchProductVO;
import com.qudiancan.backend.pojo.vo.shop.ProductCategoryVO;
import com.qudiancan.backend.service.shop.ShopProductService;
import com.qudiancan.backend.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @Autowired
    private AliyunConfig aliyunConfig;

    /**
     * 获取产品类目列表
     *
     * @param shopId   店铺id
     * @param branchId 门店id
     * @return 获取的产品类目列表
     */
    @GetMapping("/{shopId}/branches/{branchId}/categories")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_CATEGORY_SHOW)
    public Response<List<ProductCategoryPO>> listProductCategory(@PathVariable String shopId, @PathVariable Integer branchId) {
        return Response.success(shopProductService.listProductCategory(ShopAccountHolder.get().getId(), shopId, branchId));
    }

    /**
     * 创建产品类目
     *
     * @param shopId            店铺id
     * @param branchId          门店id
     * @param productCategoryVO 产品类目信息
     * @return 创建的产品类目
     */
    @PostMapping("/{shopId}/branches/{branchId}/categories")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_CATEGORY_CREATE)
    public Response<ProductCategoryPO> createProductCategory(@PathVariable String shopId, @PathVariable Integer branchId, ProductCategoryVO productCategoryVO) {
        return Response.success(shopProductService.createProductCategory(ShopAccountHolder.get().getId(), shopId, branchId, productCategoryVO));
    }

    /**
     * 获取产品类目
     *
     * @param shopId     店铺id
     * @param branchId   门店id
     * @param categoryId 类目id
     * @return 获取的产品类目
     */
    @GetMapping("/{shopId}/branches/{branchId}/categories/{categoryId}")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_CATEGORY_SHOW)
    public Response<ProductCategoryPO> getProductCategory(@PathVariable String shopId, @PathVariable Integer branchId, @PathVariable Integer categoryId) {
        return Response.success(shopProductService.getProductCategory(ShopAccountHolder.get().getId(), shopId, branchId, categoryId));
    }

    /**
     * 更新产品类目
     *
     * @param shopId            店铺id
     * @param branchId          门店id
     * @param categoryId        类目id
     * @param productCategoryVO 类目信息
     * @return 更新后的产品类目
     */
    @PostMapping("/{shopId}/branches/{branchId}/categories/{categoryId}")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_CATEGORY_SHOW)
    public Response<ProductCategoryPO> updateBranch(@PathVariable String shopId, @PathVariable Integer branchId,
                                                    @PathVariable Integer categoryId, ProductCategoryVO productCategoryVO) {
        return Response.success(shopProductService.updateProductCategory(ShopAccountHolder.get().getId(), shopId, branchId, categoryId, productCategoryVO));
    }

    /**
     * 创建门店产品
     *
     * @param shopId          店铺id
     * @param branchId        门店id
     * @param branchProductVO 门店产品信息
     * @return 创建的门店产品
     */
    @PostMapping("/{shopId}/branches/{branchId}/products")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_PRODUCT_CREATE)
    public Response<BranchProductPO> createBranchProduct(@PathVariable String shopId,
                                                         @PathVariable Integer branchId, BranchProductVO branchProductVO) {
        return Response.success(shopProductService.createBranchProduct(ShopAccountHolder.get().getId(), shopId, branchId, branchProductVO));
    }

    /**
     * 获取门店产品
     *
     * @param shopId    店铺id
     * @param branchId  门店id
     * @param productId 产品id
     * @return 获取的门店产品
     */
    @GetMapping("/{shopId}/branches/{branchId}/products/{productId}")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_PRODUCT_SHOW)
    public Response<BranchProductPO> getBranchProduct(@PathVariable String shopId,
                                                      @PathVariable Integer branchId, @PathVariable Integer productId) {
        return Response.success(shopProductService.getBranchProduct(ShopAccountHolder.get().getId(), shopId, branchId, productId));
    }

    /**
     * 更新产品
     *
     * @param shopId          店铺id
     * @param branchId        门店id
     * @param productId       产品id
     * @param branchProductVO 产品信息
     * @return 更新后的产品
     */
    @PostMapping("/{shopId}/branches/{branchId}/products/{productId}")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_PRODUCT_UPDATE)
    public Response<BranchProductPO> updateBranchProduct(@PathVariable String shopId,
                                                         @PathVariable Integer branchId, @PathVariable Integer productId,
                                                         BranchProductVO branchProductVO) {
        return Response.success(shopProductService.updateBranchProduct(ShopAccountHolder.get().getId(), shopId, branchId, productId, branchProductVO));
    }

    /**
     * 分页获取产品列表
     *
     * @param shopId   店铺id
     * @param branchId 门店id
     * @return 产品列表
     */
    @GetMapping("/{shopId}/branches/{branchId}/products")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_PRODUCT_SHOW)
    public PageResponse<BranchProductPO> pageListBranchProduct(@PathVariable String shopId, @PathVariable Integer branchId,
                                                               @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return PageResponse.success(shopProductService.pageProduct(
                ShopAccountHolder.get().getId(), shopId, branchId, new PageRequest(page - 1, size)));
    }

    /**
     * 上传图片
     *
     * @param file 图片文件
     * @return 请求结果
     * @throws IOException 文件找不到
     */
    @PostMapping("/uploadImage")
    public Response uploadImage(@RequestParam MultipartFile file) throws IOException {
        OSSClient ossClient = new OSSClient(aliyunConfig.getEndpoint(), aliyunConfig.getAccesskeyId(), aliyunConfig.getAccessKeySecret());
        String imageName = KeyUtil.genImageKey() + ".jpg";
        ossClient.putObject(aliyunConfig.getBucketName(), imageName, file.getInputStream());
        return Response.success(String.format("https://%s.%s/%s", aliyunConfig.getBucketName(), aliyunConfig.getEndpoint(), imageName));
    }

    /**
     * 上架产品
     *
     * @param productId 产品id
     * @return 请求结果
     */
    @PostMapping("/products/{productId}/up")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_PRODUCT_UP)
    public Response upProduct(@PathVariable Integer productId) {
        shopProductService.upProduct(ShopAccountHolder.get().getId(), productId);
        return Response.success();
    }

    /**
     * 下架产品
     *
     * @param productId 产品id
     * @return 请求结果
     */
    @PostMapping("/products/{productId}/down")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_PRODUCT_DOWN)
    public Response downProduct(@PathVariable Integer productId) {
        shopProductService.downProduct(ShopAccountHolder.get().getId(), productId);
        return Response.success();
    }

}
