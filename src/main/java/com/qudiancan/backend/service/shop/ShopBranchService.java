package com.qudiancan.backend.service.shop;

import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.vo.shop.BranchVO;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface ShopBranchService {
    /**
     * 判断是否可以管理指定门店
     *
     * @param accountId 账户id
     * @param shopId    店铺id
     * @param branchId  门店id
     * @return 可以:true,不可以:false
     */
    boolean canManageBranch(Integer accountId, String shopId, Integer branchId);

    /**
     * 创建门店
     *
     * @param accountId 账户id
     * @param shopId    店铺id
     * @param branchVO  门店信息
     * @return 创建的门店
     */
    BranchPO createBranch(Integer accountId, String shopId, BranchVO branchVO);

    /**
     * 更新门店
     *
     * @param accountId 账户id
     * @param shopId    店铺id
     * @param branchId  门店id
     * @param branchVO  门店信息
     * @return 更新后的门店
     */
    BranchPO updateBranch(Integer accountId, String shopId, Integer branchId, BranchVO branchVO);

    /**
     * 获取门店
     *
     * @param accountId 账户id
     * @param shopId    店铺id
     * @param branchId  门店id
     * @return 获取的门店
     */
    BranchPO getBranch(Integer accountId, String shopId, Integer branchId);

    /**
     * 获取门店列表
     *
     * @param accountId 账户id
     * @param shopId    店铺id
     * @return 获取的门店列表
     */
    List<BranchPO> listBranch(Integer accountId, String shopId);
}
