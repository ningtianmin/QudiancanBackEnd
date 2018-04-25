package com.qudiancan.backend.service.shop;

import com.qudiancan.backend.pojo.dto.shop.AccountInfoDTO;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.po.ShopPO;
import com.qudiancan.backend.pojo.vo.shop.PerfectShopVO;
import com.qudiancan.backend.pojo.vo.shop.ShopVO;

import java.util.List;
import java.util.Set;

/**
 * @author NINGTIANMIN
 */
public interface ShopService {
    /**
     * 获取餐厅
     *
     * @param accountId 账户id
     * @param shopId    餐厅id
     * @return 获取的餐厅
     */
    ShopPO getShop(Integer accountId, String shopId);

    /**
     * 更新餐厅
     *
     * @param accountId  账户id
     * @param shopId     餐厅id
     * @param shopVO     餐厅信息
     * @param fieldNames 需要更新的字段集合
     * @return 更新后的餐厅
     */
    ShopPO updateShop(Integer accountId, String shopId, ShopVO shopVO, Set<String> fieldNames);

    /**
     * 完善餐厅
     *
     * @param accountId     账户id
     * @param perfectShopVO 餐厅和第一个门店信息
     * @return 完善餐厅后返回的门店
     */
    BranchPO perfectShop(Integer accountId, PerfectShopVO perfectShopVO);

    /**
     * 通过餐厅的账户列表
     *
     * @param shopId 餐厅id
     * @return 账户列表
     */
    List<AccountInfoDTO> listShopAccount(String shopId);
}
