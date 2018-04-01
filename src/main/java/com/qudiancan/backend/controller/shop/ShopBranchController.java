package com.qudiancan.backend.controller.shop;

import com.qudiancan.backend.common.ShopAccountHolder;
import com.qudiancan.backend.common.ShopRequiredAuthority;
import com.qudiancan.backend.enums.shop.ShopAuthorityEnum;
import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.Session;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.vo.shop.BranchVO;
import com.qudiancan.backend.service.shop.ShopBranchService;
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
public class ShopBranchController {
    @Autowired
    private ShopBranchService shopBranchService;

    /**
     * 创建门店
     *
     * @param session  账户session
     * @param branchVO 门店信息
     * @return 创建的门店
     */
    @PostMapping("/{shopId}/branches")
    @ShopRequiredAuthority(ShopAuthorityEnum.SHOP_BRANCH_CREATE)
    public Response<BranchPO> createBranch(Session session, @PathVariable String shopId, BranchVO branchVO) {
        log.info("[创建门店]session:{}", session);
        return Response.success(shopBranchService.createBranch(ShopAccountHolder.get().getId(), shopId, branchVO));
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
    @ShopRequiredAuthority(ShopAuthorityEnum.SHOP_BRANCH_UPDATE)
    public Response<BranchPO> updateBranch(Session session, @PathVariable String shopId, @PathVariable Integer branchId, BranchVO branchVO) {
        log.info("[更新门店]session:{}", session);
        return Response.success(shopBranchService.updateBranch(ShopAccountHolder.get().getId(), shopId, branchId, branchVO));
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
    @ShopRequiredAuthority(ShopAuthorityEnum.SHOP_BRANCH_SHOW)
    public Response<BranchPO> getBranch(Session session, @PathVariable String shopId, @PathVariable Integer branchId) {
        log.info("[获取门店]session:{}", session);
        return Response.success(shopBranchService.getBranch(ShopAccountHolder.get().getId(), shopId, branchId));
    }

    /**
     * 获取门店列表
     *
     * @param session 账户session
     * @param shopId  店铺id
     * @return 获取的门店列表
     */
    @GetMapping("/{shopId}/branches")
    @ShopRequiredAuthority(ShopAuthorityEnum.SHOP_BRANCH_SHOW)
    public Response<List<BranchPO>> listBranch(Session session, @PathVariable String shopId) {
        log.info("[获取门店列表]session:{}", session);
        return Response.success(shopBranchService.listBranch(ShopAccountHolder.get().getId(), shopId));
    }
}
