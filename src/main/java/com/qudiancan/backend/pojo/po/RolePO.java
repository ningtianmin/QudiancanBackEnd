package com.qudiancan.backend.pojo.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "Role")
@Data
public class RolePO {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;
}
