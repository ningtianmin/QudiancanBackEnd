package com.qudiancan.backend.pojo.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "BranchProduct")
@Data
public class BranchProductPO {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer branchId;
    private Integer categoryId;
    private Integer departmentId;
    private String name;
    private String unitName;
    private BigDecimal price;
    private String description;
    private Integer position;
    private String status;
}
