package com.qudiancan.backend.service.impl.shop;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.enums.shop.ShopBranchTableStatus;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.po.BranchTablePO;
import com.qudiancan.backend.pojo.po.TableCategoryPO;
import com.qudiancan.backend.pojo.vo.shop.BranchTableVO;
import com.qudiancan.backend.pojo.vo.shop.TableCategoryVO;
import com.qudiancan.backend.repository.BranchTableRepository;
import com.qudiancan.backend.repository.TableCategoryRepository;
import com.qudiancan.backend.service.shop.ShopBranchService;
import com.qudiancan.backend.service.shop.ShopTableService;
import com.qudiancan.backend.service.util.shop.ShopTableServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
public class ShopTableServiceImpl implements ShopTableService {
    @Autowired
    private TableCategoryRepository tableCategoryRepository;
    @Autowired
    private ShopBranchService shopBranchService;
    @Autowired
    private BranchTableRepository branchTableRepository;

    @Override
    public TableCategoryPO createTableCategory(Integer accountId, String shopId, Integer branchId, TableCategoryVO tableCategoryVO) {
        log.info("[创建桌台类型]accountId:{},shopId:{},branchId:{},tableCategoryVO:{}", accountId, shopId, branchId, tableCategoryVO);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(tableCategoryVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId,tableCategoryVO");
        }
        ShopTableServiceUtil.checkTableCategoryVO(tableCategoryVO);
        if (!shopBranchService.canManageBranch(accountId, shopId, branchId)) {
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
        if (!shopBranchService.canManageBranch(accountId, shopId, branchId)) {
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
        ShopTableServiceUtil.checkTableCategoryVO(tableCategoryVO);
        if (!shopBranchService.canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        TableCategoryPO tableCategoryPO = tableCategoryRepository.findOne(tableCategoryId);
        if (Objects.isNull(tableCategoryPO) || !branchId.equals(tableCategoryPO.getBranchId())) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        TableCategoryPO byBranchIdAndName = tableCategoryRepository.findByBranchIdAndName(branchId, tableCategoryVO.getName());
        if (Objects.nonNull(byBranchIdAndName) && !tableCategoryPO.getName().equals(byBranchIdAndName.getName())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "该名称已被占用");
        }
        tableCategoryPO.setName(tableCategoryVO.getName());
        tableCategoryPO.setPosition(tableCategoryVO.getPosition());
        return tableCategoryRepository.save(tableCategoryPO);
    }

    @Override
    public BranchTablePO createBranchTable(Integer accountId, String shopId, Integer branchId, BranchTableVO branchTableVO) {
        log.info("[创建桌台]accountId:{},shopId:{},branchId:{},branchTableVO:{}", accountId, shopId, branchId, branchTableVO);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(branchTableVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId,branchTableVO");
        }
        ShopTableServiceUtil.checkBranchTableVO(branchTableVO);
        if (!shopBranchService.canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        if (Objects.nonNull(branchTableRepository.findByBranchIdAndName(branchId, branchTableVO.getName()))) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "该桌台名称已被占用");
        }
        TableCategoryPO tableCategoryPO = tableCategoryRepository.findOne(branchTableVO.getCategoryId());
        if (Objects.isNull(tableCategoryPO) || !branchId.equals(tableCategoryPO.getBranchId())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "categoryId");
        }
        return branchTableRepository.save(new BranchTablePO(null, branchId, branchTableVO.getCategoryId(), null,
                branchTableVO.getName(), branchTableVO.getCapacity(), branchTableVO.getPosition(), ShopBranchTableStatus.LEISURE.name()));
    }

    @Override
    public BranchTablePO getBranchTable(Integer accountId, String shopId, Integer branchId, Integer branchTableId) {
        log.info("[获取桌台]accountId:{},shopId:{},branchId:{},branchTableId:{}", accountId, shopId, branchId, branchTableId);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(branchTableId)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId,branchTableId");
        }
        if (!shopBranchService.canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        BranchTablePO branchTablePO = branchTableRepository.findOne(branchTableId);
        if (Objects.isNull(branchTablePO) || !branchId.equals(branchTablePO.getBranchId())) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        return branchTablePO;
    }

    @Override
    public BranchTablePO updateBranchTable(Integer accountId, String shopId, Integer branchId, Integer branchTableId, BranchTableVO branchTableVO) {
        log.info("[更新桌台]accountId:{},shopId:{},branchId:{},branchTableId:{},branchTableVO:{}",
                accountId, shopId, branchId, branchTableId, branchTableVO);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(branchTableId) || Objects.isNull(branchTableVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId,branchTableId,branchTableVO");
        }
        ShopTableServiceUtil.checkBranchTableVO(branchTableVO);
        BranchTablePO branchTablePO = branchTableRepository.findOne(branchTableId);
        if (Objects.isNull(branchTablePO) || !branchId.equals(branchTablePO.getBranchId())) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        BranchTablePO byBranchIdAndName = branchTableRepository.findByBranchIdAndName(branchId, branchTableVO.getName());
        if (Objects.nonNull(byBranchIdAndName) && !branchTablePO.getName().equals(branchTableVO.getName())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "该名称已被占用");
        }
        TableCategoryPO tableCategoryPO = tableCategoryRepository.findOne(branchTableVO.getCategoryId());
        if (Objects.isNull(tableCategoryPO) || !branchId.equals(tableCategoryPO.getBranchId())) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        BeanUtils.copyProperties(branchTableVO, branchTablePO);
        return branchTablePO;
    }

    @Override
    public BranchTablePO getBranchTable(Integer tableId) {
        log.info("【获取桌台】tableId：{}", tableId);
        if (Objects.isNull(tableId)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE, "tableId");
        }
        return branchTableRepository.findOne(tableId);
    }
}
