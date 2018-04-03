package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.OrderProductPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface OrderProductRepository extends JpaRepository<OrderProductPO, Integer> {
    /**
     * 通过订单id查询订单产品列表
     *
     * @param orderId 订单id
     * @return 订单产品列表
     */
    List<OrderProductPO> findByOrderId(Integer orderId);

    /**
     * 通过订单id列表查询订单产品列表
     *
     * @param orderIds 订单id列表
     * @return 订单产品列表
     */
    List<OrderProductPO> findByOrderIdIn(List<Integer> orderIds);
}
