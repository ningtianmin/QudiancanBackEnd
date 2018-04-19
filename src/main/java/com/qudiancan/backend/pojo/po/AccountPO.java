package com.qudiancan.backend.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "Account")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @CreatedDate
    private Timestamp createTime;
}
