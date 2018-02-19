package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.TableCategoryPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface TableCategoryRepository extends JpaRepository<TableCategoryPO, Integer> {
    /**
     * 通过门店id,桌台类型名称查询桌台类型
     *
     * @param branchId          门店id
     * @param tableCategoryName 桌台类型名称
     * @return 桌台类型
     */
    TableCategoryPO findByBranchIdAndName(Integer branchId, String tableCategoryName);

    /**
     * 通过门店id获取桌台类型列表
     *
     * @param branchId 门店id
     * @return 获取的桌台类型列表
     */
    List<TableCategoryPO> findByBranchId(Integer branchId);
}
