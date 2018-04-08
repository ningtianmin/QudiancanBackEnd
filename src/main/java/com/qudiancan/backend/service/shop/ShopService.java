package com.qudiancan.backend.service.shop;

import com.qudiancan.backend.controller.shop.PerfectShopVO;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.po.ShopPO;
import com.qudiancan.backend.pojo.vo.shop.ShopVO;

/**
 * @author NINGTIANMIN
 */
public interface ShopService {
    /**
     * 获取店铺
     *
     * @param accountId 账户id
     * @param shopId    店铺id
     * @return 获取的店铺
     */
    ShopPO getShop(Integer accountId, String shopId);

    /**
     * 更新店铺
     *
     * @param accountId 账户id
     * @param shopId    店铺id
     * @param shopVO    店铺信息
     * @return 更新后的店铺
     */
    ShopPO updateShop(Integer accountId, String shopId, ShopVO shopVO);

    /**
     * 完善店铺
     *
     * @param accountId     账户id
     * @param perfectShopVO 店铺和第一个门店信息
     * @return 完善店铺后返回的门店
     */
    BranchPO perfectShop(Integer accountId, PerfectShopVO perfectShopVO);
}
