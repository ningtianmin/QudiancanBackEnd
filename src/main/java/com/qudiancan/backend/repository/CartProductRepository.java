package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.CartProductPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author NINGTIANMIN
 */
public interface CartProductRepository extends JpaRepository<CartProductPO, Integer> {
}
