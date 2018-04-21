package com.qudiancan.backend.controller.shop;

import com.qudiancan.backend.common.ShopAccountHolder;
import com.qudiancan.backend.common.ShopRequiredAuthority;
import com.qudiancan.backend.enums.shop.ShopAuthorityEnum;
import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.dto.wechat.TableOrderDTO;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.BranchTablePO;
import com.qudiancan.backend.pojo.po.TableCategoryPO;
import com.qudiancan.backend.pojo.vo.shop.BranchTableVO;
import com.qudiancan.backend.pojo.vo.shop.TableCategoryVO;
import com.qudiancan.backend.service.shop.ShopTableService;
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
public class ShopTableController {

    @Autowired
    private ShopTableService shopTableService;

    /**
     * 创建桌台类型
     *
     * @param shopId          店铺id
     * @param branchId        门店id
     * @param tableCategoryVO 桌台类型信息
     * @return 创建的桌台类型
     */
    @PostMapping("/{shopId}/branches/{branchId}/table_categories")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_TABLE_CATEGORY_CREATE)
    public Response<TableCategoryPO> createTableCategory(@PathVariable String shopId, @PathVariable Integer branchId, TableCategoryVO tableCategoryVO) {
        return Response.success(shopTableService.createTableCategory(ShopAccountHolder.get().getId(), shopId, branchId, tableCategoryVO));
    }

    /**
     * 获取桌台类型列表
     *
     * @param shopId   店铺id
     * @param branchId 门店id
     * @return 获取的桌台类型列表
     */
    @GetMapping("/{shopId}/branches/{branchId}/table_categories")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_TABLE_CATEGORY_SHOW)
    public Response<List<TableCategoryPO>> listTableCategory(@PathVariable String shopId, @PathVariable Integer branchId) {
        return Response.success(shopTableService.listTableCategory(ShopAccountHolder.get().getId(), shopId, branchId));
    }

    /**
     * 更新桌台类型
     *
     * @param shopId          店铺id
     * @param branchId        门店id
     * @param tableCategoryId 桌台类型id
     * @param tableCategoryVO 桌台类型信息
     * @return 更新后的桌台类型
     */
    @PostMapping("/{shopId}/branches/{branchId}/table_categories/{tableCategoryId}")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_TABLE_CATEGORY_UPDATE)
    public Response<TableCategoryPO> updateTableCategory(@PathVariable String shopId, @PathVariable Integer branchId, @PathVariable Integer tableCategoryId, TableCategoryVO tableCategoryVO) {
        return Response.success(shopTableService.updateTableCategory(ShopAccountHolder.get().getId(), shopId, branchId, tableCategoryId, tableCategoryVO));
    }

    /**
     * 创建桌台
     *
     * @param shopId        店铺id
     * @param branchId      门店id
     * @param branchTableVO 桌台信息
     * @return 创建的桌台
     */
    @PostMapping("/{shopId}/branches/{branchId}/tables")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_TABLE_CREATE)
    public Response<BranchTablePO> createBranchTable(@PathVariable String shopId, @PathVariable Integer branchId, BranchTableVO branchTableVO) {
        return Response.success(shopTableService.createBranchTable(ShopAccountHolder.get().getId(), shopId, branchId, branchTableVO));
    }

    /**
     * 获取桌台
     *
     * @param shopId        店铺id
     * @param branchId      门店id
     * @param branchTableId 桌台id
     * @return 获取的桌台
     */
    @GetMapping("/{shopId}/branches/{branchId}/tables/{branchTableId}")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_TABLE_SHOW)
    public Response<BranchTablePO> getBranchTable(@PathVariable String shopId,
                                                  @PathVariable Integer branchId, @PathVariable Integer branchTableId) {
        return Response.success(shopTableService.getBranchTable(ShopAccountHolder.get().getId(), shopId, branchId, branchTableId));
    }

    /**
     * 更新桌台
     *
     * @param shopId        店铺id
     * @param branchId      门店id
     * @param branchTableId 桌台id
     * @param branchTableVO 桌台信息
     * @return 更新后的桌台
     */
    @PostMapping("/{shopId}/branches/{branchId}/tables/{branchTableId}")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_TABLE_UPDATE)
    public Response<BranchTablePO> updateBranchTable(@PathVariable String shopId, @PathVariable Integer branchId,
                                                     @PathVariable Integer branchTableId, BranchTableVO branchTableVO) {
        return Response.success(shopTableService.updateBranchTable(ShopAccountHolder.get().getId(), shopId, branchId, branchTableId, branchTableVO));
    }

    @GetMapping("/{shopId}/branches/{branchId}/tables")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_TABLE_SHOW)
    public Response<List<BranchTablePO>> listBranchTable(@PathVariable String shopId, @PathVariable Integer branchId) {
        AccountPO accountPO = ShopAccountHolder.get();
        return Response.success(shopTableService.listBranchTable(accountPO.getId(), shopId, branchId));
    }

    @GetMapping("/tables/{tableId}/tableOrder")
    @ShopRequiredAuthority(ShopAuthorityEnum.SHOP_BRANCH_CASHIER)
    public Response<TableOrderDTO> getTableOrder(@PathVariable Integer tableId) {
        return Response.success(shopTableService.getTableOrder(tableId));
    }

}
