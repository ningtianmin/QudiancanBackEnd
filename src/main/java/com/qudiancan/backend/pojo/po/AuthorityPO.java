package com.qudiancan.backend.pojo.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "Authority")
@Data
public class AuthorityPO {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String path;
    private String roleIds;
    private String description;
}
