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

import java.util.Arrays;
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
    public BranchPO createBranch(Integer accountId, BranchVO branchVO) {
        log.info("[创建门店]accountId:{},branchVO:{}", accountId, branchVO);
        if (accountId == null || branchVO == null) {
            throw new ShopException(ResponseEnum.SHOP_NO_PARAM);
        }
        // 检查字段
        BranchServiceUtil.checkBranchVO(branchVO);
        // 逻辑验证
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (Objects.isNull(accountPO)) {
            throw new ShopException(ResponseEnum.BAD_REQUEST, "账户不存在");
        }
        if (!accountPO.getShopId().equals(branchVO.getShopId())) {
            throw new ShopException(ResponseEnum.BAD_REQUEST);
        }
        if (Objects.nonNull(branchRepository.findByName(branchVO.getName()))) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "该门店名已存在");
        }

        // 保存门店
        BranchPO branchPO = new BranchPO(null, branchVO.getShopId(), branchVO.getName(), branchVO.getNotice(), branchVO.getPhone(), branchVO.getAddress(),
                branchVO.getLongitude(), branchVO.getLatitude(), branchVO.getIntroduction(), BranchStatus.NORMAL.name(), null);
        return branchRepository.save(branchPO);
    }

    @Override
    public BranchPO updateBranch(Integer accountId, Integer branchId, BranchVO branchVO) {
        log.info("[更新门店]accountId:{},branchId:{},branchVO:{}", accountId, branchId, branchVO);
        // 检查字段
        if (accountId == null || branchId == null || branchVO == null) {
            throw new ShopException(ResponseEnum.BAD_REQUEST);
        }
        BranchServiceUtil.checkBranchVO(branchVO);
        if (StringUtils.isEmpty(branchVO.getShopId())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "shopId");
        }
        // 逻辑验证
        AccountPO accountPO = accountRepository.findOne(accountId);
        BranchPO branchPO = branchRepository.findOne(branchId);
        if (Objects.isNull(accountPO)) {
            throw new ShopException(ResponseEnum.BAD_REQUEST, "账户不存在");
        }
        if (Objects.isNull(branchPO)) {
            throw new ShopException(ResponseEnum.BAD_REQUEST, "门店不存在");
        }
        if (!accountPO.getShopId().equals(branchVO.getShopId())) {
            throw new ShopException(ResponseEnum.BAD_REQUEST);
        }
        if (!branchPO.getShopId().equals(branchVO.getShopId())) {
            throw new ShopException(ResponseEnum.BAD_REQUEST);
        }
        boolean managedBranch = !StringUtils.isEmpty(accountPO.getBranchIds()) &&
                Arrays.stream(accountPO.getBranchIds().split(",")).anyMatch(String.valueOf(branchId)::equals);
        if (!managedBranch) {
            throw new ShopException(ResponseEnum.BAD_REQUEST, "无法管理该门店");
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
}
