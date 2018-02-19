package com.qudiancan.backend.service;

import com.qudiancan.backend.pojo.po.TableCategoryPO;
import com.qudiancan.backend.pojo.vo.TableCategoryVO;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface TableService {
    /**
     * 创建桌台类型
     *
     * @param accountId       账户id
     * @param shopId          店铺id
     * @param branchId        门店id
     * @param tableCategoryVO 桌台类型信息
     * @return 创建的桌台类型
     */
    TableCategoryPO createTableCategory(Integer accountId, String shopId, Integer branchId, TableCategoryVO tableCategoryVO);

    /**
     * 获取桌台类型列表
     *
     * @param accountId 账户id
     * @param shopId    店铺id
     * @param branchId  门店id
     * @return 获取的桌台类型列表
     */
    List<TableCategoryPO> listTableCategory(Integer accountId, String shopId, Integer branchId);

    /**
     * 更新桌台类型
     *
     * @param accountId       账户id
     * @param shopId          店铺id
     * @param branchId        门店id
     * @param tableCategoryId 桌台类型id
     * @param tableCategoryVO 桌台类型信息
     * @return 更新后的桌台类型
     */
    TableCategoryPO updateTableCategory(Integer accountId, String shopId, Integer branchId, Integer tableCategoryId, TableCategoryVO tableCategoryVO);
}
