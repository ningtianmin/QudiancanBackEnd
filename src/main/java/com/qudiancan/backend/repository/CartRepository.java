package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.CartPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author NINGTIANMIN
 */
public interface CartRepository extends JpaRepository<CartPO, Integer> {
    /**
     * 通过门店id和会员id获取购物车
     *
     * @param branchId 门店id
     * @param memberId 会员id
     * @return 购物车
     */
    CartPO findByBranchIdAndMemberId(Integer branchId, Integer memberId);
}
