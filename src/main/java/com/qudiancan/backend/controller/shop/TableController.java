package com.qudiancan.backend.controller.shop;

import com.qudiancan.backend.common.AccountHolder;
import com.qudiancan.backend.common.RequiredAuthority;
import com.qudiancan.backend.enums.AuthorityEnum;
import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.Session;
import com.qudiancan.backend.pojo.po.BranchTablePO;
import com.qudiancan.backend.pojo.po.TableCategoryPO;
import com.qudiancan.backend.pojo.vo.BranchTableVO;
import com.qudiancan.backend.pojo.vo.TableCategoryVO;
import com.qudiancan.backend.service.TableService;
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
public class TableController {
    @Autowired
    private TableService tableService;

    /**
     * 创建桌台类型
     *
     * @param session         账户session
     * @param shopId          店铺id
     * @param branchId        门店id
     * @param tableCategoryVO 桌台类型信息
     * @return 创建的桌台类型
     */
    @PostMapping("/{shopId}/branches/{branchId}/table_categories")
    @RequiredAuthority(AuthorityEnum.BRANCH_TABLE_CATEGORY_CREATE)
    public Response<TableCategoryPO> createTableCategory(Session session, @PathVariable String shopId, @PathVariable Integer branchId, TableCategoryVO tableCategoryVO) {
        log.info("[创建桌台类型]session:{}", session);
        return Response.success(tableService.createTableCategory(AccountHolder.get().getId(), shopId, branchId, tableCategoryVO));
    }

    /**
     * 获取桌台类型列表
     *
     * @param session  账户session
     * @param shopId   店铺id
     * @param branchId 门店id
     * @return 获取的桌台类型列表
     */
    @GetMapping("/{shopId}/branches/{branchId}/table_categories")
    @RequiredAuthority(AuthorityEnum.BRANCH_TABLE_CATEGORY_SHOW)
    public Response<List<TableCategoryPO>> listTableCategory(Session session, @PathVariable String shopId, @PathVariable Integer branchId) {
        log.info("[获取桌台类型列表]session:{}", session);
        return Response.success(tableService.listTableCategory(AccountHolder.get().getId(), shopId, branchId));
    }

    /**
     * 更新桌台类型
     *
     * @param session         账户session
     * @param shopId          店铺id
     * @param branchId        门店id
     * @param tableCategoryId 桌台类型id
     * @param tableCategoryVO 桌台类型信息
     * @return 更新后的桌台类型
     */
    @PostMapping("/{shopId}/branches/{branchId}/table_categories/{tableCategoryId}")
    @RequiredAuthority(AuthorityEnum.BRANCH_TABLE_CATEGORY_UPDATE)
    public Response<TableCategoryPO> updateTableCategory(Session session, @PathVariable String shopId, @PathVariable Integer branchId, @PathVariable Integer tableCategoryId, TableCategoryVO tableCategoryVO) {
        log.info("[更新桌台类型]session:{}", session);
        return Response.success(tableService.updateTableCategory(AccountHolder.get().getId(), shopId, branchId, tableCategoryId, tableCategoryVO));
    }

    /**
     * 创建桌台
     *
     * @param session       账户session
     * @param shopId        店铺id
     * @param branchId      门店id
     * @param branchTableVO 桌台信息
     * @return 创建的桌台
     */
    @PostMapping("/{shopId}/branches/{branchId}/tables")
    @RequiredAuthority(AuthorityEnum.BRANCH_TABLE_CREATE)
    public Response<BranchTablePO> createBranchTable(Session session, @PathVariable String shopId, @PathVariable Integer branchId, BranchTableVO branchTableVO) {
        log.info("[创建桌台]session:{}", session);
        return Response.success(tableService.createBranchTable(AccountHolder.get().getId(), shopId, branchId, branchTableVO));
    }

    /**
     * 获取桌台
     *
     * @param session       账户session
     * @param shopId        店铺id
     * @param branchId      门店id
     * @param branchTableId 桌台id
     * @return 获取的桌台
     */
    @GetMapping("/{shopId}/branches/{branchId}/tables/{branchTableId}")
    @RequiredAuthority(AuthorityEnum.BRANCH_TABLE_SHOW)
    public Response<BranchTablePO> getBranchTable(Session session, @PathVariable String shopId,
                                                  @PathVariable Integer branchId, @PathVariable Integer branchTableId) {
        log.info("[获取桌台]session:{}", session);
        return Response.success(tableService.getBranchTable(AccountHolder.get().getId(), shopId, branchId, branchTableId));
    }

    /**
     * 更新桌台
     *
     * @param session       账户session
     * @param shopId        店铺id
     * @param branchId      门店id
     * @param branchTableId 桌台id
     * @param branchTableVO 桌台信息
     * @return 更新后的桌台
     */
    @PostMapping("/{shopId}/branches/{branchId}/tables/{branchTableId}")
    @RequiredAuthority(AuthorityEnum.BRANCH_TABLE_UPDATE)
    public Response<BranchTablePO> updateBranchTable(Session session, @PathVariable String shopId, @PathVariable Integer branchId,
                                                     @PathVariable Integer branchTableId, BranchTableVO branchTableVO) {
        log.info("[更新桌台]session:{}", session);
        return Response.success(tableService.updateBranchTable(AccountHolder.get().getId(), shopId, branchId, branchTableId, branchTableVO));
    }
}