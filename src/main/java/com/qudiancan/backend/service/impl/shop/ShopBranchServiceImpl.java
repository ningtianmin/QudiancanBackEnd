package com.qudiancan.backend.service.impl.shop;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.enums.shop.ShopBranchStatus;
import com.qudiancan.backend.enums.shop.ShopIsCreator;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.vo.shop.BranchVO;
import com.qudiancan.backend.repository.AccountRepository;
import com.qudiancan.backend.repository.BranchRepository;
import com.qudiancan.backend.service.shop.ShopBranchService;
import com.qudiancan.backend.service.util.shop.ShopBranchServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author NINGTIANMIN
 */
@Service
@Slf4j
public class ShopBranchServiceImpl implements ShopBranchService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private ShopBranchService shopBranchService;

    @Override
    public boolean canManageBranch(Integer accountId, String shopId, Integer branchId) {
        AccountPO accountPO = accountRepository.findOne(accountId);
        BranchPO branchPO = branchRepository.findOne(branchId);
        return (Objects.nonNull(accountPO) && !StringUtils.isEmpty(shopId) && Objects.nonNull(branchPO)) &&
                (shopId.equals(accountPO.getShopId()) && shopId.equals(branchPO.getShopId())) &&
                ((ShopIsCreator.YES.name().equals(accountPO.getIsCreator())) ||
                        (!StringUtils.isEmpty(accountPO.getBranchIds()) &&
                                Arrays.stream(accountPO.getBranchIds().split(",")).anyMatch(String.valueOf(branchPO.getId())::equals)));
    }

    @Override
    public BranchPO createBranch(Integer accountId, String shopId, BranchVO branchVO) {
        log.info("[创建门店]accountId:{},shopId:{},branchVO:{}", accountId, shopId, branchVO);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchVO");
        }
        // 检查字段
        ShopBranchServiceUtil.checkBranchVO(branchVO);
        // 逻辑验证
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (Objects.isNull(accountPO)) {
            throw new ShopException(ResponseEnum.BAD_REQUEST, "账户不存在");
        }
        if (!accountPO.getShopId().equals(shopId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        if (Objects.nonNull(branchRepository.findByName(branchVO.getName()))) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "该门店名已存在");
        }

        // 保存门店
        BranchPO branchPO = new BranchPO(null, shopId, branchVO.getName(), branchVO.getNotice(), branchVO.getPhone(), branchVO.getAddress(),
                branchVO.getLongitude(), branchVO.getLatitude(), branchVO.getIntroduction(), ShopBranchStatus.NORMAL.getKey(), null);
        return branchRepository.save(branchPO);
    }

    @Override
    public BranchPO updateBranch(Integer accountId, String shopId, Integer branchId, BranchVO branchVO) {
        log.info("[更新门店]accountId:{},shopId:{},branchId:{},branchVO:{}", accountId, shopId, branchId, branchVO);
        // 检查字段
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(branchVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId, shopId,branchId, branchVO");
        }
        ShopBranchServiceUtil.checkBranchVO(branchVO);
        // 逻辑验证
        AccountPO accountPO = accountRepository.findOne(accountId);
        BranchPO branchPO = branchRepository.findOne(branchId);
        if (Objects.isNull(accountPO)) {
            throw new ShopException(ResponseEnum.BAD_REQUEST, "账户不存在");
        }
        if (Objects.isNull(branchPO)) {
            throw new ShopException(ResponseEnum.BAD_REQUEST, "门店不存在");
        }
        if (!canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        // 持久化
        branchPO.setAddress(branchVO.getAddress());
        branchPO.setIntroduction(branchVO.getIntroduction());
        branchPO.setLatitude(branchVO.getLatitude());
        branchPO.setLongitude(branchVO.getLongitude());
        branchPO.setName(branchVO.getName());
        branchPO.setNotice(branchVO.getNotice());
        branchPO.setPhone(branchVO.getPhone());
        return branchRepository.save(branchPO);
    }

    @Override
    public BranchPO getBranch(Integer accountId, String shopId, Integer branchId) {
        log.info("[获取门店]accountId:{},shopId:{},branchId:{}", accountId, shopId, branchId);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId");
        }
        BranchPO branchPO = branchRepository.findOne(branchId);
        if (!canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        return branchPO;
    }

    @Override
    public List<BranchPO> listBranch(Integer accountId, String shopId) {
        log.info("[获取门店列表]accountId:{},shopId:{}", accountId, shopId);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId");
        }
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (Objects.isNull(accountPO) || !shopId.equals(accountPO.getShopId())) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        return branchRepository.findByShopId(shopId);
    }

    @Override
    public List<BranchPO> getBranches(String branchIds) {
        if (StringUtils.isEmpty(branchIds)) {
            return Collections.emptyList();
        }
        return branchRepository.findAll(Arrays.stream(branchIds.split(",")).map(Integer::valueOf).collect(Collectors.toSet()));
    }

    @Override
    public List<BranchPO> listBranchManage(Integer accountId) {
        if (Objects.isNull(accountId)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE);
        }
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (Objects.isNull(accountPO)) {
            throw new ShopException(ResponseEnum.PARAM_INVALID);
        }
        List<BranchPO> all = branchRepository.findByShopId(accountPO.getShopId());
        if (ShopIsCreator.YES.getKey().equals(accountPO.getIsCreator())) {
            // 返回所有的门店
            return all;
        }
        Set<Integer> branchIdSet = Arrays.stream(accountPO.getBranchIds().split(",")).map(Integer::valueOf).collect(Collectors.toSet());
        return all.stream().filter(o -> branchIdSet.contains(o.getId())).collect(Collectors.toList());
    }
}
