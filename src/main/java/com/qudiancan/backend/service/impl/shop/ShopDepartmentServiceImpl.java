package com.qudiancan.backend.service.impl.shop;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.po.DepartmentPO;
import com.qudiancan.backend.pojo.vo.shop.DepartmentVO;
import com.qudiancan.backend.repository.DepartmentRepository;
import com.qudiancan.backend.service.shop.ShopBranchService;
import com.qudiancan.backend.service.shop.ShopDepartmentService;
import com.qudiancan.backend.service.util.shop.ShopDepartmentServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author NINGTIANMIN
 */
@Service
@Slf4j
public class ShopDepartmentServiceImpl implements ShopDepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ShopBranchService shopBranchService;

    @Override
    public DepartmentPO createDepartment(Integer accountId, String shopId, Integer branchId, DepartmentVO departmentVO) {
        log.info("[创建出品部门]accountId:{},shopId:{},branchId:{},departmentVO:{}", accountId, shopId, branchId, departmentVO);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(departmentVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,  shopId,  branchId,  departmentVO");
        }
        ShopDepartmentServiceUtil.checkDepartmentVO(departmentVO);
        if (!shopBranchService.canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        if (Objects.nonNull(departmentRepository.findByBranchIdAndName(branchId, departmentVO.getName()))) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "该出品部门名称已被占用");
        }
        return departmentRepository.save(new DepartmentPO(null, branchId, departmentVO.getName(), departmentVO.getDescription()));
    }

    @Override
    public DepartmentPO getDepartment(Integer accountId, String shopId, Integer branchId, Integer departmentId) {
        log.info("[获取出品部门]accountId:{},shopId:{},branchId:{},departmentId:{}", accountId, shopId, branchId, departmentId);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(departmentId)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId,departmentId");
        }
        if (!shopBranchService.canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        DepartmentPO departmentPO = departmentRepository.findOne(departmentId);
        if (Objects.isNull(departmentPO) || !branchId.equals(departmentPO.getBranchId())) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        return departmentPO;
    }

    @Override
    public DepartmentPO updateDepartment(Integer accountId, String shopId, Integer branchId, Integer departmentId, DepartmentVO departmentVO) {
        log.info("[更新出品部门]accountId:{},shopId:{},branchId:{},departmentId:{},departmentVO:{}", accountId, shopId, branchId, departmentId, departmentVO);
        DepartmentPO departmentPO = getDepartment(accountId, shopId, branchId, departmentId);
        ShopDepartmentServiceUtil.checkDepartmentVO(departmentVO);
        if (!departmentPO.getName().equals(departmentVO.getName()) && Objects.nonNull(departmentRepository.findByBranchIdAndName(branchId, departmentVO.getName()))) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "该出品部门名称已被占用");
        }
        departmentPO.setName(departmentVO.getName());
        departmentPO.setDescription(departmentVO.getDescription());
        return departmentRepository.save(departmentPO);
    }

    @Override
    public List<DepartmentPO> listDepartment(Integer accountId, String shopId, Integer branchId) {
        log.info("[获取出品部门列表]accountId:{},shopId:{},branchId;{}", accountId, shopId, branchId);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId,branchId");
        }
        // 逻辑验证
        if (!shopBranchService.canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        return departmentRepository.findByBranchId(branchId);
    }
}
