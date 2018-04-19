package com.qudiancan.backend.service.shop;

import com.qudiancan.backend.pojo.po.AuthorityPO;
import com.qudiancan.backend.pojo.po.RolePO;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface RoleService {

    /**
     * 通过角色id列表获取角色列表
     *
     * @param roleIds 角色id列表
     * @return 角色列表
     */
    List<RolePO> getRoles(String roleIds);

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    List<RolePO> findAll();

    /**
     * 根据角色id查询权限列表
     *
     * @param roleId 角色id
     * @return 权限列表
     */
    List<AuthorityPO> listAuthority(Integer roleId);

}
