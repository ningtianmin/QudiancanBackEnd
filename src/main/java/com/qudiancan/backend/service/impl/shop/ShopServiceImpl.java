package com.qudiancan.backend.service.impl.shop;

import com.qudiancan.backend.controller.shop.PerfectShopVO;
import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.enums.shop.ShopBranchStatus;
import com.qudiancan.backend.enums.shop.ShopIsCreator;
import com.qudiancan.backend.enums.shop.ShopStatus;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.po.ShopPO;
import com.qudiancan.backend.pojo.vo.shop.ShopVO;
import com.qudiancan.backend.repository.AccountRepository;
import com.qudiancan.backend.repository.BranchRepository;
import com.qudiancan.backend.repository.ShopRepository;
import com.qudiancan.backend.service.shop.ShopService;
import com.qudiancan.backend.service.util.shop.ShopServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * @author NINGTIANMIN
 */
@Service
@Slf4j
public class ShopServiceImpl implements ShopService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public ShopPO getShop(Integer accountId, String shopId) {
        log.info("[获取店铺]accountId:{},shopId:{}", accountId, shopId);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId");
        }
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (Objects.isNull(accountPO)) {
            throw new ShopException(ResponseEnum.SHOP_UNKNOWN_ACCOUNT);
        }
        if (!accountPO.getShopId().equals(shopId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        return shopRepository.findOne(shopId);
    }

    @Override
    public ShopPO updateShop(Integer accountId, String shopId, ShopVO shopVO) {
        log.info("[更新店铺]accountId:{},shopId:{},shopVO:{}", accountId, shopId, shopVO);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(shopVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId, shopVO");
        }
        ShopPO shopPO = getShop(accountId, shopId);
        // 检查字段
        ShopServiceUtil.checkShopVO(shopVO);

        shopPO.setHolderIdentify(shopVO.getHolderIdentify());
        shopPO.setHolderName(shopVO.getHolderName());
        shopPO.setHolderType(shopVO.getHolderType());
        shopPO.setIntroduction(shopVO.getIntroduction());
        shopPO.setName(shopVO.getName());
        shopPO.setTelephone(shopVO.getTelephone());
        return shopRepository.save(shopPO);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public BranchPO perfectShop(Integer accountId, PerfectShopVO perfectShopVO) {
        log.info("【完善店铺】accountId：{}，perfectShopVO：{}", accountId, perfectShopVO);
        if (Objects.isNull(accountId)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE, "accountId");
        }
        ShopServiceUtil.checkPerfectShopVO(perfectShopVO);
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (Objects.isNull(accountPO) || !accountPO.getIsCreator().equals(ShopIsCreator.YES.getKey())) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "accountId");
        }
        ShopPO shopPO = shopRepository.findOne(accountPO.getShopId());
        if (Objects.isNull(shopPO) || !shopPO.getStatus().equals(ShopStatus.REMAIN_PERFECT.getKey())) {
            throw new ShopException(ResponseEnum.BAD_REQUEST);
        }

        shopPO.setName(perfectShopVO.getShopName());
        shopPO.setHolderType(perfectShopVO.getShopHolderType());
        shopPO.setHolderName(perfectShopVO.getHolderName());
        shopPO.setHolderIdentify(perfectShopVO.getHolderIdentify());
        shopPO.setIntroduction(perfectShopVO.getShopIntroduction());
        shopPO.setStatus(ShopStatus.NORMAL.getKey());
        shopRepository.save(shopPO);

        BranchPO branchPO = new BranchPO(null, shopPO.getId(), perfectShopVO.getBranchName(),
                perfectShopVO.getBranchNotice(), perfectShopVO.getBranchPhone(),
                perfectShopVO.getBranchAddress(), perfectShopVO.getBranchLongitude(),
                perfectShopVO.getBranchLatitude(), perfectShopVO.getBranchIntroduction(),
                ShopBranchStatus.NORMAL.getKey(), null);
        return branchRepository.save(branchPO);
    }

}
