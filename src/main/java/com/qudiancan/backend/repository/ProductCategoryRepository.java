package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.ProductCategoryPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author NINGTIANMIN
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryPO, Integer> {
}
