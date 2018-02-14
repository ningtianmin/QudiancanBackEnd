package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.CartPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author NINGTIANMIN
 */
public interface CartRepository extends JpaRepository<CartPO, Integer> {
}
