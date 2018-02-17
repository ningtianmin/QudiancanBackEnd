package com.qudiancan.backend.service;

import com.qudiancan.backend.pojo.po.ShopPO;
import com.qudiancan.backend.pojo.vo.ShopVO;

/**
 * @author NINGTIANMIN
 */
public interface ShopService {
    /**
     * 查看店铺
     *
     * @param accountId 账户id
     * @param shopId    店铺id
     * @return 店铺
     */
    ShopPO getShop(Integer accountId, String shopId);

    /**
     * 更新店铺
     *
     * @param accountId 账户id
     * @param shopVO    店铺属性
     * @return 更新后的店铺
     */
    ShopPO updateShop(Integer accountId, ShopVO shopVO);
}
