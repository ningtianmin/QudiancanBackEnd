package com.qudiancan.backend.service.impl;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.po.DepartmentPO;
import com.qudiancan.backend.pojo.vo.DepartmentVO;
import com.qudiancan.backend.repository.AccountRepository;
import com.qudiancan.backend.repository.BranchRepository;
import com.qudiancan.backend.repository.DepartmentRepository;
import com.qudiancan.backend.service.DepartmentService;
import com.qudiancan.backend.service.util.BranchServiceUtil;
import com.qudiancan.backend.service.util.DepartmentServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author NINGTIANMIN
 */
@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentPO createDepartment(Integer accountId, String shopId, Integer branchId, DepartmentVO departmentVO) {
        log.info("[创建出品部门]accountId:{},shopId:{},branchId:{},departmentVO:{}", accountId, shopId, branchId, departmentVO);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(branchId) || Objects.isNull(departmentVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,  shopId,  branchId,  departmentVO");
        }
        DepartmentServiceUtil.checkDepartmentVO(departmentVO);
        AccountPO accountPO = accountRepository.findOne(accountId);
        BranchPO branchPO = branchRepository.findOne(branchId);
        if (!BranchServiceUtil.canManageBranch(accountPO, shopId, branchPO)) {
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
        AccountPO accountPO = accountRepository.findOne(accountId);
        BranchPO branchPO = branchRepository.findOne(branchId);
        if (!BranchServiceUtil.canManageBranch(accountPO, shopId, branchPO)) {
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
        DepartmentServiceUtil.checkDepartmentVO(departmentVO);
        if (!departmentPO.getName().equals(departmentVO.getName()) && Objects.nonNull(departmentRepository.findByBranchIdAndName(branchId, departmentVO.getName()))) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "该出品部门名称已被占用");
        }
        departmentPO.setName(departmentVO.getName());
        departmentPO.setDescription(departmentVO.getDescription());
        return departmentRepository.save(departmentPO);
    }
}
