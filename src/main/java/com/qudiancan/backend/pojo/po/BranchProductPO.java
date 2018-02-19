package com.qudiancan.backend.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "BranchProduct")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
