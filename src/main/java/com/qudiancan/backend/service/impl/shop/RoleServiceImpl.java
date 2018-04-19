package com.qudiancan.backend.service.impl.shop;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.po.AuthorityPO;
import com.qudiancan.backend.pojo.po.RolePO;
import com.qudiancan.backend.repository.AuthorityRepository;
import com.qudiancan.backend.repository.RoleRepository;
import com.qudiancan.backend.service.shop.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author NINGTIANMIN
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public List<RolePO> getRoles(String roleIds) {
        if (StringUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return roleRepository.findAll(Arrays.stream(roleIds.split(",")).map(Integer::valueOf).collect(Collectors.toSet()));
    }

    @Override
    public List<RolePO> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<AuthorityPO> listAuthority(Integer roleId) {
        if (Objects.isNull(roleId)) {
            throw new ShopException(ResponseEnum.BAD_REQUEST);
        }
        if (Objects.isNull(roleRepository.findOne(roleId))) {
            throw new ShopException(ResponseEnum.PARAM_INVALID);
        }
        return authorityRepository.findAll().stream()
                .filter(o -> !StringUtils.isEmpty(o.getRoleIds()) && Arrays.stream(o.getRoleIds().split(","))
                        .map(Integer::valueOf)
                        .anyMatch(d -> d.equals(roleId)))
                .collect(Collectors.toList());
    }

}
