package com.qudiancan.backend.service.impl;

import com.qudiancan.backend.enums.BranchProductStatus;
import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.po.BranchProductPO;
import com.qudiancan.backend.pojo.po.DepartmentPO;
import com.qudiancan.backend.pojo.po.ProductCategoryPO;
import com.qudiancan.backend.pojo.vo.BranchProductVO;
import com.qudiancan.backend.pojo.vo.ProductCategoryVO;
import com.qudiancan.backend.repository.BranchProductRepository;
import com.qudiancan.backend.repository.DepartmentRepository;
import com.qudiancan.backend.repository.ProductCategoryRepository;
import com.qudiancan.backend.service.BranchService;
import com.qudiancan.backend.service.ProductService;
import com.qudiancan.backend.service.util.ProductServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author NINGTIANMIN
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private BranchProductRepository branchProductRepository;
    @Autowired
    private BranchService branchService;

    @Override
    public List<ProductCategoryPO> listProductCategory(Integer accountId, String shopId, Integer branchId) {
        log.info("[获取产品类目列表]accountId:{},shopId:{},branchId;{}", accountId, shopId, branchId);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId");
        }
        // 逻辑验证
        if (!branchService.canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        return productCategoryRepository.findByBranchId(branchId);
    }

    @Override
    public ProductCategoryPO createProductCategory(Integer accountId, String shopId, Integer branchId, ProductCategoryVO productCategoryVO) {
        log.info("[创建产品类目]accountId:{},shopId:{},branchId:{},productCategoryVO:{}", accountId, shopId, branchId, productCategoryVO);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(productCategoryVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId,productCategoryVO");
        }
        ProductServiceUtil.checkProductCategoryVO(productCategoryVO);
        if (!branchService.canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        // 持久化
        return productCategoryRepository.save(new ProductCategoryPO(null, branchId, productCategoryVO.getName(), productCategoryVO.getPosition()));
    }

    @Override
    public ProductCategoryPO getProductCategory(Integer accountId, String shopId, Integer branchId, Integer categoryId) {
        log.info("[获取产品类目]accountId:{},shopId:{},branchId:{},categoryId:{}", accountId, shopId, branchId, categoryId);
        if (Objects.isNull(accountId) | StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(categoryId)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId,categoryId");
        }
        if (!branchService.canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        ProductCategoryPO productCategoryPO = productCategoryRepository.findOne(categoryId);
        if (!branchId.equals(productCategoryPO.getBranchId())) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        return productCategoryPO;
    }

    @Override
    public ProductCategoryPO updateProductCategory(Integer accountId, String shopId, Integer branchId, Integer categoryId, ProductCategoryVO productCategoryVO) {
        log.info("[更新产品类目]accountId:{},shopId:{},branchId:{},categoryId:{},productCategoryVO:{}",
                accountId, shopId, branchId, categoryId, productCategoryVO);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(categoryId) || Objects.isNull(productCategoryVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId,categoryId,productCategoryVO");
        }
        ProductServiceUtil.checkProductCategoryVO(productCategoryVO);
        ProductCategoryPO productCategoryPO = getProductCategory(accountId, shopId, branchId, categoryId);
        productCategoryPO.setName(productCategoryVO.getName());
        productCategoryPO.setPosition(productCategoryVO.getPosition());
        return productCategoryRepository.save(productCategoryPO);
    }

    @Override
    public BranchProductPO createBranchProduct(Integer accountId, String shopId, Integer branchId, BranchProductVO branchProductVO) {
        log.info("[创建门店产品]accountId:{},shopId:{},branchId:{},branchProductVO:{}", accountId, shopId, branchId, branchProductVO);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(branchProductVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId,branchProductVO");
        }
        ProductServiceUtil.checkBranchProductVO(branchProductVO);
        if (!branchService.canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        ProductCategoryPO productCategoryPO = productCategoryRepository.findOne(branchProductVO.getCategoryId());
        DepartmentPO departmentPO = departmentRepository.findOne(branchProductVO.getDepartmentId());
        if (Objects.isNull(productCategoryPO) || !branchId.equals(productCategoryPO.getBranchId())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "categoryId");
        }
        if (Objects.isNull(departmentPO) || !branchId.equals(departmentPO.getBranchId())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "departmentId");
        }
        BranchProductPO branchProductPO = new BranchProductPO(null, branchId, productCategoryPO.getId(), departmentPO.getId(), branchProductVO.getName(), branchProductVO.getUnitName(),
                branchProductVO.getPrice(), branchProductVO.getDescription(), branchProductVO.getPosition(), BranchProductStatus.NORMAL.name());
        return branchProductRepository.save(branchProductPO);
    }

    @Override
    public BranchProductPO getBranchProduct(Integer accountId, String shopId, Integer branchId, Integer productId) {
        log.info("[获取门店产品]accountId:{},shopId:{},branchId:{},productId:{}", accountId, shopId, branchId, productId);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(productId)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId,productId");
        }
        if (!branchService.canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        BranchProductPO branchProductPO = branchProductRepository.findOne(productId);
        if (Objects.isNull(branchProductPO) || !branchId.equals(branchProductPO.getBranchId())) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        return branchProductPO;
    }

    @Override
    public BranchProductPO updateBranchProduct(Integer accountId, String shopId, Integer branchId, Integer productId, BranchProductVO branchProductVO) {
        log.info("[更新产品]accountId:{},shopId:{},branchId:{},productId:{},branchProductVO:{}", accountId, shopId, branchId, productId, branchProductVO);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(productId) || Objects.isNull(branchProductVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId,productId,branchProductVO");
        }
        BranchProductPO branchProductPO = getBranchProduct(accountId, shopId, branchId, productId);
        ProductServiceUtil.checkBranchProductVO(branchProductVO);
        ProductCategoryPO productCategoryPO = productCategoryRepository.findOne(branchProductVO.getCategoryId());
        DepartmentPO departmentPO = departmentRepository.findOne(branchProductVO.getDepartmentId());
        if (Objects.isNull(productCategoryPO) || !branchId.equals(productCategoryPO.getBranchId())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "categoryId");
        }
        if (Objects.isNull(departmentPO) || !branchId.equals(departmentPO.getBranchId())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "departmentId");
        }
        BeanUtils.copyProperties(branchProductVO, branchProductPO);
        return branchProductRepository.save(branchProductPO);
    }
}
