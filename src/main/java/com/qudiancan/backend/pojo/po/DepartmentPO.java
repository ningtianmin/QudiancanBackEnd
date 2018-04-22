package com.qudiancan.backend.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "Department")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class DepartmentPO {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer branchId;
    private String name;
    private String description;
}
