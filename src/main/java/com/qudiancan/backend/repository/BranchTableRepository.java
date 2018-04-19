package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.BranchTablePO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface BranchTableRepository extends JpaRepository<BranchTablePO, Integer> {
    /**
     * 通过门店id,门店桌台名称查询桌台
     *
     * @param branchId        门店id
     * @param branchTableName 门店桌台名称
     * @return 桌台
     */
    BranchTablePO findByBranchIdAndName(Integer branchId, String branchTableName);

    /**
     * 通过订单id获取桌台
     *
     * @param orderId 订单id
     * @return 桌台
     */
    BranchTablePO findByOrderId(Integer orderId);

    /**
     * 通过门店id获取桌台列表
     *
     * @param branchId 门店id
     * @return 桌台列表
     */
    List<BranchTablePO> findByBranchId(Integer branchId);
}
