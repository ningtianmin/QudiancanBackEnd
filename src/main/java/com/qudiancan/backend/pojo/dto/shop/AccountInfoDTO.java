package com.qudiancan.backend.pojo.dto.shop;

import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.po.RolePO;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Data
public class AccountInfoDTO {

    private Integer id;
    private String shopId;
    private String loginId;
    private String name;
    private String email;
    private String phone;
    private String branchesString;
    private List<BranchPO> branches;
    private String rolesString;
    private List<RolePO> roles;
    private String isCreator;
    private Timestamp createTime;

}
