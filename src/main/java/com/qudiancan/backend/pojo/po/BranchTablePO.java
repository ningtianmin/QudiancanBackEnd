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
@Entity(name = "BranchTable")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class BranchTablePO {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer branchId;
    private Integer categoryId;
    private Integer orderId;
    private String name;
    private Integer capacity;
    private Integer position;
    private String status;
    private String qrcode;
}
