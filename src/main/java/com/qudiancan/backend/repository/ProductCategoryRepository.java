package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.ProductCategoryPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryPO, Integer> {
    /**
     * 通过门店id查询所有产品类目
     *
     * @param branchId 门店id
     * @return 所有产品类目
     */
    List<ProductCategoryPO> findByBranchId(Integer branchId);
}
