package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.DepartmentPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface DepartmentRepository extends JpaRepository<DepartmentPO, Integer> {
    /**
     * 通过门店id,出品部门名称查询门店出品部门
     *
     * @param branchId       门店id
     * @param departmentName 出品部门名称
     * @return 出品部门
     */
    DepartmentPO findByBranchIdAndName(Integer branchId, String departmentName);

    /**
     * 通过门店id查询出品部门列表
     *
     * @param branchId 门店id
     * @return 出品部门列表
     */
    List<DepartmentPO> findByBranchId(Integer branchId);
}
