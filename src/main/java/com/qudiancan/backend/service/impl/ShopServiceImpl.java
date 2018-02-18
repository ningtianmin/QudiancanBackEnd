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
import org.springframework.util.StringUtils;

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
}
