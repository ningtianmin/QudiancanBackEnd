package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.BranchOrderPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * @author NINGTIANMIN
 */
public interface BranchOrderRepository extends JpaRepository<BranchOrderPO, Integer> {
    /**
     * 通过订单编号查询订单
     *
     * @param orderNumber 订单编号
     * @return 订单
     */
    BranchOrderPO findByOrderNumber(String orderNumber);

    /**
     * 通过会员id分页查询订单列表
     *
     * @param memberId 会员id
     * @param pageable 分页参数
     * @return 订单列表
     */
    Page<BranchOrderPO> findByMemberIdOrderByCreateTimeDesc(Integer memberId, Pageable pageable);

    /**
     * 通过订单编号列表查询订单列表
     *
     * @param orderNumbers 订单编号列表
     * @return 订单列表
     */
    List<BranchOrderPO> findByOrderNumberIn(Set<String> orderNumbers);
}
