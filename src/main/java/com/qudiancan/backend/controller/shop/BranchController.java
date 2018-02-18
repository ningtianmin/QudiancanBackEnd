package com.qudiancan.backend.controller.shop;

import com.qudiancan.backend.common.AccountHolder;
import com.qudiancan.backend.common.RequiredAuthority;
import com.qudiancan.backend.enums.AuthorityEnum;
import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.Session;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.vo.BranchVO;
import com.qudiancan.backend.service.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
@RestController
@RequestMapping("/shops")
@Slf4j
public class BranchController {
    @Autowired
    private BranchService branchService;

    /**
     * 创建门店
     *
     * @param session  账户session
     * @param branchVO 门店信息
     * @return 创建的门店
     */
    @PostMapping("/{shopId}/branches")
    @RequiredAuthority(AuthorityEnum.SHOP_BRANCH_CREATE)
    public Response<BranchPO> createBranch(Session session, @PathVariable String shopId, BranchVO branchVO) {
        log.info("[创建门店]session:{}", session);
        return Response.success(branchService.createBranch(AccountHolder.get().getId(), shopId, branchVO));
    }

    /**
     * 更新门店
     *
     * @param session  账户session
     * @param shopId   店铺id
     * @param branchId 门店id
     * @param branchVO 门店信息
     * @return 更新后的门店
     */
    @PostMapping("/{shopId}/branches/{branchId}")
    @RequiredAuthority(AuthorityEnum.SHOP_BRANCH_UPDATE)
    public Response<BranchPO> updateBranch(Session session, @PathVariable String shopId, @PathVariable Integer branchId, BranchVO branchVO) {
        log.info("[更新门店]session:{}", session);
        return Response.success(branchService.updateBranch(AccountHolder.get().getId(), shopId, branchId, branchVO));
    }

    /**
     * 获取门店
     *
     * @param session  账户session
     * @param shopId   店铺id
     * @param branchId 门店id
     * @return 获取的门店
     */
    @GetMapping("/{shopId}/branches/{branchId}")
    @RequiredAuthority(AuthorityEnum.SHOP_BRANCH_SHOW)
    public Response<BranchPO> getBranch(Session session, @PathVariable String shopId, @PathVariable Integer branchId) {
        log.info("[获取门店]session:{}", session);
        return Response.success(branchService.getBranch(AccountHolder.get().getId(), shopId, branchId));
    }

    /**
     * 获取门店列表
     *
     * @param session 账户session
     * @param shopId  店铺id
     * @return 获取的门店列表
     */
    @GetMapping("/{shopId}/branches")
    @RequiredAuthority(AuthorityEnum.SHOP_BRANCH_SHOW)
    public Response<List<BranchPO>> listBranch(Session session, @PathVariable String shopId) {
        log.info("[获取门店列表]session:{}", session);
        return Response.success(branchService.listBranch(AccountHolder.get().getId(), shopId));
    }
}
