package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.CartProductPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface CartProductRepository extends JpaRepository<CartProductPO, Integer> {
    /**
     * 通过购物车id获取购物车产品列表
     *
     * @param cartId 购物车id
     * @return 购物车产品列表
     */
    List<CartProductPO> findByCartId(Integer cartId);

    /**
     * 通过购物车id清空购物车
     *
     * @param cartId 购物车id
     */
    void deleteAllByCartId(Integer cartId);

    /**
     * 通过购物车id和产品id获取购物车产品
     *
     * @param cartId    购物车id
     * @param productId 产品id
     * @return 购物车产品
     */
    CartProductPO findByCartIdAndProductId(Integer cartId, Integer productId);
}
