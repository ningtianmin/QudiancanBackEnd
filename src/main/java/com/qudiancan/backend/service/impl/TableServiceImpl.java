package com.qudiancan.backend.service.impl;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.po.TableCategoryPO;
import com.qudiancan.backend.pojo.vo.TableCategoryVO;
import com.qudiancan.backend.repository.TableCategoryRepository;
import com.qudiancan.backend.service.BranchService;
import com.qudiancan.backend.service.TableService;
import com.qudiancan.backend.service.util.TableServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author NINGTIANMIN
 */
@Service
@Slf4j
public class TableServiceImpl implements TableService {
    @Autowired
    private TableCategoryRepository tableCategoryRepository;
    @Autowired
    private BranchService branchService;

    @Override
    public TableCategoryPO createTableCategory(Integer accountId, String shopId, Integer branchId, TableCategoryVO tableCategoryVO) {
        log.info("[创建桌台类型]accountId:{},shopId:{},branchId:{},tableCategoryVO:{}", accountId, shopId, branchId, tableCategoryVO);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(tableCategoryVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId,tableCategoryVO");
        }
        TableServiceUtil.checkTableCategoryVO(tableCategoryVO);
        if (!branchService.canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        if (Objects.nonNull(tableCategoryRepository.findByBranchIdAndName(branchId, tableCategoryVO.getName()))) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "该名称已被占用");
        }
        return tableCategoryRepository.save(new TableCategoryPO(null, branchId, tableCategoryVO.getName(), tableCategoryVO.getPosition()));
    }

    @Override
    public List<TableCategoryPO> listTableCategory(Integer accountId, String shopId, Integer branchId) {
        log.info("[获取桌台类型列表]accountId:{},shopId:{},branchId:{}", accountId, shopId, branchId);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId");
        }
        if (!branchService.canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        List<TableCategoryPO> tableCategoryPOList = tableCategoryRepository.findByBranchId(branchId);
        return tableCategoryPOList.stream().sorted(Comparator.comparingInt(TableCategoryPO::getPosition)).collect(Collectors.toList());
    }

    @Override
    public TableCategoryPO updateTableCategory(Integer accountId, String shopId, Integer branchId, Integer tableCategoryId, TableCategoryVO tableCategoryVO) {
        log.info("[更新桌台类型]accountId:{},shopId:{},branchId:{},tableCategoryId:{},tableCategoryVO:{}",
                accountId, shopId, branchId, tableCategoryId, tableCategoryVO);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(tableCategoryId) || Objects.isNull(tableCategoryVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId,tableCategoryId,tableCategoryVO");
        }
        TableServiceUtil.checkTableCategoryVO(tableCategoryVO);
        if (!branchService.canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        TableCategoryPO tableCategoryPO = tableCategoryRepository.findOne(tableCategoryId);
        if (Objects.isNull(tableCategoryPO) || !branchId.equals(tableCategoryPO.getBranchId())) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        if (Objects.nonNull(tableCategoryRepository.findByBranchIdAndName(branchId, tableCategoryVO.getName()))) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "该名称已被占用");
        }
        tableCategoryPO.setName(tableCategoryVO.getName());
        tableCategoryPO.setPosition(tableCategoryVO.getPosition());
        return tableCategoryRepository.save(tableCategoryPO);
    }
}
