package com.qudiancan.backend.service.impl;

import com.qudiancan.backend.enums.BranchStatus;
import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.vo.BranchVO;
import com.qudiancan.backend.repository.AccountRepository;
import com.qudiancan.backend.repository.BranchRepository;
import com.qudiancan.backend.service.BranchService;
import com.qudiancan.backend.service.util.BranchServiceUtil;
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
public class BranchServiceImpl implements BranchService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public BranchPO createBranch(Integer accountId, String shopId, BranchVO branchVO) {
        log.info("[创建门店]accountId:{},shopId:{},branchVO:{}", accountId, shopId, branchVO);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchVO");
        }
        // 检查字段
        BranchServiceUtil.checkBranchVO(branchVO);
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
                branchVO.getLongitude(), branchVO.getLatitude(), branchVO.getIntroduction(), BranchStatus.NORMAL.name(), null);
        return branchRepository.save(branchPO);
    }

    @Override
    public BranchPO updateBranch(Integer accountId, String shopId, Integer branchId, BranchVO branchVO) {
        log.info("[更新门店]accountId:{},shopId:{},branchId:{},branchVO:{}", accountId, shopId, branchId, branchVO);
        // 检查字段
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(branchVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId, shopId,branchId, branchVO");
        }
        BranchServiceUtil.checkBranchVO(branchVO);
        // 逻辑验证
        AccountPO accountPO = accountRepository.findOne(accountId);
        BranchPO branchPO = branchRepository.findOne(branchId);
        if (Objects.isNull(accountPO)) {
            throw new ShopException(ResponseEnum.BAD_REQUEST, "账户不存在");
        }
        if (Objects.isNull(branchPO)) {
            throw new ShopException(ResponseEnum.BAD_REQUEST, "门店不存在");
        }
        if (!BranchServiceUtil.canManageBranch(accountPO, shopId, branchPO)) {
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
        AccountPO accountPO = accountRepository.findOne(accountId);
        BranchPO branchPO = branchRepository.findOne(branchId);
        if (!BranchServiceUtil.canManageBranch(accountPO, shopId, branchPO)) {
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
}
