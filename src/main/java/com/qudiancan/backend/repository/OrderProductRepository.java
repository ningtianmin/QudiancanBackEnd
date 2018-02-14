package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.OrderProductPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author NINGTIANMIN
 */
public interface OrderProductRepository extends JpaRepository<OrderProductPO, Integer> {
}
