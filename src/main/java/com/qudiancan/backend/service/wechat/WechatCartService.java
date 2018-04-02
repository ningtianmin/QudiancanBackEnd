package com.qudiancan.backend.service.wechat;

import com.qudiancan.backend.pojo.po.CartProductPO;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface WechatCartService {
    /**
     * 获取购物车
     *
     * @param branchId 门店id
     * @param openid   微信用户openid
     * @return 购物车
     */
    List<CartProductPO> getCart(Integer branchId, String openid);

    /**
     * 清空购物车
     *
     * @param branchId 门店id
     * @param openid   微信用户openid
     */
    void clearCart(Integer branchId, String openid);

    /**
     * 减少购物车产品数量
     *
     * @param branchId  门店id
     * @param openid    微信用户id
     * @param productId 产品id
     */
    void subtractNum(Integer branchId, String openid, Integer productId);

    /**
     * 增加购物车产品数量
     *
     * @param branchId  门店id
     * @param openid    微信用户id
     * @param productId 产品id
     */
    void addNum(Integer branchId, String openid, Integer productId);
}
