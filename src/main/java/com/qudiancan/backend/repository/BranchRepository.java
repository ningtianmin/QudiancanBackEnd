package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.BranchPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author NINGTIANMIN
 */
public interface BranchRepository extends JpaRepository<BranchPO, Integer> {
    /**
     * 通过门店名查询门店
     *
     * @param name 门店名
     * @return 门店
     */
    BranchPO findByName(String name);
}
