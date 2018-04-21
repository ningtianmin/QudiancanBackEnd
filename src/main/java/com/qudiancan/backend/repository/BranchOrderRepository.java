package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.BranchOrderPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
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

    /**
     * 通过会员id列表查询订单列表
     *
     * @param memberIds 会员id列表
     * @return 订单列表
     */
    List<BranchOrderPO> findByMemberIdIn(Set<Integer> memberIds);

    /**
     * 按时间段查询门店订单列表
     *
     * @param branchId  门店id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 门店订单列表
     */
    List<BranchOrderPO> findByBranchIdAndCreateTimeBetween(Integer branchId, Timestamp startTime, Timestamp endTime);

    /**
     * 按门店分页查询订单
     *
     * @param branchId 门店id
     * @param pageable 分页参数
     * @return 分页订单
     */
    Page<BranchOrderPO> findByBranchIdOrderByCreateTimeDesc(Integer branchId, Pageable pageable);
}
