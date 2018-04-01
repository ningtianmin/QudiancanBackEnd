package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.BranchProductPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface BranchProductRepository extends JpaRepository<BranchProductPO, Integer> {
    /**
     * 通过门店id和产品状态获取产品列表
     *
     * @param branchId      门店id
     * @param productStatus 产品状态
     * @return 产品列表
     */
    List<BranchProductPO> findByBranchIdAndStatus(Integer branchId, String productStatus);
}
