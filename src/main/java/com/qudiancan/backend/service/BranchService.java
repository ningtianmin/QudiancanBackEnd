package com.qudiancan.backend.service;

import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.vo.BranchVO;

/**
 * @author NINGTIANMIN
 */
public interface BranchService {
    /**
     * 创建门店
     *
     * @param accountId 账户id
     * @param branchVO  门店信息
     * @return 创建的门店
     */
    BranchPO createBranch(Integer accountId, BranchVO branchVO);

    /**
     * 更新门店
     *
     * @param accountId 账户id
     * @param branchId  门店id
     * @param branchVO  门店信息
     * @return 更新后的门店
     */
    BranchPO updateBranch(Integer accountId, Integer branchId, BranchVO branchVO);
}
