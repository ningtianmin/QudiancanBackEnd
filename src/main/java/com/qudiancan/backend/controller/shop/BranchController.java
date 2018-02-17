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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NINGTIANMIN
 */
@RestController
@RequestMapping("/shops")
@Slf4j
public class BranchController {
    @Autowired
    private BranchService branchService;

    @PostMapping("/{shopId}/branches")
    @RequiredAuthority(AuthorityEnum.SHOP_BRANCH_CREATE)
    public Response<BranchPO> createBranch(Session session, BranchVO branchVO) {
        log.info("[创建门店]session:{},branchVO:{}", session, branchVO);
        return Response.success(branchService.createBranch(AccountHolder.get().getId(), branchVO));
    }

    @PostMapping("/{shopId}/branches/{branchId}/edit")
    @RequiredAuthority(AuthorityEnum.SHOP_BRANCH_UPDATE)
    public Response<BranchPO> updateBranch(Session session, BranchVO branchVO, @PathVariable Integer branchId) {
        log.info("[更新门店]session:{},branchVO:{},branchId:{}", session, branchVO, branchId);
        return Response.success(branchService.updateBranch(AccountHolder.get().getId(), branchId, branchVO));
    }
}
