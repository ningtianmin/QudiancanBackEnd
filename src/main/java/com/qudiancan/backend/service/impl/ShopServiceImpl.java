package com.qudiancan.backend.service.impl;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.ShopPO;
import com.qudiancan.backend.pojo.vo.ShopVO;
import com.qudiancan.backend.repository.AccountRepository;
import com.qudiancan.backend.repository.ShopRepository;
import com.qudiancan.backend.service.ShopService;
import com.qudiancan.backend.service.util.ShopServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public ShopPO getShop(Integer accountId, String shopId) {
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (Objects.isNull(accountPO)) {
            log.warn("[获取店铺失败]accountId:{},shopId:{}", accountId, shopId);
            throw new ShopException(ResponseEnum.SHOP_UNKNOWN_ACCOUNT);
        }
        if (!accountPO.getShopId().equals(shopId)) {
            log.warn("[获取店铺失败]accountId:{},shopId:{}", accountId, shopId);
            throw new ShopException(ResponseEnum.BAD_REQUEST);
        }
        return shopRepository.findOne(shopId);
    }

    @Override
    public ShopPO updateShop(Integer accountId, ShopVO shopVO) {
        log.info("[更新店铺]accountId:{},shopVO:{}", accountId, shopVO);
        if (accountId == null || shopVO == null) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG);
        }
        ShopPO shopPO = getShop(accountId, shopVO.getShopId());
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
}
