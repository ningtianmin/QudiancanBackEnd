package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.BranchPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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

    /**
     * 通过餐厅id获取门店列表
     *
     * @param shopId 餐厅id
     * @return 门店列表
     */
    List<BranchPO> findByShopId(String shopId);

    /**
     * 通过餐厅id查询第一个门店
     *
     * @param shopId 餐厅id
     * @return 门店
     */
    BranchPO findFirstByShopId(String shopId);

}
