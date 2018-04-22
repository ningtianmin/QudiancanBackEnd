package com.qudiancan.backend.pojo.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "Authority")
@Data
@DynamicInsert
public class AuthorityPO {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String path;
    private String roleIds;
    private String description;
}
