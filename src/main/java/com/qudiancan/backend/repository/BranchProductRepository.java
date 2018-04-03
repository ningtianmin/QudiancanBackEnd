package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.BranchProductPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

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

    /**
     * 通过产品id和产品状态查询产品
     *
     * @param productId     产品id
     * @param productStatus 产品状态
     * @return 产品
     */
    BranchProductPO findByIdAndStatus(Integer productId, String productStatus);

    /**
     * 通过产品id列表和产品状态查询产品列表
     *
     * @param productIds 产品id列表
     * @return 产品列表
     */
    List<BranchProductPO> findByIdIn(Set<Integer> productIds);
}
