package com.qudiancan.backend.pojo.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "Account")
@Data
public class AccountPO {
    @Id
    @GeneratedValue
    private Integer id;
    private String shopId;
    private String loginId;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String branchIds;
    private String roleIds;
    private String isCreator;
    private Timestamp createTime;
}
