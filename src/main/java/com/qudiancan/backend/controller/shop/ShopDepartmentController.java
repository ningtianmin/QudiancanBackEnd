package com.qudiancan.backend.controller.shop;

import com.qudiancan.backend.common.ShopAccountHolder;
import com.qudiancan.backend.common.ShopRequiredAuthority;
import com.qudiancan.backend.enums.shop.ShopAuthorityEnum;
import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.po.DepartmentPO;
import com.qudiancan.backend.pojo.vo.shop.DepartmentVO;
import com.qudiancan.backend.service.shop.ShopDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author NINGTIANMIN
 */
@RestController
@RequestMapping("/shops")
@Slf4j
public class ShopDepartmentController {
    @Autowired
    private ShopDepartmentService shopDepartmentService;

    /**
     * 创建出品部门
     *
     * @param shopId       店铺id
     * @param branchId     门店id
     * @param departmentVO 出品部门信息
     * @return 创建的出品部门
     */
    @PostMapping("/{shopId}/branches/{branchId}/departments")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_DEPARTMENT_CREATE)
    public Response<DepartmentPO> createDepartment(@PathVariable String shopId, @PathVariable Integer branchId,
                                                   DepartmentVO departmentVO) {
        return Response.success(shopDepartmentService.createDepartment(ShopAccountHolder.get().getId(), shopId, branchId, departmentVO));
    }

    /**
     * 获取出品部门
     *
     * @param shopId       店铺id
     * @param branchId     门店id
     * @param departmentId 出品部门id
     * @return 获取的出品部门
     */
    @GetMapping("/{shopId}/branches/{branchId}/departments/{departmentId}")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_DEPARTMENT_SHOW)
    public Response<DepartmentPO> getDepartment(@PathVariable String shopId, @PathVariable Integer branchId, @PathVariable Integer departmentId) {
        return Response.success(shopDepartmentService.getDepartment(ShopAccountHolder.get().getId(), shopId, branchId, departmentId));
    }

    /**
     * 更新出品部门
     *
     * @param shopId       店铺id
     * @param branchId     门店id
     * @param departmentId 出品部门id
     * @param departmentVO 出品部门信息
     * @return 更新后的出品部门
     */
    @PostMapping("/{shopId}/branches/{branchId}/departments/{departmentId}")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_DEPARTMENT_UPDATE)
    public Response<DepartmentPO> updateDepartment(@PathVariable String shopId, @PathVariable Integer branchId,
                                                   @PathVariable Integer departmentId, DepartmentVO departmentVO) {
        return Response.success(shopDepartmentService.updateDepartment(ShopAccountHolder.get().getId(), shopId, branchId, departmentId, departmentVO));
    }
}
