package com.qudiancan.backend.service.shop;

import com.qudiancan.backend.enums.StringPairDTO;
import com.qudiancan.backend.pojo.po.DepartmentPO;
import com.qudiancan.backend.pojo.vo.shop.DepartmentVO;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface ShopDepartmentService {

    /**
     * 创建出品部门
     *
     * @param accountId    账户id
     * @param shopId       餐厅id
     * @param branchId     门店id
     * @param departmentVO 出品部门信息
     * @return 创建的出品部门
     */
    DepartmentPO createDepartment(Integer accountId, String shopId, Integer branchId, DepartmentVO departmentVO);

    /**
     * 获取出品部门
     *
     * @param accountId    账户id
     * @param shopId       餐厅id
     * @param branchId     门店id
     * @param departmentId 出品部门id
     * @return 获取的出品部门
     */
    DepartmentPO getDepartment(Integer accountId, String shopId, Integer branchId, Integer departmentId);

    /**
     * 更新出品部门
     *
     * @param accountId    账户id
     * @param shopId       餐厅id
     * @param branchId     门店id
     * @param departmentId 出品部门id
     * @param departmentVO 出品部门信息
     * @return 更新后的出品部门
     */
    DepartmentPO updateDepartment(Integer accountId, String shopId, Integer branchId, Integer departmentId, DepartmentVO departmentVO);

    /**
     * 获取出品部门列表
     *
     * @param accountId 账户id
     * @param shopId    餐厅id
     * @param branchId  门店id
     * @return 出品部门列表
     */
    List<DepartmentPO> listDepartment(Integer accountId, String shopId, Integer branchId);

    /**
     * 获取门店的出品部门枚举
     *
     * @param accountId 账户id
     * @param shopId    餐厅id
     * @param branchId  门店id
     * @return 出品部门枚举
     */
    List<StringPairDTO> departmentsStringPair(Integer accountId, String shopId, Integer branchId);

}
