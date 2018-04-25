package com.qudiancan.backend.service.shop;

import com.qudiancan.backend.enums.StringPairDTO;
import com.qudiancan.backend.pojo.dto.wechat.TableOrderDTO;
import com.qudiancan.backend.pojo.po.BranchTablePO;
import com.qudiancan.backend.pojo.po.TableCategoryPO;
import com.qudiancan.backend.pojo.vo.shop.BranchTableVO;
import com.qudiancan.backend.pojo.vo.shop.TableCategoryVO;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface ShopTableService {
    /**
     * 创建桌台类型
     *
     * @param accountId       账户id
     * @param shopId          餐厅id
     * @param branchId        门店id
     * @param tableCategoryVO 桌台类型信息
     * @return 创建的桌台类型
     */
    TableCategoryPO createTableCategory(Integer accountId, String shopId, Integer branchId, TableCategoryVO tableCategoryVO);

    /**
     * 获取桌台类型列表
     *
     * @param accountId 账户id
     * @param shopId    餐厅id
     * @param branchId  门店id
     * @return 获取的桌台类型列表
     */
    List<TableCategoryPO> listTableCategory(Integer accountId, String shopId, Integer branchId);

    /**
     * 更新桌台类型
     *
     * @param accountId       账户id
     * @param shopId          餐厅id
     * @param branchId        门店id
     * @param tableCategoryId 桌台类型id
     * @param tableCategoryVO 桌台类型信息
     * @return 更新后的桌台类型
     */
    TableCategoryPO updateTableCategory(Integer accountId, String shopId, Integer branchId, Integer tableCategoryId, TableCategoryVO tableCategoryVO);

    /**
     * 创建桌台
     *
     * @param accountId     账户id
     * @param shopId        餐厅id
     * @param branchId      门店id
     * @param branchTableVO 桌台信息
     * @return 创建的桌台
     */
    BranchTablePO createBranchTable(Integer accountId, String shopId, Integer branchId, BranchTableVO branchTableVO);

    /**
     * 获取桌台
     *
     * @param accountId     账户id
     * @param shopId        餐厅id
     * @param branchId      门店id
     * @param branchTableId 桌台id
     * @return 获取的桌台
     */
    BranchTablePO getBranchTable(Integer accountId, String shopId, Integer branchId, Integer branchTableId);

    /**
     * 更新桌台
     *
     * @param accountId     账户id
     * @param shopId        餐厅id
     * @param branchId      门店id
     * @param branchTableId 桌台id
     * @param branchTableVO 桌台信息
     * @return 更新后的桌台
     */
    BranchTablePO updateBranchTable(Integer accountId, String shopId, Integer branchId, Integer branchTableId, BranchTableVO branchTableVO);

    /**
     * 获取桌台
     *
     * @param tableId 桌台id
     * @return 桌台信息
     */
    BranchTablePO getBranchTable(Integer tableId);

    /**
     * 通过订单id使桌台空闲
     *
     * @param orderId 订单id
     */
    void leisureTable(Integer orderId);

    /**
     * 获取门店桌台类目列表
     *
     * @param accountId 账户id
     * @param shopId    餐厅id
     * @param branchId  门店id
     * @return 桌台类目列表
     */
    List<StringPairDTO> categoriesStringPair(Integer accountId, String shopId, Integer branchId);

    /**
     * 获取门店桌台列表
     *
     * @param accountId 账户id
     * @param shopId    餐厅id
     * @param branchId  门店id
     * @return 桌台列表
     */
    List<BranchTablePO> listBranchTable(Integer accountId, String shopId, Integer branchId);

    /**
     * 获取桌台及对应的订单
     *
     * @param tableId 桌台id
     * @return 桌台及对应的订单
     */
    TableOrderDTO getTableOrder(Integer tableId);
}
