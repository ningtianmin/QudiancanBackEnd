package com.qudiancan.backend.service.impl;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.po.ProductCategoryPO;
import com.qudiancan.backend.pojo.vo.ProductCategoryVO;
import com.qudiancan.backend.repository.AccountRepository;
import com.qudiancan.backend.repository.BranchRepository;
import com.qudiancan.backend.repository.ProductCategoryRepository;
import com.qudiancan.backend.service.ProductService;
import com.qudiancan.backend.service.util.BranchServiceUtil;
import com.qudiancan.backend.service.util.ProductServiceUtil;
import lombok.extern.slf4j.Slf4j;
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
    private AccountRepository accountRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public List<ProductCategoryPO> listProductCategory(Integer accountId, String shopId, Integer branchId) {
        log.info("[获取产品类目列表]accountId:{},shopId:{},branchId;{}", accountId, shopId, branchId);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId");
        }
        // 逻辑验证
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (!BranchServiceUtil.canManageBranch(accountPO, shopId, branchRepository.findOne(branchId))) {
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
        AccountPO accountPO = accountRepository.findOne(accountId);
        BranchPO branchPO = branchRepository.findOne(branchId);
        if (!BranchServiceUtil.canManageBranch(accountPO, shopId, branchPO)) {
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
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (!BranchServiceUtil.canManageBranch(accountPO, shopId, branchRepository.findOne(branchId))) {
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
}
