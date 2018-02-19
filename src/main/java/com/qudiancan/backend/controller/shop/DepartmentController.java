package com.qudiancan.backend.controller.shop;

import com.qudiancan.backend.common.AccountHolder;
import com.qudiancan.backend.common.RequiredAuthority;
import com.qudiancan.backend.enums.AuthorityEnum;
import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.Session;
import com.qudiancan.backend.pojo.po.DepartmentPO;
import com.qudiancan.backend.pojo.vo.DepartmentVO;
import com.qudiancan.backend.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author NINGTIANMIN
 */
@RestController
@RequestMapping("/shops")
@Slf4j
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 创建出品部门
     *
     * @param session      账户session
     * @param shopId       店铺id
     * @param branchId     门店id
     * @param departmentVO 出品部门信息
     * @return 创建的出品部门
     */
    @PostMapping("/{shopId}/branches/{branchId}/departments")
    @RequiredAuthority(AuthorityEnum.BRANCH_DEPARTMENT_CREATE)
    public Response<DepartmentPO> createDepartment(Session session, @PathVariable String shopId, @PathVariable Integer branchId,
                                                   DepartmentVO departmentVO) {
        log.info("[创建出品部门]session:{}", session);
        return Response.success(departmentService.createDepartment(AccountHolder.get().getId(), shopId, branchId, departmentVO));
    }

    /**
     * 获取出品部门
     *
     * @param session      账户session
     * @param shopId       店铺id
     * @param branchId     门店id
     * @param departmentId 出品部门id
     * @return 获取的出品部门
     */
    @GetMapping("/{shopId}/branches/{branchId}/departments/{departmentId}")
    @RequiredAuthority(AuthorityEnum.BRANCH_DEPARTMENT_SHOW)
    public Response<DepartmentPO> getDepartment(Session session, @PathVariable String shopId, @PathVariable Integer branchId, @PathVariable Integer departmentId) {
        log.info("[获取出品部门]session:{}", session);
        return Response.success(departmentService.getDepartment(AccountHolder.get().getId(), shopId, branchId, departmentId));
    }

    /**
     * 更新出品部门
     *
     * @param session      账户session
     * @param shopId       店铺id
     * @param branchId     门店id
     * @param departmentId 出品部门id
     * @param departmentVO 出品部门信息
     * @return 更新后的出品部门
     */
    @PostMapping("/{shopId}/branches/{branchId}/departments/{departmentId}")
    @RequiredAuthority(AuthorityEnum.BRANCH_DEPARTMENT_UPDATE)
    public Response<DepartmentPO> updateDepartment(Session session, @PathVariable String shopId, @PathVariable Integer branchId,
                                                   @PathVariable Integer departmentId, DepartmentVO departmentVO) {
        log.info("[更新出品部门]session:{}", session);
        return Response.success(departmentService.updateDepartment(AccountHolder.get().getId(), shopId, branchId, departmentId, departmentVO));
    }
}
