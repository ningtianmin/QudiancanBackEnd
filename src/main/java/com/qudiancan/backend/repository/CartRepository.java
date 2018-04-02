package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.CartPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author NINGTIANMIN
 */
public interface CartRepository extends JpaRepository<CartPO, Integer> {
    /**
     * 通过门店id和微信用户openid获取购物车
     *
     * @param branchId 门店id
     * @param openId   微信用户openid
     * @return 获取购物车
     */
    CartPO findByBranchIdAndWechatId(Integer branchId, String openId);
}
